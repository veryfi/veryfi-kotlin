package com.veryfi.kotlin.anydocuments

import com.veryfi.kotlin.Client
import com.veryfi.kotlin.HttpMethod
import org.json.JSONObject
import java.util.concurrent.CompletableFuture

/**
 * Get all any documents. https://docs.veryfi.com/api/anydocs/get-%E2%88%80-docs/
 * Returns a json string [String] with the list of any documents.
 * @param parameters Additional request parameters
 * @return the url [String]
 */
fun Client.getAnyDocuments(parameters: JSONObject? = null): String {
    val endpointName = "/any-documents/"
    val requestArguments = JSONObject()
    if (parameters != null && !parameters.isEmpty) {
        for (key in JSONObject.getNames(parameters)) {
            requestArguments.put(key, parameters[key])
        }
    }
    return request(HttpMethod.GET, endpointName, requestArguments)
}

/**
 * Get all any documents. https://docs.veryfi.com/api/anydocs/get-%E2%88%80-docs/
 * Returns a json string list of any documents.
 * @param parameters Additional request parameters
 * @return the list of previously processed any documents [String]
 */
fun Client.getAnyDocumentsAsync(parameters: JSONObject? = null): CompletableFuture<String> {
    val endpointName = "/any-documents/"
    val requestArguments = JSONObject()
    if (parameters != null && !parameters.isEmpty) {
        for (key in JSONObject.getNames(parameters)) {
            requestArguments.put(key, parameters[key])
        }
    }
    return requestAsync(HttpMethod.GET, endpointName, requestArguments)
}

