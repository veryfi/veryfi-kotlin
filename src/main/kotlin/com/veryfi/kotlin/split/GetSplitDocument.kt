package com.veryfi.kotlin.split

import com.veryfi.kotlin.Client
import com.veryfi.kotlin.HttpMethod
import org.json.JSONObject
import java.util.concurrent.CompletableFuture


/**
 * Veryfi's Get a Documents from PDF endpoint allows you to retrieve a collection of previously processed documents. https://docs.veryfi.com/api/receipts-invoices/get-documents-from-pdf/
 * Returns a json string [String] document information
 * @param documentId ID of the document you'd like to retrieve.
 * @return the data extracted from the document [String]
 */
fun Client.getSplitDocument(documentId: String): String {
    val endpointName = "/documents-set/$documentId/"
    val requestArguments = JSONObject()
    requestArguments.put("id", documentId)
    return request(HttpMethod.GET, endpointName, requestArguments)
}

/**
 * Veryfi's Get a Documents from PDF endpoint allows you to retrieve a collection of previously processed documents. https://docs.veryfi.com/api/receipts-invoices/get-documents-from-pdf/
 * Returns a json string [String] document information
 * @param documentId ID of the document you'd like to retrieve.
 * @return the data extracted from the document [String]
 */
fun Client.getSplitDocumentAsync(documentId: String): CompletableFuture<String> {
    val endpointName = "/documents-set/$documentId/"
    val requestArguments = JSONObject()
    requestArguments.put("id", documentId)
    return requestAsync(HttpMethod.GET, endpointName, requestArguments)
}