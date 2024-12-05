package com.veryfi.kotlin.bankstatements

import com.veryfi.kotlin.Client
import com.veryfi.kotlin.HttpMethod
import org.json.JSONObject
import java.util.concurrent.CompletableFuture

/**
 * Get a specific bank statement. https://docs.veryfi.com/api/bank-statements/get-a-bank-statement/
 * Returns a json string [String] bank statement information
 * @param documentId ID of the bank statement you'd like to retrieve.
 * @return the data extracted from the Bank Statement [String]
 */
fun Client.getBankStatement(documentId: String): String {
    val endpointName = "/bank-statements/$documentId/"
    val requestArguments = JSONObject()
    requestArguments.put("id", documentId)
    return request(HttpMethod.GET, endpointName, requestArguments)
}

/**
 * Get a specific bank statement. https://docs.veryfi.com/api/bank-statements/get-a-bank-statement/
 * Returns a json string [String] bank statement information
 * @param documentId ID of the bank statement you'd like to retrieve.
 * @return the data extracted from the Bank Statement [String]
 */
fun Client.getBankStatementAsync(documentId: String): CompletableFuture<String> {
    val endpointName = "/bank-statements/$documentId/"
    val requestArguments = JSONObject()
    requestArguments.put("id", documentId)
    return requestAsync(HttpMethod.GET, endpointName, requestArguments)
}

