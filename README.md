https://veryfi.github.io/veryfi-kotlin/

![Veryfi Logo](https://cdn.veryfi.com/logos/veryfi-logo-wide-github.png)

[![Java - version](https://img.shields.io/badge/OpenJDK-11-red)](https://openjdk.java.net/projects/jdk/11/)
[![Coverage](.github/badges/jacoco.svg)](https://github.com/veryfi/veryfi-kotlin/actions/workflows/maven.yml)

**veryfi** is a Kotlin module for communicating with the [Veryfi OCR API](https://veryfi.com/api/)

## Installation

Install from [Maven](https://mvnrepository.com/) using gradle, a package manager for Kotlin.

Install the package from Maven using gradle:

Gradle using Groovy:

```java
dependencies {
    implementation 'com.veryfi:veryfi-kotlin:1.0.0'
}
```

Gradle using Koltin:

```java
dependencies {
    implementation("com.veryfi:veryfi-kotlin:1.0.0")
}
```

## Getting Started

### Obtaining Client ID and user keys
If you don't have an account with Veryfi, please go ahead and register here: [https://hub.veryfi.com/signup/api/](https://hub.veryfi.com/signup/api/)

### Kotlin API Client Library
The **veryfi** library can be used to communicate with Veryfi API. All available functionality is described here https://veryfi.github.io/veryfi-kotlin/veryfi/Client.html

Below is the sample script using **veryfi** to OCR and extract data from a document:

```java
object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        val clientId = "your_client_id"
        val clientSecret = "your_client_secret"
        val username = "your_username"
        val apiKey = "your_password"
        val client = createClient(clientId, clientSecret, username, apiKey)
        val categories = listOf("Advertising & Marketing", "Automotive")
        val jsonResponse = client.processDocument("example1.jpg", categories, false, null)
    }
}
``` 

Update a document
```java
object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        val clientId = "your_client_id"
        val clientSecret = "your_client_secret"
        val username = "your_username"
        val apiKey = "your_password"
        val client = createClient(clientId, clientSecret, username, apiKey)
        val documentId = "your_document_id"
        val parameters = JSONObject()
        parameters.put("category", "Meals & Entertainment")
        parameters.put("total", 11.23)
        val jsonResponse = client.updateDocument(documentId, parameters)
    }
}
```

## Need help?
If you run into any issue or need help installing or using the library, please contact support@veryfi.com.

If you found a bug in this library or would like new features added, then open an issue or pull requests against this repo!

To learn more about Veryfi visit https://www.veryfi.com/

## Tutorial


Below is an introduction to the Kotlin SDK.


[Link to blog post →](https://www.veryfi.com/kotlin/)