package com.veryfi.kotlin.bankstatements

import com.veryfi.kotlin.Client
import com.veryfi.kotlin.HttpMethod
import org.json.JSONObject
import java.util.concurrent.CompletableFuture

/**
 * Delete Bank Statement from Veryfi. https://docs.veryfi.com/api/bank-statements/delete-a-bank-statement/
 * @param documentId ID of the Bank Statement you'd like to delete.
 * @return the response data. [String]
 */
fun Client.deleteBankStatement(documentId: String): String {
    val endpointName = "/bank-statements/$documentId/"
    val requestArguments = JSONObject()
    requestArguments.put("id", documentId)
    return request(HttpMethod.DELETE, endpointName, requestArguments)
}

/**
 * Delete Bank Statement from Veryfi. https://docs.veryfi.com/api/bank-statements/delete-a-bank-statement/
 * @param documentId ID of the Bank Statement you'd like to delete.
 * @return the response data. [CompletableFuture]
 */
fun Client.deleteBankStatementAsync(documentId: String): CompletableFuture<String> {
    val endpointName = "/bank-statements/$documentId/"
    val requestArguments = JSONObject()
    requestArguments.put("id", documentId)
    return requestAsync(HttpMethod.DELETE, endpointName, requestArguments)
} 