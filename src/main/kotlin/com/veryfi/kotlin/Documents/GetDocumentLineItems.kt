package com.veryfi.kotlin.documents

import com.veryfi.kotlin.Client
import com.veryfi.kotlin.HttpMethod
import org.json.JSONObject
import java.util.concurrent.CompletableFuture

/**
 * Get line items of an existing document
 * @return the list of line items
 */
fun Client.getDocumentLineItems(
    documentId: String,
): String {
    val endpointName = "/documents/${documentId}/line-items"
    return request(HttpMethod.GET, endpointName, null)
}

/**
 * Get line items of an existing document
 * @return the list of line items
 */
fun Client.getDocumentLineItemsAsync(
    documentId: String,
): CompletableFuture<String> {
    val endpointName = "/documents/${documentId}/line-items"
    return requestAsync(HttpMethod.GET, endpointName, JSONObject())
}

