package com.veryfi.kotlin.checks

import com.veryfi.kotlin.Client
import com.veryfi.kotlin.HttpMethod
import org.json.JSONObject
import java.util.concurrent.CompletableFuture

/**
 * Get all checks. https://docs.veryfi.com/api/checks/get-checks/
 * Returns a json string [String] with the list of checks.
 * @param parameters Additional request parameters
 * @return the url [String]
 */
fun Client.getChecks(parameters: JSONObject? = null): String {
    val endpointName = "/checks/"
    val requestArguments = JSONObject()
    if (parameters != null && !parameters.isEmpty) {
        for (key in JSONObject.getNames(parameters)) {
            requestArguments.put(key, parameters[key])
        }
    }
    return request(HttpMethod.GET, endpointName, requestArguments)
}

/**
 * Get all checks. https://docs.veryfi.com/api/checks/get-checks/
 * Returns a json string list of checks.
 * @param parameters Additional request parameters
 * @return the list of previously processed checks [String]
 */
fun Client.getChecksAsync(parameters: JSONObject? = null): CompletableFuture<String> {
    val endpointName = "/checks/"
    val requestArguments = JSONObject()
    if (parameters != null && !parameters.isEmpty) {
        for (key in JSONObject.getNames(parameters)) {
            requestArguments.put(key, parameters[key])
        }
    }
    return requestAsync(HttpMethod.GET, endpointName, requestArguments)
} 