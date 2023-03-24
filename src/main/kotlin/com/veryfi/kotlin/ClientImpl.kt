package com.veryfi.kotlin

import com.veryfi.kotlin.Constants.ACCEPT
import com.veryfi.kotlin.Constants.APPLICATION_JSON
import com.veryfi.kotlin.Constants.AUTHORIZATION
import com.veryfi.kotlin.Constants.AUTO_DELETE
import com.veryfi.kotlin.Constants.BOOST_MODE
import com.veryfi.kotlin.Constants.CATEGORIES
import com.veryfi.kotlin.Constants.CLIENT_ID
import com.veryfi.kotlin.Constants.CONTENT_TYPE
import com.veryfi.kotlin.Constants.EXTERNAL_ID
import com.veryfi.kotlin.Constants.FILE_DATA
import com.veryfi.kotlin.Constants.FILE_NAME
import com.veryfi.kotlin.Constants.FILE_URL
import com.veryfi.kotlin.Constants.FILE_URLS
import com.veryfi.kotlin.Constants.FORM_URL_ENCODED
import com.veryfi.kotlin.Constants.LIST_CATEGORIES
import com.veryfi.kotlin.Constants.MAX_PAGES_TO_PROCESS
import com.veryfi.kotlin.Constants.SHA256
import com.veryfi.kotlin.Constants.USER_AGENT
import com.veryfi.kotlin.Constants.USER_AGENT_KOTLIN
import com.veryfi.kotlin.Constants.X_VERYFI_REQUEST_SIGNATURE
import com.veryfi.kotlin.Constants.X_VERYFI_REQUEST_TIMESTAMP
import org.json.JSONObject
import java.io.*
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.nio.charset.StandardCharsets
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import java.time.Duration
import java.util.*
import java.util.concurrent.CompletableFuture
import java.util.logging.Logger
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

/**
 * Client implementation [ClientImpl].
 * @param clientId the [String] provided by Veryfi.
 * @param clientSecret the [String] provided by Veryfi.
 * @param username the [String] provided by Veryfi.
 * @param apiKey the [String] provided by Veryfi.
 */
class ClientImpl(
    private val clientId: String,
    private val clientSecret: String,
    private val username: String,
    private val apiKey: String,
    private val apiVersion: Int
) :
    Client {
    private val logger = Logger.getLogger("ClientImpl")
    private var baseUrl = "https://api.veryfi.com/api/"
    private var timeOut = 120
    private var httpClient: HttpClient

    /**
     * Creates an instance of [ClientImpl].
     */
    init {
        httpClient = HttpClient.newHttpClient()
    }

    /**
     * Returns a [String] API Base URL with API Version.
     * @return the url [String]
     */
    private val url: String
        get() = baseUrl + "v" + apiVersion

    /**
     * Submit the HTTP request.
     * @param httpVerb HTTP Method
     * @param endpointName Endpoint name such as 'documents', 'users', etc.
     * @param requestArguments SON payload to send to Veryfi
     * @return A JSON of the response data.
     */
    private fun request(httpVerb: HttpMethod, endpointName: String, requestArguments: JSONObject?): String {
        return request(httpVerb, endpointName, requestArguments, false)
    }

    /**
     * Submit the HTTP request.
     * @param httpVerb HTTP Method
     * @param endpointName Endpoint name such as 'documents', 'users', etc.
     * @param requestArguments JSON payload to send to Veryfi
     * @param hasFiles true if there are any files to be submitted as binary.
     * @return A JSON of the response data.
     */
    private fun request(
        httpVerb: HttpMethod,
        endpointName: String,
        requestArguments: JSONObject?,
        hasFiles: Boolean
    ): String {
        val request = getHttpRequest(httpVerb, endpointName, requestArguments, hasFiles)
        return try {
            val response = httpClient.send(request, HttpResponse.BodyHandlers.ofString())
            response.body()
        } catch (e: Exception) {
            logger.severe(e.message)
            ""
        }
    }

    /**
     * Submit the HTTP request.
     * @param httpVerb HTTP Method
     * @param endpointName Endpoint name such as 'documents', 'users', etc.
     * @param requestArguments JSON payload to send to Veryfi
     * @return A JSON of the response data.
     */
    private fun requestAsync(
        httpVerb: HttpMethod, endpointName: String,
        requestArguments: JSONObject
    ): CompletableFuture<String> {
        val request = getHttpRequest(httpVerb, endpointName, requestArguments, false)
        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
            .thenApply { obj: HttpResponse<String> -> obj.body() }
    }

    /**
     * Creates the HTTP request Object.
     * @param httpVerb HTTP Method
     * @param endpointName Endpoint name such as 'documents', 'users', etc.
     * @param requestArguments JSON payload to send to Veryfi
     * @param hasFiles true if there are any files to be submitted as binary.
     * @return request Object for the HttpClient [HttpRequest]
     */
    private fun getHttpRequest(
        httpVerb: HttpMethod, endpointName: String, requestArguments: JSONObject?,
        hasFiles: Boolean
    ): HttpRequest {
        val request: HttpRequest
        val apiUrl = "$url/partner$endpointName"
        val headers = getHeaders(hasFiles, requestArguments)
        request = when (httpVerb) {
            HttpMethod.DELETE -> HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .timeout(Duration.ofSeconds(timeOut.toLong()))
                .headers(*headers.toTypedArray())
                .DELETE()
                .build()
            HttpMethod.POST -> HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .timeout(Duration.ofSeconds(timeOut.toLong()))
                .headers(*headers.toTypedArray())
                .POST(HttpRequest.BodyPublishers.ofString(requestArguments.toString()))
                .build()
            HttpMethod.PUT -> HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .timeout(Duration.ofSeconds(timeOut.toLong()))
                .headers(*headers.toTypedArray())
                .PUT(HttpRequest.BodyPublishers.ofString(requestArguments.toString()))
                .build()
            else -> HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .timeout(Duration.ofSeconds(timeOut.toLong()))
                .headers(*headers.toTypedArray())
                .GET()
                .build()
        }
        return request
    }

    /**
     * Returns a [String] ApiKey for the headers of the request.
     * @return the ApiKey [String]
     */
    private fun getApiKey(): String {
        return "apikey $username:$apiKey"
    }

    /**
     * Prepares the headers needed for a request.
     * @param hasFiles true if there are any files to be submitted as binary
     * @param requestArguments JSON payload to send to Veryfi [JSONObject]
     * @return List of the headers
     */
    private fun getHeaders(hasFiles: Boolean, requestArguments: JSONObject?): List<String> {
        val date = Date()
        val timeStamp = date.time
        val headers: MutableList<String> = ArrayList()
        val jsonHeaders = JSONObject()
        jsonHeaders.put(USER_AGENT, USER_AGENT_KOTLIN)
        jsonHeaders.put(ACCEPT, APPLICATION_JSON)
        jsonHeaders.put(CONTENT_TYPE, APPLICATION_JSON)
        jsonHeaders.put(CLIENT_ID, clientId)
        jsonHeaders.put(AUTHORIZATION, getApiKey())
        if (hasFiles) {
            headers.add(CONTENT_TYPE)
            headers.add(FORM_URL_ENCODED)
        }
        jsonHeaders.put(X_VERYFI_REQUEST_TIMESTAMP, timeStamp.toString())
        jsonHeaders.put(X_VERYFI_REQUEST_SIGNATURE, generateSignature(requestArguments))
        for (key in JSONObject.getNames(jsonHeaders)) {
            headers.add(key)
            headers.add(jsonHeaders.getString(key))
        }
        return headers
    }

    /**
     * Generate unique signature for payload params.
     * @param timeStamp Unix Long timestamp
     * @param payloadParams JSON params to be sent to API request
     * @return Unique signature generated using the client_secret and the payload
     */
    private fun generateSignature(payloadParams: JSONObject?): String {
        val payload = payloadParams.toString()
        val secretBytes = clientSecret.toByteArray(StandardCharsets.UTF_8)
        val payloadBytes = payload.toByteArray(StandardCharsets.UTF_8)
        val keySpec = SecretKeySpec(secretBytes, SHA256)
        val mac: Mac = try {
            Mac.getInstance(SHA256)
        } catch (e: NoSuchAlgorithmException) {
            return e.message + ""
        }
        try {
            mac.init(keySpec)
        } catch (e: InvalidKeyException) {
            return e.message + ""
        }
        val macBytes = mac.doFinal(payloadBytes)
        return Base64.getEncoder().encodeToString(macBytes)
    }

    /**
     * Returns a json string [String] with the list of documents.
     * @return the url [String]
     */
    override fun getDocuments(): String {
        val endpointName = "/documents/"
        val requestArguments = JSONObject()
        return request(HttpMethod.GET, endpointName, requestArguments)
    }

    /**
     * Returns a json string list of documents.
     * @return the list of previously processed documents [String]
     */
    override fun getDocumentsAsync(): CompletableFuture<String> {
        val endpointName = "/documents/"
        val requestArguments = JSONObject()
        return requestAsync(HttpMethod.GET, endpointName, requestArguments)
    }

    /**
     * Returns a json string [String] document information
     * @param documentId ID of the document you'd like to retrieve.
     * @return the data extracted from the Document [String]
     */
    override fun getDocument(documentId: String): String {
        val endpointName = "/documents/$documentId/"
        val requestArguments = JSONObject()
        requestArguments.put("id", documentId)
        return request(HttpMethod.GET, endpointName, requestArguments)
    }

    /**
     * Returns a json string document information.
     * @param documentId ID of the document you'd like to retrieve.
     * @return the data extracted from the Document [String]
     */
    override fun getDocumentAsync(documentId: String): CompletableFuture<String> {
        val endpointName = "/documents/$documentId/"
        val requestArguments = JSONObject()
        requestArguments.put("id", documentId)
        return requestAsync(HttpMethod.GET, endpointName, requestArguments)
    }

    /**
     * Creates the JSON Object for the parameters of the request
     * @param filePath Path on disk to a file to submit for data extraction
     * @param categories List of categories Veryfi can use to categorize the document
     * @param deleteAfterProcessing Delete this document from Veryfi after data has been extracted
     * @param parameters Additional request parameters
     * @return the JSON object of the parameters of the request
     */
    private fun getProcessDocumentArguments(
        filePath: String, categories: List<String?>?,
        deleteAfterProcessing: Boolean, parameters: JSONObject?
    ): JSONObject {
        var categoriesLocal = categories
        if (categoriesLocal == null || categoriesLocal.isEmpty()) {
            categoriesLocal = LIST_CATEGORIES
        }
        val fileName = filePath.replace("^.*[/\\\\]".toRegex(), "")
        val file = File(filePath)
        var base64EncodedString: String? = ""
        try {
            if (file.exists() && file.canRead()) {
                base64EncodedString = Base64.getEncoder().encodeToString(file.readBytes())
            }
        } catch (e: IOException) {
            logger.severe(e.message)
        }
        val requestArguments = JSONObject()
        requestArguments.put(FILE_NAME, fileName)
        requestArguments.put(FILE_DATA, base64EncodedString)
        requestArguments.put(CATEGORIES, categoriesLocal)
        requestArguments.put(AUTO_DELETE, deleteAfterProcessing)
        if (parameters != null && !parameters.isEmpty) {
            for (key in JSONObject.getNames(parameters)) {
                requestArguments.put(key, parameters[key])
            }
        }
        return requestArguments
    }

    /**
     * Process a document and extract all the fields from it
     * @param filePath Path on disk to a file to submit for data extraction
     * @param categories List of categories Veryfi can use to categorize the document
     * @param deleteAfterProcessing Delete this document from Veryfi after data has been extracted
     * @param parameters Additional request parameters
     * @return the data extracted from the Document [String]
     */
    override fun processDocument(
        filePath: String, categories: List<String?>?, deleteAfterProcessing: Boolean,
        parameters: JSONObject?
    ): String {
        val endpointName = "/documents/"
        val requestArguments = getProcessDocumentArguments(filePath, categories, deleteAfterProcessing, parameters)
        return request(HttpMethod.POST, endpointName, requestArguments)
    }

    /**
     * Process a document and extract all the fields from it
     * @param filePath Path on disk to a file to submit for data extraction
     * @param categories List of categories Veryfi can use to categorize the document
     * @param deleteAfterProcessing Delete this document from Veryfi after data has been extracted
     * @param parameters Additional request parameters
     * @return the data extracted from the Document 
     */
    override fun processDocumentAsync(
        filePath: String, categories: List<String?>?,
        deleteAfterProcessing: Boolean, parameters: JSONObject?
    ): CompletableFuture<String> {
        val endpointName = "/documents/"
        val requestArguments = getProcessDocumentArguments(filePath, categories, deleteAfterProcessing, parameters)
        return requestAsync(HttpMethod.POST, endpointName, requestArguments)
    }

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
    private fun getProcessDocumentUrlArguments(
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
    override fun processDocumentUrl(
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
    override fun processDocumentUrlAsync(
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

    /**
     * Delete Document from Veryfi
     * @param documentId ID of the document you'd like to delete.
     * @return the response data. [String]
     */
    override fun deleteDocument(documentId: String): String {
        val endpointName = "/documents/$documentId/"
        val requestArguments = JSONObject()
        requestArguments.put("id", documentId)
        return request(HttpMethod.DELETE, endpointName, requestArguments)
    }

    /**
     * Delete Document from Veryfi
     * @param documentId ID of the document you'd like to delete.
     * @return the response data. 
     */
    override fun deleteDocumentAsync(documentId: String): CompletableFuture<String> {
        val endpointName = "/documents/$documentId/"
        val requestArguments = JSONObject()
        requestArguments.put("id", documentId)
        return requestAsync(HttpMethod.DELETE, endpointName, requestArguments)
    }

    /**
     * Update data for a previously processed document, including almost any field like `vendor`, `date`, `notes` etc.
     * @param documentId ID of the document you'd like to update.
     * @param parameters Additional request parameters
     * @return A document json with updated fields, if fields are writable. Otherwise, a document with unchanged fields. [String]
     */
    override fun updateDocument(documentId: String, parameters: JSONObject?): String {
        val endpointName = "/documents/$documentId/"
        return request(HttpMethod.PUT, endpointName, parameters)
    }

    /**
     * Update data for a previously processed document, including almost any field like `vendor`, `date`, `notes` etc.
     * @param documentId ID of the document you'd like to update.
     * @param parameters Additional request parameters
     * @return A document json with updated fields, if fields are writable. Otherwise, a document with unchanged fields. 
     */
    override fun updateDocumentAsync(documentId: String, parameters: JSONObject?): CompletableFuture<String> {
        val endpointName = "/documents/$documentId/"
        val requestArguments = JSONObject()
        parameters?.let {
            for (key in JSONObject.getNames(it)) {
                requestArguments.put(key, it[key])
            }
        }
        return requestAsync(HttpMethod.PUT, endpointName, requestArguments)
    }

    /**
     * Define new time out for the requests in seconds
     * @param timeOut of the http requests in seconds
     */
    override fun setTimeOut(timeOut: Int) {
        this.timeOut = timeOut
    }

    /**
     * By default, the base URL is https://api.veryfi.com/api/;
     * @param baseUrl for the Veryfi API
     */
    override fun setBaseUrl(baseUrl: String) {
        this.baseUrl = baseUrl
    }

    override fun setHttpClient(httpClient: HttpClient) {
        this.httpClient = httpClient
    }
}