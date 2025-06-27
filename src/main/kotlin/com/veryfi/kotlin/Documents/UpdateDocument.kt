package com.veryfi.kotlin.documents

import com.veryfi.kotlin.Client
import com.veryfi.kotlin.HttpMethod
import org.json.JSONObject
import java.util.concurrent.CompletableFuture

/**
 * https://docs.veryfi.com/api/receipts-invoices/update-a-document/
 * Update data for a previously processed document, including almost any field like `vendor`, `date`, `notes` etc.
 * @param documentId ID of the document you'd like to update.
 * @param parameters Additional request parameters
 * @return A document json with updated fields, if fields are writable. Otherwise, a document with unchanged fields. [String]
 */
fun Client.updateDocument(documentId: String, parameters: JSONObject?): String {
    val endpointName = "/documents/$documentId/"
    return request(HttpMethod.PUT, endpointName, parameters)
}

/**
 * https://docs.veryfi.com/api/receipts-invoices/update-a-document/
 * Update data for a previously processed document, including almost any field like `vendor`, `date`, `notes` etc.
 * @param documentId ID of the document you'd like to update.
 * @param parameters Additional request parameters
 * @return A document json with updated fields, if fields are writable. Otherwise, a document with unchanged fields.
 */
fun Client.updateDocumentAsync(documentId: String, parameters: JSONObject?): CompletableFuture<String> {
    val endpointName = "/documents/$documentId/"
    val requestArguments = JSONObject()
    parameters?.let {
        for (key in JSONObject.getNames(it)) {
            requestArguments.put(key, it[key])
        }
    }
    return requestAsync(HttpMethod.PUT, endpointName, requestArguments)
}

