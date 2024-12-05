package com.veryfi.kotlin.documents

import com.veryfi.kotlin.Client
import com.veryfi.kotlin.HttpMethod
import org.json.JSONObject
import java.util.concurrent.CompletableFuture

/**
 * Delete document from Veryfi. https://docs.veryfi.com/api/receipts-invoices/delete-a-document/
 * @param documentId ID of the document you'd like to delete.
 * @return the response data. [String]
 */
fun Client.deleteDocument(documentId: String): String {
    val endpointName = "/documents/$documentId/"
    val requestArguments = JSONObject()
    requestArguments.put("id", documentId)
    return request(HttpMethod.DELETE, endpointName, requestArguments)
}

/**
 * Delete document from Veryfi. https://docs.veryfi.com/api/receipts-invoices/delete-a-document/
 * @param documentId ID of the document you'd like to delete.
 * @return the response data.
 */
fun Client.deleteDocumentAsync(documentId: String): CompletableFuture<String> {
    val endpointName = "/documents/$documentId/"
    val requestArguments = JSONObject()
    requestArguments.put("id", documentId)
    return requestAsync(HttpMethod.DELETE, endpointName, requestArguments)
}

