package com.veryfi.kotlin.anydocuments

import com.veryfi.kotlin.Client
import com.veryfi.kotlin.Constants.FILE_URL
import com.veryfi.kotlin.Constants.FILE_URLS
import com.veryfi.kotlin.Constants.TEMPLATE_NAME
import com.veryfi.kotlin.Constants.MAX_PAGES_TO_PROCESS
import com.veryfi.kotlin.HttpMethod
import org.json.JSONObject
import java.util.concurrent.CompletableFuture

/**
 * Creates the JSON object of the parameters of the request
 * @param fileUrl Required if file_urls isn't specified. Publicly accessible URL to a file, e.g. "https://cdn.example.com/receipt.jpg".
 * @param fileUrls Required if file_url isn't specifies. List of publicly accessible URLs to multiple files, e.g. ["https://cdn.example.com/receipt1.jpg", "https://cdn.example.com/receipt2.jpg"]
 * @param templateName picks the blueprint that will process the any document
 * @param maxPagesToProcess When sending a long document to Veryfi for processing, this parameter controls how many pages of the document will be read and processed, starting from page 1.
 * @param parameters Additional request parameters
 * @return JSON object of the request arguments
 */
private fun Client.getProcessAnyDocumentUrlArguments(
    fileUrl: String, fileUrls: List<String?>?, templateName: String,
    maxPagesToProcess: Int,
    parameters: JSONObject?
): JSONObject {
    val requestArguments = JSONObject()
    requestArguments.put(TEMPLATE_NAME, templateName)
    requestArguments.put(FILE_URL, fileUrl)
    if (fileUrls != null) {
        requestArguments.put(FILE_URLS, fileUrls)
    }
    requestArguments.put(MAX_PAGES_TO_PROCESS, maxPagesToProcess)
    if (parameters != null && !parameters.isEmpty) {
        for (key in JSONObject.getNames(parameters)) {
            requestArguments.put(key, parameters[key])
        }
    }
    return requestArguments
}

/**
 * Process Document from url and extract all the fields from it.
 * @param fileUrl Required if file_urls isn't specified. Publicly accessible URL to a file, e.g. "https://cdn.example.com/receipt.jpg".
 * @param fileUrls Required if file_url isn't specifies. List of publicly accessible URLs to multiple files, e.g. ["https://cdn.example.com/receipt1.jpg", "https://cdn.example.com/receipt2.jpg"]
 * @param templateName picks the blueprint that will process the any document
 * @param maxPagesToProcess When sending a long document to Veryfi for processing, this parameter controls how many pages of the document will be read and processed, starting from page 1.
 * @param parameters Additional request parameters
 * @return the data extracted from the Document [String]
 */
fun Client.processAnyDocumentUrl(
    fileUrl: String, fileUrls: List<String?>?, templateName: String,
    maxPagesToProcess: Int,
    parameters: JSONObject?
): String {
    val endpointName = "/any-documents/"
    val requestArguments = getProcessAnyDocumentUrlArguments(
        fileUrl,
        fileUrls,
        templateName,
        maxPagesToProcess,
        parameters
    )
    return request(HttpMethod.POST, endpointName, requestArguments)
}

/**
 * Process Document from url and extract all the fields from it.
 * @param fileUrl Required if file_urls isn't specified. Publicly accessible URL to a file, e.g. "https://cdn.example.com/receipt.jpg".
 * @param fileUrls Required if file_url isn't specifies. List of publicly accessible URLs to multiple files, e.g. ["https://cdn.example.com/receipt1.jpg", "https://cdn.example.com/receipt2.jpg"]
 * @param templateName picks the blueprint that will process the any document
 * @param maxPagesToProcess When sending a long document to Veryfi for processing, this parameter controls how many pages of the document will be read and processed, starting from page 1.
 * @param parameters Additional request parameters
 * @return the data extracted from the Document
 */
fun Client.processAnyDocumentUrlAsync(
    fileUrl: String, fileUrls: List<String?>?,
    templateName: String,
    maxPagesToProcess: Int,
    parameters: JSONObject?
): CompletableFuture<String> {
    val endpointName = "/any-documents/"
    val requestArguments = getProcessAnyDocumentUrlArguments(
        fileUrl,
        fileUrls,
        templateName,
        maxPagesToProcess,
        parameters
    )
    return requestAsync(HttpMethod.POST, endpointName, requestArguments)
}

