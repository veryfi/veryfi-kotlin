package com.veryfi.kotlin.businessCards

import com.veryfi.kotlin.Client
import com.veryfi.kotlin.HttpMethod
import org.json.JSONObject
import java.util.concurrent.CompletableFuture

/**
 * Get all Business cards. https://docs.veryfi.com/api/business-cards/get-business-cards/
 * Returns a json string [String] with the list of Business Cards.
 * @param parameters Additional request parameters
 * @return the url [String]
 */
fun Client.getBusinessCards(parameters: JSONObject? = null): String {
    val endpointName = "/business-cards/"
    val requestArguments = JSONObject()
    if (parameters != null && !parameters.isEmpty) {
        for (key in JSONObject.getNames(parameters)) {
            requestArguments.put(key, parameters[key])
        }
    }
    return request(HttpMethod.GET, endpointName, requestArguments)
}

/**
 * Get all Business cards. https://docs.veryfi.com/api/business-cards/get-business-cards/
 * Returns a json string list of Business Cards.
 * @param parameters Additional request parameters
 * @return the list of previously processed Business Cards [String]
 */
fun Client.getBusinessCardsAsync(parameters: JSONObject? = null): CompletableFuture<String> {
    val endpointName = "/business-cards/"
    val requestArguments = JSONObject()
    if (parameters != null && !parameters.isEmpty) {
        for (key in JSONObject.getNames(parameters)) {
            requestArguments.put(key, parameters[key])
        }
    }
    return requestAsync(HttpMethod.GET, endpointName, requestArguments)
}

