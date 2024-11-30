package com.veryfi.kotlin.w9s

import com.veryfi.kotlin.Client
import com.veryfi.kotlin.HttpMethod
import org.json.JSONObject
import java.util.concurrent.CompletableFuture

/**
 * Returns a json string [String] with the list of W9s.
 * @return the url [String]
 */
fun Client.getW9s(): String {
    val endpointName = "/w9s/"
    val requestArguments = JSONObject()
    return request(HttpMethod.GET, endpointName, requestArguments)
}

/**
 * Returns a json string list of W9s.
 * @return the list of previously processed W9s [String]
 */
fun Client.getW9sAsync(): CompletableFuture<String> {
    val endpointName = "/w9s/"
    val requestArguments = JSONObject()
    return requestAsync(HttpMethod.GET, endpointName, requestArguments)
}

