package com.veryfi.kotlin.businessCards

import com.veryfi.kotlin.Client
import com.veryfi.kotlin.HttpMethod
import org.json.JSONObject
import java.util.concurrent.CompletableFuture

/**
 * Get all Business cards. https://docs.veryfi.com/api/business-cards/get-business-cards/
 * Returns a json string [String] with the list of Business Cards.
 * @return the url [String]
 */
fun Client.getBusinessCards(): String {
    val endpointName = "/business-cards/"
    val requestArguments = JSONObject()
    return request(HttpMethod.GET, endpointName, requestArguments)
}

/**
 * Get all Business cards. https://docs.veryfi.com/api/business-cards/get-business-cards/
 * Returns a json string list of Business Cards.
 * @return the list of previously processed Business Cards [String]
 */
fun Client.getBusinessCardsAsync(): CompletableFuture<String> {
    val endpointName = "/business-cards/"
    val requestArguments = JSONObject()
    return requestAsync(HttpMethod.GET, endpointName, requestArguments)
}

