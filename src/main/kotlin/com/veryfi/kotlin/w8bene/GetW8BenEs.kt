package com.veryfi.kotlin.w8bene

import com.veryfi.kotlin.Client
import com.veryfi.kotlin.HttpMethod
import org.json.JSONObject
import java.util.concurrent.CompletableFuture

/**
 * Get all W-8BEN-E. https://docs.veryfi.com/api/w-8ben-e/get-w-8-ben-es/
 * Returns a json string [String] with the list of w8BenEs.
 * @return the url [String]
 */
fun Client.getW8BenEs(): String {
    val endpointName = "/w-8ben-e/"
    val requestArguments = JSONObject()
    return request(HttpMethod.GET, endpointName, requestArguments)
}

/**
 * Get all W-8BEN-E. https://docs.veryfi.com/api/w-8ben-e/get-w-8-ben-es/
 * Returns a json string list of w8BenEs.
 * @return the list of previously processed w8BenEs [String]
 */
fun Client.getW8BenEsAsync(): CompletableFuture<String> {
    val endpointName = "/w-8ben-e/"
    val requestArguments = JSONObject()
    return requestAsync(HttpMethod.GET, endpointName, requestArguments)
}

