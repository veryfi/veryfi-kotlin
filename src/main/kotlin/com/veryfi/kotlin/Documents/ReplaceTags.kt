package com.veryfi.kotlin.documents

import com.veryfi.kotlin.Client
import com.veryfi.kotlin.Constants.TAGS
import com.veryfi.kotlin.HttpMethod
import org.json.JSONObject
import java.util.concurrent.CompletableFuture

/**
 * Creates the JSON Object for the parameters of the request
 * @param tags Tags ta be added
 * @return the JSON object of the parameters of the request
 */
private fun Client.getReplaceTagsArguments(
    tags: List<String>
): JSONObject {
    val requestArguments = JSONObject()
    requestArguments.put(TAGS, tags)
    return requestArguments
}

/**
 * Replaces tags to an existing document
 * @param tags Tags ta be added
 * @return the document with replaced tags
 */
fun Client.replaceTags(
    documentId: String,
    tags: List<String>
): String {
    val endpointName = "/documents/${documentId}"
    val requestArguments = getReplaceTagsArguments(tags)
    return request(HttpMethod.PUT, endpointName, requestArguments)
}

/**
 * Replaces tags to an existing document
 * @param tags Tags ta be added
 * @return the document with replaced tags
 */
fun Client.replaceTagsAsync(
    documentId: String,
    tags: List<String>
): CompletableFuture<String> {
    val endpointName = "/documents/${documentId}"
    val requestArguments = getReplaceTagsArguments(tags)
    return requestAsync(HttpMethod.PUT, endpointName, requestArguments)
}

