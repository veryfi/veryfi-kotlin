package com.veryfi.kotlin.checks

import com.veryfi.kotlin.Client
import com.veryfi.kotlin.HttpMethod
import org.json.JSONObject
import java.util.concurrent.CompletableFuture

/**
 * Get a specific check. https://docs.veryfi.com/api/checks/get-a-check/
 * Returns a json string [String] check information
 * @param documentId ID of the check you'd like to retrieve.
 * @return the data extracted from the Check [String]
 */
fun Client.getCheck(documentId: String): String {
    val endpointName = "/checks/$documentId/"
    val requestArguments = JSONObject()
    requestArguments.put("id", documentId)
    return request(HttpMethod.GET, endpointName, requestArguments)
}

/**
 * Get a specific check. https://docs.veryfi.com/api/checks/get-a-check/
 * Returns a json string [String] check information
 * @param documentId ID of the check you'd like to retrieve.
 * @return the data extracted from the Check [CompletableFuture]
 */
fun Client.getCheckAsync(documentId: String): CompletableFuture<String> {
    val endpointName = "/checks/$documentId/"
    val requestArguments = JSONObject()
    requestArguments.put("id", documentId)
    return requestAsync(HttpMethod.GET, endpointName, requestArguments)
} 