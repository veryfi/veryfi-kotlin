package com.veryfi.kotlin.w2s

import com.veryfi.kotlin.Client
import com.veryfi.kotlin.Constants.FILE_URL
import com.veryfi.kotlin.Constants.FILE_URLS
import com.veryfi.kotlin.HttpMethod
import org.json.JSONObject
import java.util.concurrent.CompletableFuture

/**
 * Creates the JSON object of the parameters of the request
 * @param fileUrl Required if file_urls isn't specified. Publicly accessible URL to a file, e.g. "https://cdn.example.com/receipt.jpg".
 * @param fileUrls Required if file_url isn't specifies. List of publicly accessible URLs to multiple files, e.g. ["https://cdn.example.com/receipt1.jpg", "https://cdn.example.com/receipt2.jpg"]
 * @param parameters Additional request parameters
 * @return JSON object of the request arguments
 */
private fun Client.getProcessW2UrlArguments(
    fileUrl: String?, fileUrls: List<String?>?,
    parameters: JSONObject?
): JSONObject {
    val requestArguments = JSONObject()
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
 * Process W2 from url and extract all the fields from it.
 * @param fileUrl Required if file_urls isn't specified. Publicly accessible URL to a file, e.g. "https://cdn.example.com/receipt.jpg".
 * @param fileUrls Required if file_url isn't specifies. List of publicly accessible URLs to multiple files, e.g. ["https://cdn.example.com/receipt1.jpg", "https://cdn.example.com/receipt2.jpg"]
 * @param parameters Additional request parameters
 * @return the data extracted from the W2
 */
fun Client.processW2Url(
    fileUrl: String?, fileUrls: List<String?>?,
    parameters: JSONObject?
): String {
    val endpointName = "/w2s/"
    val requestArguments = getProcessW2UrlArguments(
        fileUrl,
        fileUrls,
        parameters
    )
    return request(HttpMethod.POST, endpointName, requestArguments)
}

/**
 * Process W2 from url and extract all the fields from it.
 * @param fileUrl Required if file_urls isn't specified. Publicly accessible URL to a file, e.g. "https://cdn.example.com/receipt.jpg".
 * @param fileUrls Required if file_url isn't specifies. List of publicly accessible URLs to multiple files, e.g. ["https://cdn.example.com/receipt1.jpg", "https://cdn.example.com/receipt2.jpg"]
 * @param parameters Additional request parameters
 * @return the data extracted from the W2
 */
fun Client.processW2UrlAsync(
    fileUrl: String?, fileUrls: List<String?>?,
    parameters: JSONObject?
): CompletableFuture<String> {
    val endpointName = "/w2s/"
    val requestArguments = getProcessW2UrlArguments(
        fileUrl,
        fileUrls,
        parameters
    )
    return requestAsync(HttpMethod.POST, endpointName, requestArguments)
}

