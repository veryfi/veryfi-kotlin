package com.veryfi.kotlin.documents

import com.veryfi.kotlin.Client
import com.veryfi.kotlin.HttpMethod
import org.json.JSONObject
import java.util.concurrent.CompletableFuture

/**
 * Get JSON of documents. https://docs.veryfi.com/api/receipts-invoices/search-documents/
 * Returns a json string [String] with the list of documents.
 * @param parameters Additional request parameters
 * @return the url [String]
 */
fun Client.getDocuments(parameters: JSONObject? = null): String {
    val endpointName = "/documents/"
    val requestArguments = JSONObject()
    if (parameters != null && !parameters.isEmpty) {
        for (key in JSONObject.getNames(parameters)) {
            requestArguments.put(key, parameters[key])
        }
    }
    return request(HttpMethod.GET, endpointName, requestArguments)
}

/**
 * Get JSON of documents. https://docs.veryfi.com/api/receipts-invoices/search-documents/
 * Returns a json string list of documents.
 * @param parameters Additional request parameters
 * @return the list of previously processed documents [String]
 */
fun Client.getDocumentsAsync(parameters: JSONObject? = null): CompletableFuture<String> {
    val endpointName = "/documents/"
    val requestArguments = JSONObject()
    if (parameters != null && !parameters.isEmpty) {
        for (key in JSONObject.getNames(parameters)) {
            requestArguments.put(key, parameters[key])
        }
    }
    return requestAsync(HttpMethod.GET, endpointName, requestArguments)
}

