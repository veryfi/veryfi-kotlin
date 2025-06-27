package com.veryfi.kotlin.businessCards

import com.veryfi.kotlin.Client
import com.veryfi.kotlin.HttpMethod
import org.json.JSONObject
import java.util.concurrent.CompletableFuture

/**
 * Get a specific Business card. https://docs.veryfi.com/api/business-cards/get-a-business-card/
 * Returns a json string [String] business card information
 * @param documentId ID of the business card you'd like to retrieve.
 * @return the data extracted from the Business card [String]
 */
fun Client.getBusinessCard(documentId: String): String {
    val endpointName = "/business-cards/$documentId/"
    val requestArguments = JSONObject()
    requestArguments.put("id", documentId)
    return request(HttpMethod.GET, endpointName, requestArguments)
}

/**
 * Get a specific Business card. https://docs.veryfi.com/api/business-cards/get-a-business-card/
 * Returns a json string [String] business card information
 * @param documentId ID of the business card you'd like to retrieve.
 * @return the data extracted from the Business card [String]
 */
fun Client.getBusinessCardAsync(documentId: String): CompletableFuture<String> {
    val endpointName = "/business-cards/$documentId/"
    val requestArguments = JSONObject()
    requestArguments.put("id", documentId)
    return requestAsync(HttpMethod.GET, endpointName, requestArguments)
}

