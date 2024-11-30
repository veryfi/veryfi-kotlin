package com.veryfi.kotlin.anydocuments

import com.veryfi.kotlin.Client
import com.veryfi.kotlin.HttpMethod
import org.json.JSONObject
import java.util.concurrent.CompletableFuture

/**
 * Returns a json string [String] with the list of any documents.
 * @return the url [String]
 */
fun Client.getAnyDocuments(): String {
    val endpointName = "/any-documents/"
    val requestArguments = JSONObject()
    return request(HttpMethod.GET, endpointName, requestArguments)
}

/**
 * Returns a json string list of any documents.
 * @return the list of previously processed any documents [String]
 */
fun Client.getAnyDocumentsAsync(): CompletableFuture<String> {
    val endpointName = "/any-documents/"
    val requestArguments = JSONObject()
    return requestAsync(HttpMethod.GET, endpointName, requestArguments)
}

