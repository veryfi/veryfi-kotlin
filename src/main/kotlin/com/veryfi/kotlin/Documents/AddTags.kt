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
private fun Client.getAddTagsArguments(
    tags: List<String>
): JSONObject {
    val requestArguments = JSONObject()
    requestArguments.put(TAGS, tags)
    return requestArguments
}

/**
 * Adds tags to an existing document
 * @param tags Tags ta be added
 * @return the added tags
 */
fun Client.addTags(
    documentId: String,
    tags: List<String>
): String {
    val endpointName = "/documents/${documentId}/tags"
    val requestArguments = getAddTagsArguments(tags)
    return request(HttpMethod.POST, endpointName, requestArguments)
}

/**
 * Adds tags to an existing document
 * @param tags Tags ta be added
 * @return the added tags
 */
fun Client.addTagsAsync(
    documentId: String,
    tags: List<String>
): CompletableFuture<String> {
    val endpointName = "/documents/${documentId}/tags"
    val requestArguments = getAddTagsArguments(tags)
    return requestAsync(HttpMethod.POST, endpointName, requestArguments)
}

