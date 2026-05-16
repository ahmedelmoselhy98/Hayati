package com.ahmed.elmoselhy.hayati.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GitHubUser(
    @SerialName("login")
    val login: String,
    @SerialName("id")
    val id: Int
)
