package com.veryfi.kotlin.contracts

import com.veryfi.kotlin.Client
import com.veryfi.kotlin.HttpMethod
import org.json.JSONObject
import java.util.concurrent.CompletableFuture

/**
 * Get a Contract document
 * Returns a json string [String] Contract information
 * @param documentId ID of the Contract you'd like to retrieve.
 * @return the data extracted from the Contract [String]
 */
fun Client.getContract(documentId: String): String {
    val endpointName = "/contracts/$documentId/"
    val requestArguments = JSONObject()
    requestArguments.put("id", documentId)
    return request(HttpMethod.GET, endpointName, requestArguments)
}

/**
 * Get a Contract document
 * Returns a json string [String] Contract information
 * @param documentId ID of the Contract you'd like to retrieve.
 * @return the data extracted from the Contract [String]
 */
fun Client.getContractAsync(documentId: String): CompletableFuture<String> {
    val endpointName = "/contracts/$documentId/"
    val requestArguments = JSONObject()
    requestArguments.put("id", documentId)
    return requestAsync(HttpMethod.GET, endpointName, requestArguments)
}

