package com.ahmed.elmoselhy.hayati.network.request

import org.junit.Assert.assertEquals
import org.junit.Test

class UrlBuilderTest {


    @Test
    fun `should join base url with endpoint`() {

        val result = UrlBuilder.build(
            baseUrl = "https://api.github.com",
            endpoint = "/users"
        )

        assertEquals(
            "https://api.github.com/users",
            result
        )
    }


    @Test
    fun `should replace path parameters`() {
        val result = UrlBuilder.build(
            baseUrl = "https://api.github.com",
            endpoint = "/users/{username}",
            pathParameters = mapOf("username" to "octocat")
        )
        assertEquals("https://api.github.com/users/octocat", result)
    }
    @Test
    fun `should avoid double slashes`() {

        val result = UrlBuilder.build(
            baseUrl = "https://api.github.com/",
            endpoint = "/users"
        )

        assertEquals(
            "https://api.github.com/users",
            result
        )
    }
}