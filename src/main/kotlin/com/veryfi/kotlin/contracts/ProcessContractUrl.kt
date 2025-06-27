package com.veryfi.kotlin.contracts

import com.veryfi.kotlin.Client
import com.veryfi.kotlin.Constants.FILE_URL
import com.veryfi.kotlin.Constants.FILE_URLS
import com.veryfi.kotlin.HttpMethod
import org.json.JSONObject
import java.util.concurrent.CompletableFuture

/**
 * Creates the JSON object of the parameters of the request
 * @param fileUrl Required if file_urls isn't specified. Publicly accessible URL to a file, e.g. "https://cdn.example.com/receipt.jpg".
 * @param parameters Additional request parameters
 * @return JSON object of the request arguments
 */
private fun Client.getProcessContractUrlArguments(
    fileUrl: String?,
    parameters: JSONObject?
): JSONObject {
    val requestArguments = JSONObject()
    if (fileUrl != null) {
        requestArguments.put(FILE_URL, fileUrl)
    }
    if (parameters != null && !parameters.isEmpty) {
        for (key in JSONObject.getNames(parameters)) {
            requestArguments.put(key, parameters[key])
        }
    }
    return requestArguments
}

/**
 * Process a Contract document from an url.
 * @param fileUrl Required if file_urls isn't specified. Publicly accessible URL to a file, e.g. "https://cdn.example.com/receipt.jpg".
 * @param parameters Additional request parameters
 * @return the data extracted from theContract
 */
fun Client.processContractUrl(
    fileUrl: String?,
    parameters: JSONObject?
): String {
    val endpointName = "/contracts/"
    val requestArguments = getProcessContractUrlArguments(
        fileUrl,
        parameters
    )
    return request(HttpMethod.POST, endpointName, requestArguments)
}

/**
 * Process a Contract document from an url.
 * @param fileUrl Required if file_urls isn't specified. Publicly accessible URL to a file, e.g. "https://cdn.example.com/receipt.jpg".
 * @param parameters Additional request parameters
 * @return the data extracted from the Contract
 */
fun Client.processContractUrlAsync(
    fileUrl: String?,
    parameters: JSONObject?
): CompletableFuture<String> {
    val endpointName = "/contracts/"
    val requestArguments = getProcessContractUrlArguments(
        fileUrl,
        parameters
    )
    return requestAsync(HttpMethod.POST, endpointName, requestArguments)
}

