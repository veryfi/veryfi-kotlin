package com.veryfi.kotlin.bankstatements

import com.veryfi.kotlin.Client
import com.veryfi.kotlin.HttpMethod
import org.json.JSONObject
import java.util.concurrent.CompletableFuture

/**
 * Get all bank statements. https://docs.veryfi.com/api/bank-statements/get-bank-statements/
 * Returns a json string [String] with the list of bank statements.
 * @param parameters Additional request parameters
 * @return the url [String]
 */
fun Client.getBankStatements(parameters: JSONObject? = null): String {
    val endpointName = "/bank-statements/"
    val requestArguments = JSONObject()
    if (parameters != null && !parameters.isEmpty) {
        for (key in JSONObject.getNames(parameters)) {
            requestArguments.put(key, parameters[key])
        }
    }
    return request(HttpMethod.GET, endpointName, requestArguments)
}

/**
 * Get all bank statements. https://docs.veryfi.com/api/bank-statements/get-bank-statements/
 * Returns a json string list of bank statements.
 * @param parameters Additional request parameters
 * @return the list of previously processed bank statements [String]
 */
fun Client.getBankStatementsAsync(parameters: JSONObject? = null): CompletableFuture<String> {
    val endpointName = "/bank-statements/"
    val requestArguments = JSONObject()
    if (parameters != null && !parameters.isEmpty) {
        for (key in JSONObject.getNames(parameters)) {
            requestArguments.put(key, parameters[key])
        }
    }
    return requestAsync(HttpMethod.GET, endpointName, requestArguments)
}

