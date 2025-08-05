package com.veryfi.kotlin.split

import com.veryfi.kotlin.Client
import com.veryfi.kotlin.HttpMethod
import org.json.JSONObject
import java.util.concurrent.CompletableFuture

/**
 * Veryfi's Get a Submitted PDF endpoint allows you to retrieve a collection of previously processed documents. https://docs.veryfi.com/api/receipts-invoices/get-submitted-pdf/
 * @param page The page number. The response is capped to maximum of 50 results per page.
 * @param pageSize The number of Documents per page.
 * @return JSON object of previously processed documents [String]
 */
fun Client.getSplitDocuments(
    page: Int,
    pageSize: Int = 50
): String {
    val endpointName = "/documents-set/"
    val requestArguments = JSONObject()
    requestArguments.put("page", page)
    requestArguments.put("page_size", pageSize)
    return request(HttpMethod.POST, endpointName, requestArguments)
}

/**
 * Veryfi's Get a Submitted PDF endpoint allows you to retrieve a collection of previously processed documents. https://docs.veryfi.com/api/receipts-invoices/get-submitted-pdf/
 * @param page The page number. The response is capped to maximum of 50 results per page.
 * @param pageSize The number of Documents per page.
 * @return JSON object of previously processed documents [String]
 */
fun Client.getSplitDocumentsAsync(
    page: Int,
    pageSize: Int = 50
): CompletableFuture<String> {
    val endpointName = "/documents-set/"
    val requestArguments = JSONObject()
    requestArguments.put("page", page)
    requestArguments.put("page_size", pageSize)
    return requestAsync(HttpMethod.POST, endpointName, requestArguments)
}
