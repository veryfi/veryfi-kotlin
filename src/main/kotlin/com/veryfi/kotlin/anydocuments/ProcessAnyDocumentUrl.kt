package com.veryfi.kotlin.anydocuments

import com.veryfi.kotlin.Client
import com.veryfi.kotlin.Constants.FILE_URL
import com.veryfi.kotlin.Constants.FILE_URLS
import com.veryfi.kotlin.Constants.TEMPLATE_NAME
import com.veryfi.kotlin.HttpMethod
import org.json.JSONObject
import java.util.concurrent.CompletableFuture

/**
 * Creates the JSON object of the parameters of the request
 * @param fileUrl Required if file_urls isn't specified. Publicly accessible URL to a file, e.g. "https://cdn.example.com/receipt.jpg".
 * @param fileUrls Required if file_url isn't specifies. List of publicly accessible URLs to multiple files, e.g. ["https://cdn.example.com/receipt1.jpg", "https://cdn.example.com/receipt2.jpg"]
 * @param templateName picks the blueprint that will process the any document
 * @param parameters Additional request parameters
 * @return JSON object of the request arguments
 */
private fun Client.getProcessAnyDocumentUrlArguments(
    fileUrl: String?, fileUrls: List<String?>?, templateName: String,
    parameters: JSONObject?
): JSONObject {
    val requestArguments = JSONObject()
    requestArguments.put(TEMPLATE_NAME, templateName)
    if (fileUrl != null) {
        requestArguments.put(FILE_URL, fileUrl)
    }
    if (fileUrls != null) {
        requestArguments.put(FILE_URLS, fileUrls)
    }
    if (parameters != null && !parameters.isEmpty) {
        for (key in JSONObject.getNames(parameters)) {
            requestArguments.put(key, parameters[key])
        }
    }
    return requestArguments
}

/**
 * Process Any Document from url and extract all the fields from it.
 * @param fileUrl Required if file_urls isn't specified. Publicly accessible URL to a file, e.g. "https://cdn.example.com/receipt.jpg".
 * @param fileUrls Required if file_url isn't specifies. List of publicly accessible URLs to multiple files, e.g. ["https://cdn.example.com/receipt1.jpg", "https://cdn.example.com/receipt2.jpg"]
 * @param templateName picks the blueprint that will process the any document
 * @param parameters Additional request parameters
 * @return the data extracted from the Any Document [String]
 */
fun Client.processAnyDocumentUrl(
    fileUrl: String?, fileUrls: List<String?>?, templateName: String,
    parameters: JSONObject?
): String {
    val endpointName = "/any-documents/"
    val requestArguments = getProcessAnyDocumentUrlArguments(
        fileUrl,
        fileUrls,
        templateName,
        parameters
    )
    return request(HttpMethod.POST, endpointName, requestArguments)
}

/**
 * Process Any Document from url and extract all the fields from it.
 * @param fileUrl Required if file_urls isn't specified. Publicly accessible URL to a file, e.g. "https://cdn.example.com/receipt.jpg".
 * @param fileUrls Required if file_url isn't specifies. List of publicly accessible URLs to multiple files, e.g. ["https://cdn.example.com/receipt1.jpg", "https://cdn.example.com/receipt2.jpg"]
 * @param templateName picks the blueprint that will process the any document
 * @param parameters Additional request parameters
 * @return the data extracted from the Any Document
 */
fun Client.processAnyDocumentUrlAsync(
    fileUrl: String?, fileUrls: List<String?>?,
    templateName: String,
    parameters: JSONObject?
): CompletableFuture<String> {
    val endpointName = "/any-documents/"
    val requestArguments = getProcessAnyDocumentUrlArguments(
        fileUrl,
        fileUrls,
        templateName,
        parameters
    )
    return requestAsync(HttpMethod.POST, endpointName, requestArguments)
}

