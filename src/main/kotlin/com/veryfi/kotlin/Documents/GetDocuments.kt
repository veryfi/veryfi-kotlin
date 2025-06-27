package com.veryfi.kotlin.documents

import com.veryfi.kotlin.Client
import com.veryfi.kotlin.HttpMethod
import org.json.JSONObject
import java.util.concurrent.CompletableFuture

/**
 * Get JSON of documents. https://docs.veryfi.com/api/receipts-invoices/search-documents/
 * Returns a json string [String] with the list of documents.
 * @return the url [String]
 */
fun Client.getDocuments(): String {
    val endpointName = "/documents/"
    val requestArguments = JSONObject()
    return request(HttpMethod.GET, endpointName, requestArguments)
}

/**
 * Get JSON of documents. https://docs.veryfi.com/api/receipts-invoices/search-documents/
 * Returns a json string list of documents.
 * @return the list of previously processed documents [String]
 */
fun Client.getDocumentsAsync(): CompletableFuture<String> {
    val endpointName = "/documents/"
    val requestArguments = JSONObject()
    return requestAsync(HttpMethod.GET, endpointName, requestArguments)
}

