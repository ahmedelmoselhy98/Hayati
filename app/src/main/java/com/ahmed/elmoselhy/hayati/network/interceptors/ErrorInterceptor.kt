package com.ahmed.elmoselhy.hayati.network.interceptors

import okhttp3.Interceptor
import okhttp3.Response

class ErrorInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())

        when (response.code) {
            401 -> throw UnauthorizedException()
            403 -> throw ForbiddenException()
            404 -> throw NotFoundException()
            in 500..599 -> throw ServerException()
        }

        return response
    }
}

class UnauthorizedException : Exception()
class ForbiddenException : Exception()
class NotFoundException : Exception()
class ServerException : Exception()