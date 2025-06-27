package com.veryfi.kotlin.w8bene

import com.veryfi.kotlin.Client
import com.veryfi.kotlin.HttpMethod
import org.json.JSONObject
import java.util.concurrent.CompletableFuture

/**
 * Get a specific W-8BEN-E. https://docs.veryfi.com/api/w-8ben-e/get-a-w-8-ben-e/
 * Returns a json string [String] W8BenE information
 * @param documentId ID of the W8BenE you'd like to retrieve.
 * @return the data extracted from the W8BenE  [String]
 */
fun Client.getW8BenE(documentId: String): String {
    val endpointName = "/w-8ben-e/$documentId/"
    val requestArguments = JSONObject()
    requestArguments.put("id", documentId)
    return request(HttpMethod.GET, endpointName, requestArguments)
}

/**
 * Get a specific W-8BEN-E. https://docs.veryfi.com/api/w-8ben-e/get-a-w-8-ben-e/
 * Returns a json string [String] W8BenE information
 * @param documentId ID of the W8BenE you'd like to retrieve.
 * @return the data extracted from the W8BenE  [String]
 */
fun Client.getW8BenEAsync(documentId: String): CompletableFuture<String> {
    val endpointName = "/w-8ben-e/$documentId/"
    val requestArguments = JSONObject()
    requestArguments.put("id", documentId)
    return requestAsync(HttpMethod.GET, endpointName, requestArguments)
}

