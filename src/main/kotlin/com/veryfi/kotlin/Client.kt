package com.veryfi.kotlin

import com.veryfi.kotlin.Constants.ACCEPT
import com.veryfi.kotlin.Constants.APPLICATION_JSON
import com.veryfi.kotlin.Constants.AUTHORIZATION
import com.veryfi.kotlin.Constants.CLIENT_ID
import com.veryfi.kotlin.Constants.CONTENT_TYPE
import com.veryfi.kotlin.Constants.FORM_URL_ENCODED
import com.veryfi.kotlin.Constants.USER_AGENT
import com.veryfi.kotlin.Constants.USER_AGENT_KOTLIN
import com.veryfi.kotlin.Constants.X_VERYFI_REQUEST_TIMESTAMP
import org.json.JSONObject
import java.io.*
import java.net.URI
import java.net.URLEncoder
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.nio.charset.StandardCharsets
import java.time.Duration
import java.util.*
import java.util.concurrent.CompletableFuture
import java.util.logging.Logger


/**
 *  Veryfi API client for kotlin
 * @param clientId the [String] provided by Veryfi.
 * @param username the [String] provided by Veryfi.
 * @param apiKey the [String] provided by Veryfi.
 */
class Client(
    private val clientId: String,
    private val username: String,
    private val apiKey: String,
    private val apiVersion: Int
) {
    internal val logger = Logger.getLogger("ClientImpl")
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
    internal fun request(httpVerb: HttpMethod, endpointName: String, requestArguments: JSONObject?): String {
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
            if (response.headers() != null) {
                val traceId = response.headers().firstValue("x-veryfi-trace-id")
                if (traceId.isPresent) {
                    logger.info("x-veryfi-trace-id: ${traceId.get()}")
                    return addTraceIdToResponse(response.body(), traceId.get())
                }
            }
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
    internal fun requestAsync(
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
        var apiUrl = "$url/partner$endpointName"
        val headers = getHeaders(hasFiles, requestArguments)

        if (httpVerb == HttpMethod.GET && requestArguments != null) {
            // Convert JSONObject to query string
            val queryString = buildQueryString(requestArguments)
            // Append query string to the URL
            apiUrl = "$apiUrl?$queryString"
        }
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
        for (key in JSONObject.getNames(jsonHeaders)) {
            headers.add(key)
            headers.add(jsonHeaders.getString(key))
        }
        return headers
    }

    // Utility to build query string from JSONObject
    private fun buildQueryString(jsonObject: JSONObject): String {
        val queryString = StringBuilder()
        for (key in jsonObject.keySet()) {
            val value = jsonObject[key].toString()
            if (queryString.length > 0) {
                queryString.append("&")
            }
            queryString.append(URLEncoder.encode(key, StandardCharsets.UTF_8))
            queryString.append("=")
            queryString.append(URLEncoder.encode(value, StandardCharsets.UTF_8))
        }
        return queryString.toString()
    }

    /**
     * Define new time out for the requests in seconds
     * @param timeOut of the http requests in seconds
     */
    fun setTimeOut(timeOut: Int) {
        this.timeOut = timeOut
    }

    /**
     * By default, the base URL is https://api.veryfi.com/api/;
     * @param baseUrl for the Veryfi API
     */
    fun setBaseUrl(baseUrl: String) {
        this.baseUrl = baseUrl
    }

    fun setHttpClient(httpClient: HttpClient) {
        this.httpClient = httpClient
    }

    /**
     * Sets a custom HttpClient to be used for API requests.
     * @param httpClient The HttpClient instance to use for HTTP requests.
     */
    private fun addTraceIdToResponse(responseBody: String, traceId: String?): String {
        return try {
            if (traceId != null && responseBody.isNotEmpty()) {
                val jsonResponse = JSONObject(responseBody)
                jsonResponse.put("x_veryfi_trace_id", traceId)
                jsonResponse.toString()
            } else {
                responseBody
            }
        } catch (e: Exception) {
            // If response is not valid JSON, return as-is
            logger.warning("Could not parse response as JSON, returning original response")
            responseBody
        }
    }
}