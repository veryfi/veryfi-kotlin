package com.veryfi.kotlin.businessCards

import com.veryfi.kotlin.Client
import com.veryfi.kotlin.HttpMethod
import org.json.JSONObject
import java.util.concurrent.CompletableFuture

/**
 * Delete Business Card from Veryfi. https://docs.veryfi.com/api/business-cards/delete-a-business-card/
 * @param documentId ID of the Business Card you'd like to delete.
 * @return the response data. [String]
 */
fun Client.deleteBusinessCard(documentId: String): String {
    val endpointName = "/business-cards/$documentId/"
    val requestArguments = JSONObject()
    requestArguments.put("id", documentId)
    return request(HttpMethod.DELETE, endpointName, requestArguments)
}

/**
 * Delete Business Card from Veryfi. https://docs.veryfi.com/api/business-cards/delete-a-business-card/
 * @param documentId ID of the Business Card you'd like to delete.
 * @return the response data. [CompletableFuture]
 */
fun Client.deleteBusinessCardAsync(documentId: String): CompletableFuture<String> {
    val endpointName = "/business-cards/$documentId/"
    val requestArguments = JSONObject()
    requestArguments.put("id", documentId)
    return requestAsync(HttpMethod.DELETE, endpointName, requestArguments)
} 