package com.veryfi.kotlin.documents

import com.veryfi.kotlin.Client
import com.veryfi.kotlin.HttpMethod
import org.json.JSONObject
import java.util.concurrent.CompletableFuture

/**
 * Deletes line items of an existing document. https://docs.veryfi.com/api/receipts-invoices/delete-all-document-line-items/
 * @return the status
 */
fun Client.deleteDocumentLineItems(
    documentId: String,
): String {
    val endpointName = "/documents/${documentId}/line-items"
    return request(HttpMethod.DELETE, endpointName, null)
}

/**
 * Deletes line items of an existing document. https://docs.veryfi.com/api/receipts-invoices/delete-all-document-line-items/
 * @return the status
 */
fun Client.deleteDocumentLineItemsAsync(
    documentId: String,
): CompletableFuture<String> {
    val endpointName = "/documents/${documentId}/line-items"
    return requestAsync(HttpMethod.DELETE, endpointName, JSONObject())
}

