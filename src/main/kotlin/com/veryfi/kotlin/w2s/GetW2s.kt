package com.veryfi.kotlin.w2s

import com.veryfi.kotlin.Client
import com.veryfi.kotlin.HttpMethod
import org.json.JSONObject
import java.util.concurrent.CompletableFuture

/**
 * Get all w2 documents. https://docs.veryfi.com/api/w2s/get-w-2-s/
 * Returns a json string [String] with the list of W2s.
 * @param parameters Additional request parameters
 * @return the url [String]
 */
fun Client.getW2s(parameters: JSONObject? = null): String {
    val endpointName = "/w2s/"
    val requestArguments = JSONObject()
    if (parameters != null && !parameters.isEmpty) {
        for (key in JSONObject.getNames(parameters)) {
            requestArguments.put(key, parameters[key])
        }
    }
    return request(HttpMethod.GET, endpointName, requestArguments)
}

/**
 * Get all w2 documents. https://docs.veryfi.com/api/w2s/get-w-2-s/
 * Returns a json string list of W2s.
 * @param parameters Additional request parameters
 * @return the list of previously processed W2s [String]
 */
fun Client.getW2sAsync(parameters: JSONObject? = null): CompletableFuture<String> {
    val endpointName = "/w2s/"
    val requestArguments = JSONObject()
    if (parameters != null && !parameters.isEmpty) {
        for (key in JSONObject.getNames(parameters)) {
            requestArguments.put(key, parameters[key])
        }
    }
    return requestAsync(HttpMethod.GET, endpointName, requestArguments)
}

