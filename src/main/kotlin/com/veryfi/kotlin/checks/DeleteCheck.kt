package com.veryfi.kotlin.checks

import com.veryfi.kotlin.Client
import com.veryfi.kotlin.HttpMethod
import org.json.JSONObject
import java.util.concurrent.CompletableFuture

/**
 * Delete Check from Veryfi. https://docs.veryfi.com/api/checks/delete-a-check/
 * @param documentId ID of the Check you'd like to delete.
 * @return the response data. [String]
 */
fun Client.deleteCheck(documentId: String): String {
    val endpointName = "/checks/$documentId/"
    val requestArguments = JSONObject()
    requestArguments.put("id", documentId)
    return request(HttpMethod.DELETE, endpointName, requestArguments)
}

/**
 * Delete Check from Veryfi. https://docs.veryfi.com/api/checks/delete-a-check/
 * @param documentId ID of the Check you'd like to delete.
 * @return the response data. [CompletableFuture]
 */
fun Client.deleteCheckAsync(documentId: String): CompletableFuture<String> {
    val endpointName = "/checks/$documentId/"
    val requestArguments = JSONObject()
    requestArguments.put("id", documentId)
    return requestAsync(HttpMethod.DELETE, endpointName, requestArguments)
} 