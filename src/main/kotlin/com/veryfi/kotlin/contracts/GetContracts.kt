package com.veryfi.kotlin.contracts

import com.veryfi.kotlin.Client
import com.veryfi.kotlin.Constants.FILE_DATA
import com.veryfi.kotlin.Constants.FILE_NAME
import com.veryfi.kotlin.HttpMethod
import org.json.JSONObject
import java.io.File
import java.io.IOException
import java.util.Base64
import java.util.concurrent.CompletableFuture

/**
 * Creates the JSON Object for the parameters of the request
 * @param filePath Path on disk to a file to submit for data extraction
 * @param parameters Additional request parameters
 * @return the JSON object of the parameters of the request
 */
private fun Client.getContractsArguments(
    parameters: JSONObject?
): JSONObject {
    val requestArguments = JSONObject()
    if (parameters != null && !parameters.isEmpty) {
        for (key in JSONObject.getNames(parameters)) {
            requestArguments.put(key, parameters[key])
        }
    }
    return requestArguments
}

/**
 * Get all Contract documents.
 * Returns a json string [String] with the list of Contracts.
 * @return the url [String]
 */
fun Client.getContracts(
    parameters: JSONObject?
): String {
    val endpointName = "/contracts/"
    val requestArguments = getContractsArguments(parameters)
    return request(HttpMethod.GET, endpointName, requestArguments)
}

/**
 * Get all Contract documents.
 * Returns a json string list of Contracts.
 * @return the list of previously processed Contracts [String]
 */
fun Client.getContractsAsync(
    parameters: JSONObject?
): CompletableFuture<String> {
    val endpointName = "/contracts/"
    val requestArguments = getContractsArguments(parameters)
    return requestAsync(HttpMethod.GET, endpointName, requestArguments)
}

