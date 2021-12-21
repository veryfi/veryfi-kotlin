package com.veryfi.kotlin

/**
 * header constants for HttpRequests.
 */
object Constants {
    /**
     * header for HttpRequest
     */
    const val ACCEPT = "Accept"

    /**
     * header for HttpRequest
     */
    const val USER_AGENT = "User-Agent"

    /**
     * header for HttpRequest
     */
    const val USER_AGENT_JAVA = "Java Veryfi-Java/1.0.6"

    /**
     * header for HttpRequest
     */
    const val APPLICATION_JSON = "application/json"

    /**
     * header for HttpRequest
     */
    const val CONTENT_TYPE = "Content-Type"

    /**
     * header for HttpRequest
     */
    const val CLIENT_ID = "Client-Id"

    /**
     * header for HttpRequest
     */
    const val AUTHORIZATION = "Authorization"

    /**
     * header for HttpRequest
     */
    const val FORM_URL_ENCODED = "application/x-www-form-urlencoded"

    /**
     * header for HttpRequest
     */
    const val X_VERYFI_REQUEST_TIMESTAMP = "X-Veryfi-Request-Timestamp"

    /**
     * header for HttpRequest
     */
    const val X_VERYFI_REQUEST_SIGNATURE = "X-Veryfi-Request-Signature"

    /**
     * header for HttpRequest
     */
    const val TIMESTAMP = "timestamp"

    /**
     * header for HttpRequest
     */
    const val SHA256 = "HmacSHA256"

    /**
     * header for HttpRequest
     */
    const val FILE_NAME = "file_name"

    /**
     * header for HttpRequest
     */
    const val FILE_DATA = "file_data"

    /**
     * header for HttpRequest
     */
    const val CATEGORIES = "categories"

    /**
     * header for HttpRequest
     */
    const val AUTO_DELETE = "auto_delete"

    /**
     * header for HttpRequest
     */
    const val BOOST_MODE = "boost_mode"

    /**
     * header for HttpRequest
     */
    const val EXTERNAL_ID = "external_id"

    /**
     * header for HttpRequest
     */
    const val FILE_URL = "file_url"

    /**
     * header for HttpRequest
     */
    const val FILE_URLS = "file_urls"

    /**
     * header for HttpRequest
     */
    const val MAX_PAGES_TO_PROCESS = "max_pages_to_process"

    /**
     * default list of categories.
     */
    val LIST_CATEGORIES: List<String> = listOf(
        "Advertising & Marketing",
        "Automotive",
        "Bank Charges & Fees",
        "Legal & Professional Services",
        "Insurance",
        "Meals & Entertainment",
        "Office Supplies & Software",
        "Taxes & Licenses",
        "Travel",
        "Rent & Lease",
        "Repairs & Maintenance",
        "Payroll",
        "Utilities",
        "Job Supplies",
        "Grocery"
    )
}