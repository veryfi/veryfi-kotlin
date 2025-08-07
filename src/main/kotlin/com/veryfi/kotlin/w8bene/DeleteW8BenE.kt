package com.veryfi.kotlin.w8bene

import com.veryfi.kotlin.Client
import com.veryfi.kotlin.HttpMethod
import org.json.JSONObject
import java.util.concurrent.CompletableFuture

/**
 * Delete W8BenE from Veryfi. https://docs.veryfi.com/api/w-8ben-e/delete-a-w-8-ben-e/
 * @param documentId ID of the W8BenE you'd like to delete.
 * @return the response data. [String]
 */
fun Client.deleteW8BenE(documentId: String): String {
    val endpointName = "/w-8ben-e/$documentId/"
    val requestArguments = JSONObject()
    requestArguments.put("id", documentId)
    return request(HttpMethod.DELETE, endpointName, requestArguments)
}

/**
 * Delete W8BenE from Veryfi. https://docs.veryfi.com/api/w-8ben-e/delete-a-w-8-ben-e/
 * @param documentId ID of the W8BenE you'd like to delete.
 * @return the response data. [CompletableFuture]
 */
fun Client.deleteW8BenEAsync(documentId: String): CompletableFuture<String> {
    val endpointName = "/w-8ben-e/$documentId/"
    val requestArguments = JSONObject()
    requestArguments.put("id", documentId)
    return requestAsync(HttpMethod.DELETE, endpointName, requestArguments)
} 