package com.veryfi.kotlin.documents

import com.veryfi.kotlin.Client
import com.veryfi.kotlin.HttpMethod
import org.json.JSONObject
import java.util.concurrent.CompletableFuture

/**
 * Gets a line item to an existing document
 * @param lineItemId Id of line item
 * @return the line item
 */
fun Client.getLineItem(
    documentId: String,
    lineItemId: String
): String {
    val endpointName = "/documents/${documentId}/line-items/${lineItemId}"
    return request(HttpMethod.GET, endpointName, null)
}

/**
 * Gets a line item to an existing document
 * @param lineItemId Id of line item
 * @return the line item
 */
fun Client.getLineItemAsync(
    documentId: String,
    lineItemId: String
): CompletableFuture<String> {
    val endpointName = "/documents/${documentId}/line-items/${lineItemId}"
    return requestAsync(HttpMethod.GET, endpointName, JSONObject())
}

