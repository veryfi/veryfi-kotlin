package com.veryfi.kotlin.split

import com.veryfi.kotlin.Client
import com.veryfi.kotlin.HttpMethod
import org.json.JSONObject
import java.util.concurrent.CompletableFuture

/**
 * Split document PDF from url and extract all the fields from it. https://docs.veryfi.com/api/receipts-invoices/split-and-process-a-pdf/
 * @param fileUrl Required if file_urls isn't specified. Publicly accessible URL to a file, e.g. "https://cdn.example.com/receipt.jpg".
 * @param fileUrls Required if file_url isn't specifies. List of publicly accessible URLs to multiple files, e.g. ["https://cdn.example.com/receipt1.jpg", "https://cdn.example.com/receipt2.jpg"]
 * @param parameters Additional request parameters
 * @return the data extracted from the document [String]
 */
fun Client.splitDocumentUrl(
    fileUrl: String?,
    fileUrls: List<String?>?,
    parameters: JSONObject?
): String {
    val endpointName = "/documents-set/"
    val requestArguments = getArguments(fileUrl, fileUrls, parameters)
    return request(HttpMethod.POST, endpointName, requestArguments)
}

/**
 * Split document PDF from url and extract all the fields from it. https://docs.veryfi.com/api/receipts-invoices/split-and-process-a-pdf/
 * @param fileUrl Required if file_urls isn't specified. Publicly accessible URL to a file, e.g. "https://cdn.example.com/receipt.jpg".
 * @param fileUrls Required if file_url isn't specifies. List of publicly accessible URLs to multiple files, e.g. ["https://cdn.example.com/receipt1.jpg", "https://cdn.example.com/receipt2.jpg"]
 * @param parameters Additional request parameters
 * @return the data extracted from the document
 */
fun Client.splitDocumentUrlAsync(
    fileUrl: String?,
    fileUrls: List<String?>?,
    parameters: JSONObject?
): CompletableFuture<String> {
    val endpointName = "/documents-set/"
    val requestArguments = getArguments(fileUrl, fileUrls, parameters)
    return requestAsync(HttpMethod.POST, endpointName, requestArguments)
}
