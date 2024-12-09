package com.veryfi.kotlin.contracts

import com.veryfi.kotlin.Client
import com.veryfi.kotlin.HttpMethod
import org.json.JSONObject
import java.util.concurrent.CompletableFuture

/**
 * Get all Contract documents.
 * Returns a json string [String] with the list of Contracts.
 * @return the url [String]
 */
fun Client.getContracts(): String {
    val endpointName = "/contracts/"
    val requestArguments = JSONObject()
    return request(HttpMethod.GET, endpointName, requestArguments)
}

/**
 * Get all Contract documents.
 * Returns a json string list of Contracts.
 * @return the list of previously processed Contracts [String]
 */
fun Client.getContractsAsync(): CompletableFuture<String> {
    val endpointName = "/contracts/"
    val requestArguments = JSONObject()
    return requestAsync(HttpMethod.GET, endpointName, requestArguments)
}

