package com.veryfi.kotlin.split

import com.veryfi.kotlin.Client
import com.veryfi.kotlin.HttpMethod
import org.json.JSONObject
import java.util.concurrent.CompletableFuture

/**
 * Split document PDF from url and extract all the fields from it. https://docs.veryfi.com/api/receipts-invoices/split-and-process-a-pdf/
 * @param filePath Path on disk to a file to submit for data extraction
 * @param parameters Additional request parameters
 * @return the data extracted from the document [String]
 */
fun Client.splitDocument(
    filePath: String,
    parameters: JSONObject?
): String {
    val endpointName = "/documents-set/"
    val requestArguments = getArguments(filePath, parameters)
    return request(HttpMethod.POST, endpointName, requestArguments)
}

/**
 * Split document PDF from url and extract all the fields from it. https://docs.veryfi.com/api/receipts-invoices/split-and-process-a-pdf/
 * @param filePath Path on disk to a file to submit for data extraction
 * @param parameters Additional request parameters
 * @return the data extracted from the document
 */
fun Client.splitDocumentAsync(
    filePath: String,
    parameters: JSONObject?
): CompletableFuture<String> {
    val endpointName = "/documents-set/"
    val requestArguments = getArguments(filePath, parameters)
    return requestAsync(HttpMethod.POST, endpointName, requestArguments)
}
