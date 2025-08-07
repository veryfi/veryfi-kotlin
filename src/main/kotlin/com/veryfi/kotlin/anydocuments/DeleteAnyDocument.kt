package com.veryfi.kotlin.anydocuments

import com.veryfi.kotlin.Client
import com.veryfi.kotlin.HttpMethod
import org.json.JSONObject
import java.util.concurrent.CompletableFuture

/**
 * Delete Any Document from Veryfi. https://docs.veryfi.com/api/anydocs/delete-a-A-doc/
 * @param documentId ID of the Any Document you'd like to delete.
 * @return the response data. [String]
 */
fun Client.deleteAnyDocument(documentId: String): String {
    val endpointName = "/any-documents/$documentId/"
    val requestArguments = JSONObject()
    requestArguments.put("id", documentId)
    return request(HttpMethod.DELETE, endpointName, requestArguments)
}

/**
 * Delete Any Document from Veryfi. https://docs.veryfi.com/api/anydocs/delete-a-A-doc/
 * @param documentId ID of the Any Document you'd like to delete.
 * @return the response data. [CompletableFuture]
 */
fun Client.deleteAnyDocumentAsync(documentId: String): CompletableFuture<String> {
    val endpointName = "/any-documents/$documentId/"
    val requestArguments = JSONObject()
    requestArguments.put("id", documentId)
    return requestAsync(HttpMethod.DELETE, endpointName, requestArguments)
}
