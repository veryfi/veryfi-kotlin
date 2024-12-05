package com.veryfi.kotlin.w2s

import com.veryfi.kotlin.Client
import com.veryfi.kotlin.HttpMethod
import org.json.JSONObject
import java.util.concurrent.CompletableFuture

/**
 * Get a w2 document https://docs.veryfi.com/api/w2s/get-a-w-2/
 * Returns a json string [String] w2 information
 * @param documentId ID of the w2 you'd like to retrieve.
 * @return the data extracted from the W2 [String]
 */
fun Client.getW2(documentId: String): String {
    val endpointName = "/w2s/$documentId/"
    val requestArguments = JSONObject()
    requestArguments.put("id", documentId)
    return request(HttpMethod.GET, endpointName, requestArguments)
}

/**
 * Get a w2 document https://docs.veryfi.com/api/w2s/get-a-w-2/
 * Returns a json string [String] w2 information
 * @param documentId ID of the w2 you'd like to retrieve.
 * @return the data extracted from the W2 [String]
 */
fun Client.getW2Async(documentId: String): CompletableFuture<String> {
    val endpointName = "/w2s/$documentId/"
    val requestArguments = JSONObject()
    requestArguments.put("id", documentId)
    return requestAsync(HttpMethod.GET, endpointName, requestArguments)
}

