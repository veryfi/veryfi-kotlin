package com.veryfi.kotlin.w9s

import com.veryfi.kotlin.Client
import com.veryfi.kotlin.HttpMethod
import org.json.JSONObject
import java.util.concurrent.CompletableFuture

/**
 * Get all w9 documents. https://docs.veryfi.com/api/w9s/get-w-9-s/
 * Returns a json string [String] with the list of W9s.
 * @param parameters Additional request parameters
 * @return the url [String]
 */
fun Client.getW9s(parameters: JSONObject? = null): String {
    val endpointName = "/w9s/"
    val requestArguments = JSONObject()
    if (parameters != null && !parameters.isEmpty) {
        for (key in JSONObject.getNames(parameters)) {
            requestArguments.put(key, parameters[key])
        }
    }
    return request(HttpMethod.GET, endpointName, requestArguments)
}

/**
 * Get all w9 documents. https://docs.veryfi.com/api/w9s/get-w-9-s/
 * Returns a json string list of W9s.
 * @param parameters Additional request parameters
 * @return the list of previously processed W9s [String]
 */
fun Client.getW9sAsync(parameters: JSONObject? = null): CompletableFuture<String> {
    val endpointName = "/w9s/"
    val requestArguments = JSONObject()
    if (parameters != null && !parameters.isEmpty) {
        for (key in JSONObject.getNames(parameters)) {
            requestArguments.put(key, parameters[key])
        }
    }
    return requestAsync(HttpMethod.GET, endpointName, requestArguments)
}

