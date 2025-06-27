package com.veryfi.kotlin.w9s

import com.veryfi.kotlin.Client
import com.veryfi.kotlin.HttpMethod
import org.json.JSONObject
import java.util.concurrent.CompletableFuture

/**
 * Get a w9 document https://docs.veryfi.com/api/w9s/get-a-w-9/
 * Returns a json string [String] w9 information
 * @param documentId ID of the w9 you'd like to retrieve.
 * @return the data extracted from the W9 [String]
 */
fun Client.getW9(documentId: String): String {
    val endpointName = "/w9s/$documentId/"
    val requestArguments = JSONObject()
    requestArguments.put("id", documentId)
    return request(HttpMethod.GET, endpointName, requestArguments)
}

/**
 * Get a w9 document https://docs.veryfi.com/api/w9s/get-a-w-9/
 * Returns a json string [String] w9 information
 * @param documentId ID of the w9 you'd like to retrieve.
 * @return the data extracted from the W9 [String]
 */
fun Client.getW9Async(documentId: String): CompletableFuture<String> {
    val endpointName = "/w9s/$documentId/"
    val requestArguments = JSONObject()
    requestArguments.put("id", documentId)
    return requestAsync(HttpMethod.GET, endpointName, requestArguments)
}

