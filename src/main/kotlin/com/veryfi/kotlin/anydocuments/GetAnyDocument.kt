package com.veryfi.kotlin.anydocuments

import com.veryfi.kotlin.Client
import com.veryfi.kotlin.HttpMethod
import org.json.JSONObject
import java.util.concurrent.CompletableFuture

/**
 * Returns a json string [String] any document information
 * @param documentId ID of the any document you'd like to retrieve.
 * @return the data extracted from the AnyDocument [String]
 */
fun Client.getAnyDocument(documentId: String): String {
    val endpointName = "/any-documents/$documentId/"
    val requestArguments = JSONObject()
    requestArguments.put("id", documentId)
    return request(HttpMethod.GET, endpointName, requestArguments)
}

/**
 * Returns a json string any document information.
 * @param documentId ID of the any document you'd like to retrieve.
 * @return the data extracted from the AnyDocument [String]
 */
fun Client.getAnyDocumentAsync(documentId: String): CompletableFuture<String> {
    val endpointName = "/any-documents/$documentId/"
    val requestArguments = JSONObject()
    requestArguments.put("id", documentId)
    return requestAsync(HttpMethod.GET, endpointName, requestArguments)
}

