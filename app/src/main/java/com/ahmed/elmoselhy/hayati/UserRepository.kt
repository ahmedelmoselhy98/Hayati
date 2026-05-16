package com.ahmed.elmoselhy.hayati

import com.ahmed.elmoselhy.hayati.network.GitHubUser
import com.ahmed.elmoselhy.hayati.network.client.NetworkClient
import com.ahmed.elmoselhy.hayati.network.extensions.toRequestBodyData
import com.ahmed.elmoselhy.hayati.network.models.ApiRequest
import com.ahmed.elmoselhy.hayati.network.models.ApiResult
import com.ahmed.elmoselhy.hayati.network.models.HttpMethod

class UserRepository(
    private val networkClient: NetworkClient
) {

    suspend fun getUser(): ApiResult<GitHubUser> {

        return networkClient.execute(

            request = ApiRequest(
                endpoint = "users/octocat",
                method = HttpMethod.GET
            ),

            serializer = GitHubUser.serializer()
        )
    }
    suspend fun getCustomUser(): ApiResult<GitHubUser> {

        return networkClient.execute(

            request = ApiRequest(

                customUrl = "https://dummyjson.com/",

                endpoint = "products",

                method = HttpMethod.GET
            ),

            serializer = GitHubUser.serializer()
        )
    }
    suspend fun postUser(): ApiResult<GitHubUser> {

        return networkClient.execute(

            request = ApiRequest(
                endpoint = "users",
                method = HttpMethod.POST,
                body = GitHubUser(
                    login = "ahmed",
                    id = 1
                ).toRequestBodyData(GitHubUser.serializer())
            ),

            serializer = GitHubUser.serializer()
        )
    }
}