package com.veryfi.kotlin.businessCards

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
private fun Client.getProcessBusinessCardArguments(
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
 * Process Business card and extract all the fields from it. https://docs.veryfi.com/api/business-cards/process-a-business-card/
 * @param filePath Path on disk to a file to submit for data extraction
 * @param parameters Additional request parameters
 * @return the data extracted from the Business Card String]
 */
fun Client.processBusinessCard(
    filePath: String,
    parameters: JSONObject?
): String {
    val endpointName = "/business-cards/"
    val requestArguments = getProcessBusinessCardArguments(filePath, parameters)
    return request(HttpMethod.POST, endpointName, requestArguments)
}

/**
 * Process Business card and extract all the fields from it. https://docs.veryfi.com/api/business-cards/process-a-business-card/
 * @param filePath Path on disk to a file to submit for data extraction
 * @param parameters Additional request parameters
 * @return the data extracted from the Business Card String]
 */
fun Client.processBusinessCardAsync(
    filePath: String,
    parameters: JSONObject?
): CompletableFuture<String> {
    val endpointName = "/business-cards/"
    val requestArguments = getProcessBusinessCardArguments(filePath, parameters)
    return requestAsync(HttpMethod.POST, endpointName, requestArguments)
}

