package com.veryfi.kotlin.contracts

import com.veryfi.kotlin.Client
import com.veryfi.kotlin.HttpMethod
import org.json.JSONObject
import java.util.concurrent.CompletableFuture

/**
 * Delete Contract from Veryfi. https://docs.veryfi.com/api/contracts/delete-a-contract/
 * @param documentId ID of the Contract you'd like to delete.
 * @return the response data. [String]
 */
fun Client.deleteContract(documentId: String): String {
    val endpointName = "/contracts/$documentId/"
    val requestArguments = JSONObject()
    requestArguments.put("id", documentId)
    return request(HttpMethod.DELETE, endpointName, requestArguments)
}

/**
 * Delete Contract from Veryfi. https://docs.veryfi.com/api/contracts/delete-a-contract/
 * @param documentId ID of the Contract you'd like to delete.
 * @return the response data. [CompletableFuture]
 */
fun Client.deleteContractAsync(documentId: String): CompletableFuture<String> {
    val endpointName = "/contracts/$documentId/"
    val requestArguments = JSONObject()
    requestArguments.put("id", documentId)
    return requestAsync(HttpMethod.DELETE, endpointName, requestArguments)
} 