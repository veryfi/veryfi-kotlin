package com.veryfi.kotlin.documents

import com.veryfi.kotlin.Client
import com.veryfi.kotlin.HttpMethod
import org.json.JSONObject
import java.util.concurrent.CompletableFuture

/**
 * Deletes a line item to an existing document
 * @param lineItemId Id of line item
 * @return the status
 */
fun Client.deleteLineItem(
    documentId: String,
    lineItemId: String
): String {
    val endpointName = "/documents/${documentId}/line-items/${lineItemId}"
    return request(HttpMethod.DELETE, endpointName, null)
}

/**
 * Deletes a line item to an existing document
 * @param lineItemId Id of line item
 * @return the status
 */
fun Client.deleteLineItemAsync(
    documentId: String,
    lineItemId: String
): CompletableFuture<String> {
    val endpointName = "/documents/${documentId}/line-items/${lineItemId}"
    return requestAsync(HttpMethod.DELETE, endpointName, JSONObject())
}

