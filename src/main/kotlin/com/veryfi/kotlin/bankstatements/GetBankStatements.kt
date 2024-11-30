package com.veryfi.kotlin.bankstatements

import com.veryfi.kotlin.Client
import com.veryfi.kotlin.HttpMethod
import org.json.JSONObject
import java.util.concurrent.CompletableFuture

/**
 * Returns a json string [String] with the list of bank statements.
 * @return the url [String]
 */
fun Client.getBankStatements(): String {
    val endpointName = "/bank-statements/"
    val requestArguments = JSONObject()
    return request(HttpMethod.GET, endpointName, requestArguments)
}

/**
 * Returns a json string list of bank statements.
 * @return the list of previously processed bank statements [String]
 */
fun Client.getBankStatementsAsync(): CompletableFuture<String> {
    val endpointName = "/bank-statements/"
    val requestArguments = JSONObject()
    return requestAsync(HttpMethod.GET, endpointName, requestArguments)
}

