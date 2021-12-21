package com.veryfi.kotlin

import com.veryfi.kotlin.VeryfiClientFactory.createClient
import org.json.JSONObject
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.springframework.boot.test.context.SpringBootTest
import java.io.IOException
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.net.http.HttpResponse.BodyHandler
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ExecutionException

@Suppress("UNCHECKED_CAST")
@SpringBootTest
class ClientTest {
    private var clientId = "your_client_id"
    private var clientSecret = "your_client_secret"
    private var username = "your_username"
    private var apiKey = "your_password"
    private var client = createClient(clientId, clientSecret, username, apiKey) as ClientImpl
    private var mockResponses = true // Change to “false” if you want to test your personal credential

    @Test
    @Throws(IOException::class, InterruptedException::class)
    fun getDocumentsTest() {
        if (mockResponses) {
            val httpClient = mock(HttpClient::class.java)
            client.setHttpClient(httpClient)
            val fileStream = ClassLoader.getSystemResourceAsStream("getDocuments.json")!!
            val result = String(fileStream.readAllBytes())
            val httpResponse: HttpResponse<String> = mock(HttpResponse::class.java) as HttpResponse<String>
            `when`(httpClient.send(any(HttpRequest::class.java), any<BodyHandler<String>>())).thenReturn(httpResponse)
            `when`(httpResponse.statusCode()).thenReturn(200)
            `when`(httpResponse.body()).thenReturn(result)
        }
        val jsonResponse = client.getDocuments()
        val documents = JSONObject(jsonResponse)
        Assertions.assertTrue(documents.length() > 0)
    }

    @Test
    @Throws(IOException::class, InterruptedException::class)
    fun getDocumentsTestWithException() {
        val httpClient = mock(HttpClient::class.java)
        client.setHttpClient(httpClient)
        `when`(
            httpClient.send(
                any(HttpRequest::class.java), any<BodyHandler<String>>()
            )
        ).thenThrow(InterruptedException())
        val jsonResponse = client.getDocuments()
        Assertions.assertEquals("", jsonResponse)
    }

    @Test
    @Throws(IOException::class, InterruptedException::class)
    fun getDocumentTest() {
        val documentId = "31727276" // Change to your document Id
        if (mockResponses) {
            val httpClient = mock(HttpClient::class.java)
            client.setHttpClient(httpClient)
            val fileStream = ClassLoader.getSystemResourceAsStream("getDocument.json")!!
            val result = String(fileStream.readAllBytes())
            val httpResponse: HttpResponse<String> = mock(HttpResponse::class.java) as HttpResponse<String>
            `when`(httpClient.send(any(HttpRequest::class.java), any<BodyHandler<String>>())).thenReturn(httpResponse)
            `when`(httpResponse.statusCode()).thenReturn(200)
            `when`(httpResponse.body()).thenReturn(result)
        }
        val jsonResponse = client.getDocument(documentId)
        val document = JSONObject(jsonResponse)
        Assertions.assertEquals(document.getInt("id"), documentId.toInt())
    }

    @Test
    @Throws(IOException::class, InterruptedException::class)
    fun processDocumentTest() {
        val categories = listOf("Advertising & Marketing", "Automotive")
        if (mockResponses) {
            val httpClient = mock(HttpClient::class.java)
            client.setHttpClient(httpClient)
            val fileStream = ClassLoader.getSystemResourceAsStream("processDocument.json")!!
            val result = String(fileStream.readAllBytes())
            val httpResponse: HttpResponse<String> = mock(HttpResponse::class.java) as HttpResponse<String>
            `when`(httpClient.send(any(HttpRequest::class.java), any<BodyHandler<String>>())).thenReturn(httpResponse)
            `when`(httpResponse.statusCode()).thenReturn(200)
            `when`(httpResponse.body()).thenReturn(result)
        }
        val jsonResponse = client.processDocument("receipt.png", categories, false, null)
        val document = JSONObject(jsonResponse)
        Assertions.assertEquals("In-n-out Burger", document.getJSONObject("vendor").getString("name"))
    }

    @Test
    @Throws(IOException::class, InterruptedException::class)
    fun processDocumentTestWithParameters() {
        val categories = listOf("Advertising & Marketing", "Automotive")
        val parameters = JSONObject()
        parameters.put("p1", "p1value")
        if (mockResponses) {
            val httpClient = mock(HttpClient::class.java)
            client.setHttpClient(httpClient)
            val fileStream = ClassLoader.getSystemResourceAsStream("processDocument.json")!!
            val result = String(fileStream.readAllBytes())
            val httpResponse: HttpResponse<String> = mock(HttpResponse::class.java) as HttpResponse<String>
            `when`(httpClient.send(any(HttpRequest::class.java), any<BodyHandler<String>>())).thenReturn(httpResponse)
            `when`(httpResponse.statusCode()).thenReturn(200)
            `when`(httpResponse.body()).thenReturn(result)
        }
        val jsonResponse = client.processDocument("receipt.png", categories, false, parameters)
        val document = JSONObject(jsonResponse)
        Assertions.assertEquals("In-n-out Burger", document.getJSONObject("vendor").getString("name"))
    }

    @Test
    @Throws(IOException::class, InterruptedException::class)
    fun processDocumentTestWithoutCategories() {
        if (mockResponses) {
            val httpClient = mock(HttpClient::class.java)
            client.setHttpClient(httpClient)
            val fileStream = ClassLoader.getSystemResourceAsStream("processDocument.json")!!
            val result = String(fileStream.readAllBytes())
            val httpResponse: HttpResponse<String> = mock(HttpResponse::class.java) as HttpResponse<String>
            `when`(httpClient.send(any(HttpRequest::class.java), any<BodyHandler<String>>())).thenReturn(httpResponse)
            `when`(httpResponse.statusCode()).thenReturn(200)
            `when`(httpResponse.body()).thenReturn(result)
        }
        val jsonResponse = client.processDocument("receipt.png", null, false, null)
        val document = JSONObject(jsonResponse)
        Assertions.assertEquals("In-n-out Burger", document.getJSONObject("vendor").getString("name"))
    }

    @Test
    @Throws(IOException::class, InterruptedException::class)
    fun updateDocumentTest() {
        val documentId = "31727276" // Change to your document Id
        val parameters = JSONObject()
        val notes = "Note updated"
        parameters.put("notes", notes)
        if (mockResponses) {
            val httpClient = mock(HttpClient::class.java)
            client.setHttpClient(httpClient)
            val fileStream = ClassLoader.getSystemResourceAsStream("updateDocument.json")!!
            val result = String(fileStream.readAllBytes())
            val httpResponse = mock(HttpResponse::class.java) as HttpResponse<String>
            `when`(httpClient.send(any(HttpRequest::class.java), any<BodyHandler<String>>())).thenReturn(httpResponse)
            `when`(httpResponse.statusCode()).thenReturn(200)
            `when`(httpResponse.body()).thenReturn(result)
        }
        val jsonResponseUpdated = client.updateDocument(documentId, parameters)
        val documentJson = JSONObject(jsonResponseUpdated)
        Assertions.assertEquals(documentJson.getString("notes"), notes)
    }

    @Test
    @Throws(IOException::class, InterruptedException::class)
    fun deleteDocumentTest() {
        val documentId = "37769185" // Change to your document Id
        if (mockResponses) {
            val httpClient = mock(HttpClient::class.java)
            client.setHttpClient(httpClient)
            val fileStream = ClassLoader.getSystemResourceAsStream("deleteDocument.json")!!
            val result = String(fileStream.readAllBytes())
            val httpResponse: HttpResponse<String> = mock(HttpResponse::class.java) as HttpResponse<String>
            `when`(httpClient.send(any(HttpRequest::class.java), any<BodyHandler<String>>())).thenReturn(httpResponse)
            `when`(httpResponse.statusCode()).thenReturn(200)
            `when`(httpResponse.body()).thenReturn(result)
        }
        val jsonResponse = client.deleteDocument(documentId)
        Assertions.assertFalse(jsonResponse.isEmpty())
    }

    @Test
    @Throws(IOException::class, InterruptedException::class)
    fun processDocumentUrlTest() {
        if (mockResponses) {
            val httpClient = mock(HttpClient::class.java)
            client.setHttpClient(httpClient)
            val fileStream = ClassLoader.getSystemResourceAsStream("processDocumentUrl.json")!!
            val result = String(fileStream.readAllBytes())
            val httpResponse: HttpResponse<String> = mock(HttpResponse::class.java) as HttpResponse<String>
            `when`(httpClient.send(any(HttpRequest::class.java), any<BodyHandler<String>>())).thenReturn(httpResponse)
            `when`(httpResponse.statusCode()).thenReturn(200)
            `when`(httpResponse.body()).thenReturn(result)
        }
        val jsonResponse = client.processDocumentUrl(
            "https://cdn.veryfi.com/receipts/92233902-c94a-491d-a4f9-0d61f9407cd2.pdf",
            null,
            null,
            false,
            1,
            false,
            null,
            null
        )
        val document = JSONObject(jsonResponse)
        Assertions.assertEquals("Rumpke", document.getJSONObject("vendor").getString("name"))
    }

    @Test
    @Throws(IOException::class, InterruptedException::class)
    fun processDocumentUrlTestWithFileUrlsAndParameters() {
        if (mockResponses) {
            val httpClient = mock(HttpClient::class.java)
            client.setHttpClient(httpClient)
            val fileStream = ClassLoader.getSystemResourceAsStream("processDocumentUrl.json")!!
            val result = String(fileStream.readAllBytes())
            val httpResponse: HttpResponse<String> = mock(HttpResponse::class.java) as HttpResponse<String>
            `when`(httpClient.send(any(HttpRequest::class.java), any<BodyHandler<String>>())).thenReturn(httpResponse)
            `when`(httpResponse.statusCode()).thenReturn(200)
            `when`(httpResponse.body()).thenReturn(result)
        }
        val parameters = JSONObject()
        parameters.put("p1", "p1value")
        val jsonResponse = client.processDocumentUrl(
            "https://cdn.veryfi.com/receipts/92233902-c94a-491d-a4f9-0d61f9407cd2.pdf",
            listOf("https://cdn.veryfi.com/receipts/92233902-c94a-491d-a4f9-0d61f9407cd2.pdf"),
            null,
            false,
            1,
            false,
            null,
            parameters
        )
        val document = JSONObject(jsonResponse)
        Assertions.assertEquals("Rumpke", document.getJSONObject("vendor").getString("name"))
    }

    @Test
    @Throws(ExecutionException::class, InterruptedException::class, IOException::class)
    fun getDocumentsAsyncTest() {
        if (mockResponses) {
            val httpClient = mock(HttpClient::class.java)
            client.setHttpClient(httpClient)
            val fileStream = ClassLoader.getSystemResourceAsStream("getDocuments.json")!!
            val result = String(fileStream.readAllBytes())
            val httpResponse: HttpResponse<String> = mock(HttpResponse::class.java) as HttpResponse<String>
            val jsonResponseFuture = CompletableFuture.completedFuture(httpResponse)
            `when`(httpClient.sendAsync(any(HttpRequest::class.java), any<BodyHandler<String>>())).thenReturn(
                jsonResponseFuture
            )
            `when`(httpResponse.statusCode()).thenReturn(200)
            `when`(httpResponse.body()).thenReturn(result)
        }
        val jsonResponseFuture = client.getDocumentsAsync()
        val jsonResponse = jsonResponseFuture.get()
        val documents = JSONObject(jsonResponse)
        Assertions.assertTrue(documents.length() > 0)
    }

    @Test
    @Throws(ExecutionException::class, InterruptedException::class, IOException::class)
    fun getDocumentAsyncTest() {
        val documentId = "31727276" // Change to your document Id
        if (mockResponses) {
            val httpClient = mock(HttpClient::class.java)
            client.setHttpClient(httpClient)
            val fileStream = ClassLoader.getSystemResourceAsStream("getDocument.json")!!
            val result = String(fileStream.readAllBytes())
            val httpResponse: HttpResponse<String> = mock(HttpResponse::class.java) as HttpResponse<String>
            val jsonResponseFuture = CompletableFuture.completedFuture(httpResponse)
            `when`(httpClient.sendAsync(any(HttpRequest::class.java), any<BodyHandler<String>>())).thenReturn(
                jsonResponseFuture
            )
            `when`(httpResponse.statusCode()).thenReturn(200)
            `when`(httpResponse.body()).thenReturn(result)
        }
        val jsonResponseFuture = client.getDocumentAsync(documentId)
        val jsonResponse = jsonResponseFuture.get()
        val document = JSONObject(jsonResponse)
        Assertions.assertEquals(document.getInt("id"), documentId.toInt())
    }

    @Test
    @Throws(ExecutionException::class, InterruptedException::class, IOException::class)
    fun processDocumentAsyncTest() {
        val categories = listOf("Advertising & Marketing", "Automotive")
        if (mockResponses) {
            val httpClient = mock(HttpClient::class.java)
            client.setHttpClient(httpClient)
            val fileStream = ClassLoader.getSystemResourceAsStream("processDocument.json")!!
            val result = String(fileStream.readAllBytes())
            val httpResponse: HttpResponse<String> = mock(HttpResponse::class.java) as HttpResponse<String>
            val jsonResponseFuture = CompletableFuture.completedFuture(httpResponse)
            `when`(httpClient.sendAsync(any(HttpRequest::class.java), any<BodyHandler<String>>())).thenReturn(
                jsonResponseFuture
            )
            `when`(httpResponse.statusCode()).thenReturn(200)
            `when`(httpResponse.body()).thenReturn(result)
        }
        val jsonResponseFuture = client.processDocumentAsync("receipt.png", categories, false, null)
        val jsonResponse = jsonResponseFuture.get()
        val document = JSONObject(jsonResponse)
        Assertions.assertEquals("In-n-out Burger", document.getJSONObject("vendor").getString("name"))
    }

    @Test
    @Throws(ExecutionException::class, InterruptedException::class, IOException::class)
    fun updateDocumentAsyncTest() {
        val documentId = "31727276" // Change to your document Id
        val parameters = JSONObject()
        val notes = "Note updated"
        parameters.put("notes", notes)
        if (mockResponses) {
            val httpClient = mock(HttpClient::class.java)
            client.setHttpClient(httpClient)
            val fileStream = ClassLoader.getSystemResourceAsStream("updateDocument.json")!!
            val result = String(fileStream.readAllBytes())
            val httpResponse: HttpResponse<String> = mock(HttpResponse::class.java) as HttpResponse<String>
            val jsonResponseFuture = CompletableFuture.completedFuture(httpResponse)
            `when`(httpClient.sendAsync(any(HttpRequest::class.java), any<BodyHandler<String>>())).thenReturn(
                jsonResponseFuture
            )
            `when`(httpResponse.statusCode()).thenReturn(200)
            `when`(httpResponse.body()).thenReturn(result)
        }
        val jsonResponseFuture = client.updateDocumentAsync(documentId, parameters)
        val documentJson = JSONObject(jsonResponseFuture.get())
        Assertions.assertEquals(documentJson.getString("notes"), notes)
    }

    @Test
    @Throws(ExecutionException::class, InterruptedException::class, IOException::class)
    fun deleteDocumentAsyncTest() {
        val documentId = "37769185" // Change to your document Id
        if (mockResponses) {
            val httpClient = mock(HttpClient::class.java)
            client.setHttpClient(httpClient)
            val fileStream = ClassLoader.getSystemResourceAsStream("deleteDocument.json")!!
            val result = String(fileStream.readAllBytes())
            val httpResponse: HttpResponse<String> = mock(HttpResponse::class.java) as HttpResponse<String>
            val jsonResponseFuture = CompletableFuture.completedFuture(httpResponse)
            `when`(httpClient.sendAsync(any(HttpRequest::class.java), any<BodyHandler<String>>())).thenReturn(
                jsonResponseFuture
            )
            `when`(httpResponse.statusCode()).thenReturn(200)
            `when`(httpResponse.body()).thenReturn(result)
        }
        val jsonResponseFuture = client.deleteDocumentAsync(documentId)
        val jsonResponse = jsonResponseFuture.get()
        Assertions.assertFalse(jsonResponse.isEmpty())
    }

    @Test
    @Throws(ExecutionException::class, InterruptedException::class, IOException::class)
    fun processDocumentUrlAsyncTest() {
        if (mockResponses) {
            val httpClient = mock(HttpClient::class.java)
            client.setHttpClient(httpClient)
            val fileStream = ClassLoader.getSystemResourceAsStream("processDocumentUrl.json")!!
            val result = String(fileStream.readAllBytes())
            val httpResponse: HttpResponse<String> = mock(HttpResponse::class.java) as HttpResponse<String>
            val jsonResponseFuture = CompletableFuture.completedFuture(httpResponse)
            `when`(httpClient.sendAsync(any(HttpRequest::class.java), any<BodyHandler<String>>())).thenReturn(
                jsonResponseFuture
            )
            `when`(httpResponse.statusCode()).thenReturn(200)
            `when`(httpResponse.body()).thenReturn(result)
        }
        val jsonResponseFuture = client.processDocumentUrlAsync(
            "https://cdn.veryfi.com/receipts/92233902-c94a-491d-a4f9-0d61f9407cd2.pdf",
            null,
            null,
            false,
            1,
            false,
            null,
            null
        )
        val jsonResponse = jsonResponseFuture.get()
        val document = JSONObject(jsonResponse)
        Assertions.assertEquals("Rumpke", document.getJSONObject("vendor").getString("name"))
    }

    @Test
    @Throws(ExecutionException::class, InterruptedException::class, IOException::class)
    fun badCredentialsTest() {
        val clientTmp =
            createClient("bad_credentials", "bad_credentials", "bad_credentials", "bad_credentials") as ClientImpl
        val jsonResponse = clientTmp.getDocuments()
        val documents = JSONObject(jsonResponse)
        Assertions.assertTrue(documents.get("message") == "Not Authorized")
    }
}
