package com.veryfi.kotlin.documents

import com.veryfi.kotlin.Client
import com.veryfi.kotlin.HttpMethod
import org.json.JSONObject
import java.util.concurrent.CompletableFuture

/**
 * Returns a json string [String] document information
 * @param documentId ID of the document you'd like to retrieve.
 * @return the data extracted from the Document [String]
 */
fun Client.getDocument(documentId: String): String {
    val endpointName = "/documents/$documentId/"
    val requestArguments = JSONObject()
    requestArguments.put("id", documentId)
    return request(HttpMethod.GET, endpointName, requestArguments)
}

/**
 * Returns a json string document information.
 * @param documentId ID of the document you'd like to retrieve.
 * @return the data extracted from the Document [String]
 */
fun Client.getDocumentAsync(documentId: String): CompletableFuture<String> {
    val endpointName = "/documents/$documentId/"
    val requestArguments = JSONObject()
    requestArguments.put("id", documentId)
    return requestAsync(HttpMethod.GET, endpointName, requestArguments)
}

