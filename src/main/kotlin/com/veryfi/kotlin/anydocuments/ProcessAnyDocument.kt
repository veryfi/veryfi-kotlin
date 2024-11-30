package com.veryfi.kotlin.anydocuments

import com.veryfi.kotlin.Client
import com.veryfi.kotlin.Constants.FILE_DATA
import com.veryfi.kotlin.Constants.FILE_NAME
import com.veryfi.kotlin.Constants.TEMPLATE_NAME
import com.veryfi.kotlin.HttpMethod
import org.json.JSONObject
import java.io.File
import java.io.IOException
import java.util.Base64
import java.util.concurrent.CompletableFuture

/**
 * Creates the JSON Object for the parameters of the request
 * @param filePath Path on disk to a file to submit for data extraction
 * @param templateName picks the blueprint that will process the any document
 * @param parameters Additional request parameters
 * @return the JSON object of the parameters of the request
 */
private fun Client.getProcessAnyDocumentArguments(
    filePath: String, templateName: String,
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
    requestArguments.put(TEMPLATE_NAME, templateName)
    if (parameters != null && !parameters.isEmpty) {
        for (key in JSONObject.getNames(parameters)) {
            requestArguments.put(key, parameters[key])
        }
    }
    return requestArguments
}

/**
 * Process a any document and extract all the fields from it
 * @param filePath Path on disk to a file to submit for data extraction
 * @param templateName picks the blueprint that will process the any document
 * @param parameters Additional request parameters
 * @return the data extracted from the Any Document [String]
 */
fun Client.processAnyDocument(
    filePath: String, templateName: String,
    parameters: JSONObject?
): String {
    val endpointName = "/any-documents/"
    val requestArguments = getProcessAnyDocumentArguments(filePath, templateName, parameters)
    return request(HttpMethod.POST, endpointName, requestArguments)
}

/**
 * Process a any document and extract all the fields from it
 * @param filePath Path on disk to a file to submit for data extraction
 * @param templateName picks the blueprint that will process the any document
 * @param parameters Additional request parameters
 * @return the data extracted from the Any Document
 */
fun Client.processAnyDocumentAsync(
    filePath: String, templateName: String,
    parameters: JSONObject?
): CompletableFuture<String> {
    val endpointName = "/any-documents/"
    val requestArguments = getProcessAnyDocumentArguments(filePath, templateName, parameters)
    return requestAsync(HttpMethod.POST, endpointName, requestArguments)
}

