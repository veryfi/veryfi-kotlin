package com.veryfi.kotlin.w2s

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
private fun Client.getProcessW2Arguments(
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
 * Upload a document from a file path https://docs.veryfi.com/api/w2s/process-a-w-2/
 * @param filePath Path on disk to a file to submit for data extraction
 * @param parameters Additional request parameters
 * @return the data extracted from the W2 String]
 */
fun Client.processW2(
    filePath: String,
    parameters: JSONObject?
): String {
    val endpointName = "/w2s/"
    val requestArguments = getProcessW2Arguments(filePath, parameters)
    return request(HttpMethod.POST, endpointName, requestArguments)
}

/**
 * Upload a document from a file path https://docs.veryfi.com/api/w2s/process-a-w-2/
 * @param filePath Path on disk to a file to submit for data extraction
 * @param parameters Additional request parameters
 * @return the data extracted from the W2 String]
 */
fun Client.processW2Async(
    filePath: String,
    parameters: JSONObject?
): CompletableFuture<String> {
    val endpointName = "/w2s/"
    val requestArguments = getProcessW2Arguments(filePath, parameters)
    return requestAsync(HttpMethod.POST, endpointName, requestArguments)
}

