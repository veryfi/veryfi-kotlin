package com.veryfi.kotlin.w2s

import com.veryfi.kotlin.Client
import com.veryfi.kotlin.HttpMethod
import org.json.JSONObject
import java.util.concurrent.CompletableFuture

/**
 * Returns a json string [String] with the list of W2s.
 * @return the url [String]
 */
fun Client.getW2s(): String {
    val endpointName = "/w2s/"
    val requestArguments = JSONObject()
    return request(HttpMethod.GET, endpointName, requestArguments)
}

/**
 * Returns a json string list of W2s.
 * @return the list of previously processed W2s [String]
 */
fun Client.getW2sAsync(): CompletableFuture<String> {
    val endpointName = "/w2s/"
    val requestArguments = JSONObject()
    return requestAsync(HttpMethod.GET, endpointName, requestArguments)
}

