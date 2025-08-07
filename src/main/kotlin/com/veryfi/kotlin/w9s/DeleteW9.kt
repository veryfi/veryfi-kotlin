package com.veryfi.kotlin.w9s

import com.veryfi.kotlin.Client
import com.veryfi.kotlin.HttpMethod
import org.json.JSONObject
import java.util.concurrent.CompletableFuture

/**
 * Delete W9 from Veryfi. https://docs.veryfi.com/api/w9s/delete-a-w-9/
 * @param documentId ID of the W9 you'd like to delete.
 * @return the response data. [String]
 */
fun Client.deleteW9(documentId: String): String {
    val endpointName = "/w9s/$documentId/"
    val requestArguments = JSONObject()
    requestArguments.put("id", documentId)
    return request(HttpMethod.DELETE, endpointName, requestArguments)
}

/**
 * Delete W9 from Veryfi. https://docs.veryfi.com/api/w9s/delete-a-w-9/
 * @param documentId ID of the W9 you'd like to delete.
 * @return the response data. [CompletableFuture]
 */
fun Client.deleteW9Async(documentId: String): CompletableFuture<String> {
    val endpointName = "/w9s/$documentId/"
    val requestArguments = JSONObject()
    requestArguments.put("id", documentId)
    return requestAsync(HttpMethod.DELETE, endpointName, requestArguments)
} 