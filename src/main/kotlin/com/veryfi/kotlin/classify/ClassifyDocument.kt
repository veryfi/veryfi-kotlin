package com.veryfi.kotlin.classify

import com.veryfi.kotlin.Client
import com.veryfi.kotlin.HttpMethod
import org.json.JSONObject
import java.util.concurrent.CompletableFuture

/**
 * Classify a document and extract all the fields from it. https://docs.veryfi.com/api/classify/classify-a-document/
 * @param filePath Path on disk to a file to submit for data extraction
 * @param parameters Additional request parameters
 * @return the data extracted from the document [String]
 */
fun Client.classifyDocument(
    filePath: String,
    parameters: JSONObject?
): String {
    val endpointName = "/classify/"
    val requestArguments = getArguments(filePath, parameters)
    return request(HttpMethod.POST, endpointName, requestArguments)
}

/**
 * Classify a document and extract all the fields from it. https://docs.veryfi.com/api/receipts-invoices/process-a-document/
 * @param filePath Path on disk to a file to submit for data extraction
 * @param parameters Additional request parameters
 * @return the data extracted from the document
 */
fun Client.classifyDocumentAsync(
    filePath: String,
    parameters: JSONObject?
): CompletableFuture<String> {
    val endpointName = "/classify/"
    val requestArguments = getArguments(filePath, parameters)
    return requestAsync(HttpMethod.POST, endpointName, requestArguments)
}
