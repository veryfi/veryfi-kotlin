package com.veryfi.kotlin.documents

import com.veryfi.kotlin.Client
import com.veryfi.kotlin.Constants.AUTO_DELETE
import com.veryfi.kotlin.Constants.BOOST_MODE
import com.veryfi.kotlin.Constants.CATEGORIES
import com.veryfi.kotlin.Constants.EXTERNAL_ID
import com.veryfi.kotlin.Constants.FILE_URL
import com.veryfi.kotlin.Constants.FILE_URLS
import com.veryfi.kotlin.Constants.LIST_CATEGORIES
import com.veryfi.kotlin.Constants.MAX_PAGES_TO_PROCESS
import com.veryfi.kotlin.HttpMethod
import org.json.JSONObject
import java.util.concurrent.CompletableFuture

/**
 * Creates the JSON object of the parameters of the request
 * @param fileUrl Required if file_urls isn't specified. Publicly accessible URL to a file, e.g. "https://cdn.example.com/receipt.jpg".
 * @param fileUrls Required if file_url isn't specifies. List of publicly accessible URLs to multiple files, e.g. ["https://cdn.example.com/receipt1.jpg", "https://cdn.example.com/receipt2.jpg"]
 * @param categories List of categories to use when categorizing the document
 * @param deleteAfterProcessing Delete this document from Veryfi after data has been extracted
 * @param maxPagesToProcess When sending a long document to Veryfi for processing, this parameter controls how many pages of the document will be read and processed, starting from page 1.
 * @param boostMode Flag that tells Veryfi whether boost mode should be enabled. When set to 1, Veryfi will skip data enrichment steps, but will process the document faster. Default value for this flag is 0
 * @param externalId Optional custom document identifier. Use this if you would like to assign your own ID to documents
 * @param parameters Additional request parameters
 * @return JSON object of the request arguments
 */
private fun Client.getProcessDocumentUrlArguments(
    fileUrl: String, fileUrls: List<String?>?, categories: List<String?>?,
    deleteAfterProcessing: Boolean, maxPagesToProcess: Int,
    boostMode: Boolean, externalId: String?, parameters: JSONObject?
): JSONObject {
    var categoriesLocal = categories
    if (categoriesLocal == null || categoriesLocal.isEmpty()) {
        categoriesLocal = LIST_CATEGORIES
    }
    val requestArguments = JSONObject()
    requestArguments.put(AUTO_DELETE, deleteAfterProcessing)
    requestArguments.put(BOOST_MODE, boostMode)
    requestArguments.put(CATEGORIES, categoriesLocal)
    requestArguments.put(EXTERNAL_ID, externalId)
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
 * @param categories List of categories to use when categorizing the document
 * @param deleteAfterProcessing Delete this document from Veryfi after data has been extracted
 * @param maxPagesToProcess When sending a long document to Veryfi for processing, this parameter controls how many pages of the document will be read and processed, starting from page 1.
 * @param boostMode Flag that tells Veryfi whether boost mode should be enabled. When set to 1, Veryfi will skip data enrichment steps, but will process the document faster. Default value for this flag is 0
 * @param externalId Optional custom document identifier. Use this if you would like to assign your own ID to documents
 * @param parameters Additional request parameters
 * @return the data extracted from the Document [String]
 */
fun Client.processDocumentUrl(
    fileUrl: String, fileUrls: List<String?>?, categories: List<String?>?,
    deleteAfterProcessing: Boolean, maxPagesToProcess: Int,
    boostMode: Boolean, externalId: String?, parameters: JSONObject?
): String {
    val endpointName = "/documents/"
    val requestArguments = getProcessDocumentUrlArguments(
        fileUrl,
        fileUrls,
        categories,
        deleteAfterProcessing,
        maxPagesToProcess,
        boostMode,
        externalId,
        parameters
    )
    return request(HttpMethod.POST, endpointName, requestArguments)
}

/**
 * Process Document from url and extract all the fields from it.
 * @param fileUrl Required if file_urls isn't specified. Publicly accessible URL to a file, e.g. "https://cdn.example.com/receipt.jpg".
 * @param fileUrls Required if file_url isn't specifies. List of publicly accessible URLs to multiple files, e.g. ["https://cdn.example.com/receipt1.jpg", "https://cdn.example.com/receipt2.jpg"]
 * @param categories List of categories to use when categorizing the document
 * @param deleteAfterProcessing Delete this document from Veryfi after data has been extracted
 * @param maxPagesToProcess When sending a long document to Veryfi for processing, this parameter controls how many pages of the document will be read and processed, starting from page 1.
 * @param boostMode Flag that tells Veryfi whether boost mode should be enabled. When set to 1, Veryfi will skip data enrichment steps, but will process the document faster. Default value for this flag is 0
 * @param externalId Optional custom document identifier. Use this if you would like to assign your own ID to documents
 * @param parameters Additional request parameters
 * @return the data extracted from the Document
 */
fun Client.processDocumentUrlAsync(
    fileUrl: String, fileUrls: List<String?>?,
    categories: List<String?>?, deleteAfterProcessing: Boolean,
    maxPagesToProcess: Int, boostMode: Boolean,
    externalId: String?, parameters: JSONObject?
): CompletableFuture<String> {
    val endpointName = "/documents/"
    val requestArguments = getProcessDocumentUrlArguments(
        fileUrl,
        fileUrls,
        categories,
        deleteAfterProcessing,
        maxPagesToProcess,
        boostMode,
        externalId,
        parameters
    )
    return requestAsync(HttpMethod.POST, endpointName, requestArguments)
}

