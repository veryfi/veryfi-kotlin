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
private fun Client.getProcessContractArguments(
    filePath: String,
    parameters: JSONObject?
): JSONObject {
    val fileName = filePath.replace("^.*[/\\\\]".toRegex(), "")
    val file = File(filePath)
    var base64EncodedString: String? = ""
    try {
        if (file.exists() && file.canRead()) {
            base64EncodedString = Base64.getEncoder().encodeToString(file.readBytes())
        }
    } catch (e: IOException) {
        logger.severe(e.message)
    }
    val requestArguments = JSONObject()
    requestArguments.put(FILE_NAME, fileName)
    requestArguments.put(FILE_DATA, base64EncodedString)
    if (parameters != null && !parameters.isEmpty) {
        for (key in JSONObject.getNames(parameters)) {
            requestArguments.put(key, parameters[key])
        }
    }
    return requestArguments
}

/**
 * Upload a Contract from a file path
 * @param filePath Path on disk to a file to submit for data extraction
 * @param parameters Additional request parameters
 * @return the data extracted from the Contract String
 */
fun Client.processContract(
    filePath: String,
    parameters: JSONObject?
): String {
    val endpointName = "/contracts/"
    val requestArguments = getProcessContractArguments(filePath, parameters)
    return request(HttpMethod.POST, endpointName, requestArguments)
}

/**
 * Upload a Contract from a file path
 * @param filePath Path on disk to a file to submit for data extraction
 * @param parameters Additional request parameters
 * @return the data extracted from the Contract String
 */
fun Client.processContractAsync(
    filePath: String,
    parameters: JSONObject?
): CompletableFuture<String> {
    val endpointName = "/contracts/"
    val requestArguments = getProcessContractArguments(filePath, parameters)
    return requestAsync(HttpMethod.POST, endpointName, requestArguments)
}

