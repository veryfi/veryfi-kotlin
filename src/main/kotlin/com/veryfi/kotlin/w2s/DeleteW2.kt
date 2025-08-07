package com.veryfi.kotlin.w2s

import com.veryfi.kotlin.Client
import com.veryfi.kotlin.HttpMethod
import org.json.JSONObject
import java.util.concurrent.CompletableFuture

/**
 * Delete W2 from Veryfi. https://docs.veryfi.com/api/w2s/delete-a-w-2/
 * @param documentId ID of the W2 you'd like to delete.
 * @return the response data. [String]
 */
fun Client.deleteW2(documentId: String): String {
    val endpointName = "/w2s/$documentId/"
    val requestArguments = JSONObject()
    requestArguments.put("id", documentId)
    return request(HttpMethod.DELETE, endpointName, requestArguments)
}

/**
 * Delete W2 from Veryfi. https://docs.veryfi.com/api/w2s/delete-a-w-2/
 * @param documentId ID of the W2 you'd like to delete.
 * @return the response data. [CompletableFuture]
 */
fun Client.deleteW2Async(documentId: String): CompletableFuture<String> {
    val endpointName = "/w2s/$documentId/"
    val requestArguments = JSONObject()
    requestArguments.put("id", documentId)
    return requestAsync(HttpMethod.DELETE, endpointName, requestArguments)
} 