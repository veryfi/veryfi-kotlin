package com.veryfi.kotlin

import org.json.JSONObject
import java.net.http.HttpClient
import java.util.concurrent.CompletableFuture

/**
 * Veryfi API client for kotlin.
 * The instances of classes that implement this interface are thread-safe and immutable.
 */
interface Client {

    /**
     * Returns a json string {@link String} list of documents.
     * @return the list of previously processed documents {@link String}
     */
    fun getDocuments(): String

    /**
     * Returns a json string {@link CompletableFuture<String>} list of documents.
     * @return the list of previously processed documents {@link String}
     */
    fun getDocumentsAsync(): CompletableFuture<String>

    /**
     * Returns a json string [String] document information
     * @param documentId ID of the document you'd like to retrieve.
     * @return the data extracted from the Document [String]
     */
    fun getDocument(documentId: String): String

    /**
     * Returns a json string  document information.
     * @param documentId ID of the document you'd like to retrieve.
     * @return the data extracted from the Document 
     */
    fun getDocumentAsync(documentId: String): CompletableFuture<String>

    /**
     * Process a document and extract all the fields from it
     * @param filePath Path on disk to a file to submit for data extraction
     * @param categories List of categories Veryfi can use to categorize the document
     * @param deleteAfterProcessing Delete this document from Veryfi after data has been extracted
     * @param parameters Additional request parameters
     * @return the data extracted from the Document [String]
     */
    fun processDocument(
        filePath: String, categories: List<String?>?, deleteAfterProcessing: Boolean,
        parameters: JSONObject?
    ): String

    /**
     * Process a document and extract all the fields from it
     * @param filePath Path on disk to a file to submit for data extraction
     * @param categories List of categories Veryfi can use to categorize the document
     * @param deleteAfterProcessing Delete this document from Veryfi after data has been extracted
     * @param parameters Additional request parameters
     * @return the data extracted from the Document 
     */
    fun processDocumentAsync(
        filePath: String, categories: List<String?>?,
        deleteAfterProcessing: Boolean, parameters: JSONObject?
    ): CompletableFuture<String>

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
    fun processDocumentUrl(
        fileUrl: String, fileUrls: List<String?>?, categories: List<String?>?, deleteAfterProcessing: Boolean,
        maxPagesToProcess: Int, boostMode: Boolean, externalId: String?, parameters: JSONObject?
    ): String

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
    fun processDocumentUrlAsync(
        fileUrl: String, fileUrls: List<String?>?, categories: List<String?>?, deleteAfterProcessing: Boolean,
        maxPagesToProcess: Int, boostMode: Boolean, externalId: String?, parameters: JSONObject?
    ): CompletableFuture<String>

    /**
     * Delete Document from Veryfi
     * @param documentId ID of the document you'd like to delete.
     * @return the response data. [String]
     */
    fun deleteDocument(documentId: String): String

    /**
     * Delete Document from Veryfi
     * @param documentId ID of the document you'd like to delete.
     * @return the response data. 
     */
    fun deleteDocumentAsync(documentId: String): CompletableFuture<String>

    /**
     * Update data for a previously processed document, including almost any field like `vendor`, `date`, `notes` and etc.
     * @param documentId ID of the document you'd like to update.
     * @param parameters Additional request parameters
     * @return A document json with updated fields, if fields are writable. Otherwise a document with unchanged fields. [String]
     */
    fun updateDocument(documentId: String, parameters: JSONObject?): String

    /**
     * Update data for a previously processed document, including almost any field like `vendor`, `date`, `notes` and etc.
     * @param documentId ID of the document you'd like to update.
     * @param parameters Additional request parameters
     * @return A document json with updated fields, if fields are writable. Otherwise a document with unchanged fields. 
     */
    fun updateDocumentAsync(documentId: String, parameters: JSONObject?): CompletableFuture<String>

    /**
     * Define new time out for the requests in seconds
     * @param timeOut of the http requests in seconds
     */
    fun setTimeOut(timeOut: Int)

    /**
     * By default is https://api.veryfi.com/api/;
     * @param baseUrl for the Veryfi API
     */
    fun setBaseUrl(baseUrl: String)

    /**
     * By default is https://api.veryfi.com/api/;
     * @param httpClient for the Veryfi API
     */
    fun setHttpClient(httpClient: HttpClient)
}