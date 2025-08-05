<img src="https://user-images.githubusercontent.com/30125790/212157461-58bdc714-2f89-44c2-8e4d-d42bee74854e.png#gh-dark-mode-only" width="200">
<img src="https://user-images.githubusercontent.com/30125790/212157486-bfd08c5d-9337-4b78-be6f-230dc63838ba.png#gh-light-mode-only" width="200">

[![Java - version](https://img.shields.io/badge/OpenJDK-11-red)](https://openjdk.java.net/projects/jdk/11/)
![Coverage](.github/badges/jacoco.svg)

**veryfi** is a Kotlin module for communicating with the [Veryfi OCR API](https://veryfi.com/api/)

## Installation

Install from [Maven](https://mvnrepository.com/) using gradle, a package manager for Kotlin.

Install the package from Maven using gradle:

Gradle using Groovy:

```groovy
dependencies {
    implementation 'com.veryfi:veryfi-kotlin:2.0.2'
}
```

Gradle using Koltin:

```groovy
dependencies {
    implementation("com.veryfi:veryfi-kotlin:2.0.2")
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
Visit https://docs.veryfi.com/ to access integration guides and usage notes in the Veryfi API Documentation Portal

If you run into any issue or need help installing or using the library, please contact support@veryfi.com.

If you found a bug in this library or would like new features added, then open an issue or pull requests against this repo!

To learn more about Veryfi visit https://www.veryfi.com/

## Tutorial


Below is an introduction to the Kotlin SDK.


[Link to blog post →](https://www.veryfi.com/kotlin/)
