package com.veryfi.kotlin.documents

import com.veryfi.kotlin.Client
import com.veryfi.kotlin.Constants.NAME
import com.veryfi.kotlin.HttpMethod
import org.json.JSONObject
import java.util.concurrent.CompletableFuture

/**
 * Creates the JSON Object for the parameters of the request
 * @param name Name of tag
 * @return the JSON object of the parameters of the request
 */
private fun Client.getAddTagArguments(
    name: String
): JSONObject {
    val requestArguments = JSONObject()
    requestArguments.put(NAME, name)
    return requestArguments
}

/**
 * Add a new tag on an existing document. https://docs.veryfi.com/api/receipts-invoices/add-a-tag-to-a-document/
 * @param name Name of tag
 * @return the added tag
 */
fun Client.addTag(
    documentId: String,
    name: String
): String {
    val endpointName = "/documents/${documentId}/tags"
    val requestArguments = getAddTagArguments(name)
    return request(HttpMethod.PUT, endpointName, requestArguments)
}

/**
 * Add a new tag on an existing document. https://docs.veryfi.com/api/receipts-invoices/add-a-tag-to-a-document/
 * @param name Name of tag
 * @return the added tag
 */
fun Client.addTagAsync(
    documentId: String,
    name: String
): CompletableFuture<String> {
    val endpointName = "/documents/${documentId}/tags"
    val requestArguments = getAddTagArguments(name)
    return requestAsync(HttpMethod.PUT, endpointName, requestArguments)
}

