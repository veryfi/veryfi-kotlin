package com.veryfi.kotlin.documents

import com.veryfi.kotlin.Client
import com.veryfi.kotlin.Constants.ORDER
import com.veryfi.kotlin.Constants.DESCRIPTION
import com.veryfi.kotlin.Constants.TOTAL
import com.veryfi.kotlin.HttpMethod
import org.json.JSONObject
import java.util.concurrent.CompletableFuture

/**
 * Creates the JSON Object for the parameters of the request
 * @param order Order of line item
 * @param description Description of line item
 * @param total Total of line item
 * @return the JSON object of the parameters of the request
 */
private fun Client.getAddLineItemArguments(
    order: Int, description: String,
    total: Float
): JSONObject {
    val requestArguments = JSONObject()
    requestArguments.put(ORDER, order)
    requestArguments.put(DESCRIPTION, description)
    requestArguments.put(TOTAL, total)
    return requestArguments
}

/**
 * Adds a line item to an existing document
 * @param order Order of line item
 * @param description Description of line item
 * @param total Total of line item
 * @return the line item added
 */
fun Client.addLineItem(
    documentId: String,
    order: Int, description: String,
    total: Float
): String {
    val endpointName = "/documents/${documentId}/line-items"
    val requestArguments = getAddLineItemArguments(order, description, total)
    return request(HttpMethod.POST, endpointName, requestArguments)
}

/**
 * Adds a line item to an existing document
 * @param order Order of line item
 * @param description Description of line item
 * @param total Total of line item
 * @return the line item added
 */
fun Client.addLineItemAsync(
    documentId: String,
    order: Int, description: String,
    total: Float
): CompletableFuture<String> {
    val endpointName = "/documents/${documentId}/line-items"
    val requestArguments = getAddLineItemArguments(order, description, total)
    return requestAsync(HttpMethod.POST, endpointName, requestArguments)
}

