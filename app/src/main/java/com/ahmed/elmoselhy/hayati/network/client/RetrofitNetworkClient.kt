package com.ahmed.elmoselhy.hayati.network.client

import com.ahmed.elmoselhy.hayati.network.config.NetworkConfig
import com.ahmed.elmoselhy.hayati.network.errors.NetworkError
import com.ahmed.elmoselhy.hayati.network.models.ApiRequest
import com.ahmed.elmoselhy.hayati.network.models.ApiResult
import com.ahmed.elmoselhy.hayati.network.models.HttpMethod
import com.ahmed.elmoselhy.hayati.network.providers.BaseUrlResolver
import com.ahmed.elmoselhy.hayati.network.providers.CustomBaseUrlProvider
import com.ahmed.elmoselhy.hayati.network.providers.DefaultBaseUrlProvider
import com.ahmed.elmoselhy.hayati.network.providers.RemoteBaseUrlProvider
import com.ahmed.elmoselhy.hayati.network.request.UrlBuilder
import com.ahmed.elmoselhy.hayati.network.service.RetrofitApiService
import kotlinx.coroutines.CancellationException
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

class RetrofitNetworkClient(
    private val service: RetrofitApiService,
    private val json: Json,
    private val config: NetworkConfig,
    private val remoteBaseUrlProvider: RemoteBaseUrlProvider,
) : NetworkClient {

    override suspend fun <T> execute(
        request: ApiRequest, serializer: KSerializer<T>
    ): ApiResult<T> {

        return try {

            val baseUrl = BaseUrlResolver.resolve(
                listOf(
                    CustomBaseUrlProvider(request),
                    remoteBaseUrlProvider,
                    DefaultBaseUrlProvider(config)
                )
            )
            val finalUrl = UrlBuilder.build(
                url = baseUrl, endpoint = request.endpoint, pathParameters = request.pathParameters
            )

            val headers = request.headers

            val response = when (request.method) {

                HttpMethod.GET -> {
                    service.get(
                        url = finalUrl, headers = headers, queries = request.queries
                    )
                }

                HttpMethod.POST -> {
                    executePost(finalUrl, request)
                }

                HttpMethod.PUT -> {
                    executePut(finalUrl, request)
                }

                HttpMethod.PATCH -> {
                    executePatch(finalUrl, request)
                }

                HttpMethod.DELETE -> {
                    service.delete(
                        url = finalUrl, headers = headers, queries = request.queries
                    )
                }
            }

            handleResponse(
                responseBody = response.body()?.string().orEmpty(),
                serializer = serializer,
                statusCode = response.code(),
                isSuccessful = response.isSuccessful,
                message = response.message()
            )

        } catch (e: CancellationException) {

            throw e

        } catch (e: SocketTimeoutException) {

            ApiResult.Failure(NetworkError.Timeout)

        } catch (e: IOException) {

            ApiResult.Failure(NetworkError.NoInternet)

        } catch (e: SerializationException) {

            ApiResult.Failure(
                NetworkError.SerializationError(e.message)
            )

        } catch (e: HttpException) {

            ApiResult.Failure(
                NetworkError.HttpError(
                    e.code(), e.message()
                )
            )

        } catch (e: Throwable) {

            ApiResult.Failure(
                NetworkError.Unknown(e)
            )
        }
    }

    private suspend fun executePost(
        url: String, request: ApiRequest
    ) = service.post(
        url = url,
        headers = request.headers,
        queries = request.queries,
        body = createRequestBody(request)
    )

    private suspend fun executePut(
        url: String, request: ApiRequest
    ) = service.put(
        url = url,
        headers = request.headers,
        queries = request.queries,
        body = createRequestBody(request)
    )

    private suspend fun executePatch(
        url: String, request: ApiRequest
    ) = service.patch(
        url = url,
        headers = request.headers,
        queries = request.queries,
        body = createRequestBody(request)
    )

    private fun createRequestBody(
        request: ApiRequest
    ) = request.body?.let {
        json.encodeToString(
            serializer(it::class.java), it
        )

    }?.toRequestBody(
        "application/json".toMediaType()
    ) ?: ByteArray(0).toRequestBody()

    private fun <T> handleResponse(
        responseBody: String,
        serializer: KSerializer<T>,
        statusCode: Int,
        isSuccessful: Boolean,
        message: String?
    ): ApiResult<T> {

        if (!isSuccessful) {
            return ApiResult.Failure(
                NetworkError.HttpError(
                    statusCode, message
                )
            )
        }

        val data = json.decodeFromString(
            serializer, responseBody
        )

        return ApiResult.Success(
            data = data, statusCode = statusCode
        )
    }

}