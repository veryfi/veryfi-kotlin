package com.veryfi.kotlin.documents

import com.veryfi.kotlin.Client
import com.veryfi.kotlin.Constants.AUTO_DELETE
import com.veryfi.kotlin.Constants.CATEGORIES
import com.veryfi.kotlin.Constants.FILE_DATA
import com.veryfi.kotlin.Constants.FILE_NAME
import com.veryfi.kotlin.Constants.LIST_CATEGORIES
import com.veryfi.kotlin.HttpMethod
import org.json.JSONObject
import java.io.File
import java.io.IOException
import java.util.Base64
import java.util.concurrent.CompletableFuture

/**
 * Creates the JSON Object for the parameters of the request
 * @param filePath Path on disk to a file to submit for data extraction
 * @param categories List of categories Veryfi can use to categorize the document
 * @param deleteAfterProcessing Delete this document from Veryfi after data has been extracted
 * @param parameters Additional request parameters
 * @return the JSON object of the parameters of the request
 */
private fun Client.getProcessDocumentArguments(
    filePath: String, categories: List<String?>?,
    deleteAfterProcessing: Boolean, parameters: JSONObject?
): JSONObject {
    var categoriesLocal = categories
    if (categoriesLocal == null || categoriesLocal.isEmpty()) {
        categoriesLocal = LIST_CATEGORIES
    }
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
    requestArguments.put(CATEGORIES, categoriesLocal)
    requestArguments.put(AUTO_DELETE, deleteAfterProcessing)
    if (parameters != null && !parameters.isEmpty) {
        for (key in JSONObject.getNames(parameters)) {
            requestArguments.put(key, parameters[key])
        }
    }
    return requestArguments
}

/**
 * Process a document and extract all the fields from it. https://docs.veryfi.com/api/receipts-invoices/process-a-document/
 * @param filePath Path on disk to a file to submit for data extraction
 * @param categories List of categories Veryfi can use to categorize the document
 * @param deleteAfterProcessing Delete this document from Veryfi after data has been extracted
 * @param parameters Additional request parameters
 * @return the data extracted from the Document [String]
 */
fun Client.processDocument(
    filePath: String, categories: List<String?>?, deleteAfterProcessing: Boolean,
    parameters: JSONObject?
): String {
    val endpointName = "/documents/"
    val requestArguments = getProcessDocumentArguments(filePath, categories, deleteAfterProcessing, parameters)
    return request(HttpMethod.POST, endpointName, requestArguments)
}

/**
 * Process a document and extract all the fields from it. https://docs.veryfi.com/api/receipts-invoices/process-a-document/
 * @param filePath Path on disk to a file to submit for data extraction
 * @param categories List of categories Veryfi can use to categorize the document
 * @param deleteAfterProcessing Delete this document from Veryfi after data has been extracted
 * @param parameters Additional request parameters
 * @return the data extracted from the Document
 */
fun Client.processDocumentAsync(
    filePath: String, categories: List<String?>?,
    deleteAfterProcessing: Boolean, parameters: JSONObject?
): CompletableFuture<String> {
    val endpointName = "/documents/"
    val requestArguments = getProcessDocumentArguments(filePath, categories, deleteAfterProcessing, parameters)
    return requestAsync(HttpMethod.POST, endpointName, requestArguments)
}

