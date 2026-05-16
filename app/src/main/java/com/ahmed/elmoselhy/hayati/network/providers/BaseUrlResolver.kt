package com.ahmed.elmoselhy.hayati.network.providers

/**
 * @author Ahmed Elmoselhy
 * @since 1.0.0
 * "BaseUrlResolver" is a class that resolves the base URL for a network request.
 * which means take a decision about which base url to use in the network request
 *
 * @param providers is a list of base url providers that can be used to resolve the base url
 * and the providers are in the order of priority
 * */

object BaseUrlResolver {
    fun resolve(providers: List<BaseUrlProvider>): String {

        return providers.firstNotNullOfOrNull {

            it.getBaseUrl()?.takeIf(String::isNotBlank)

        } ?: error("No base url found")
    }
}