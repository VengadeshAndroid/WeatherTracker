package com.nooro.weather.model

data class ErrorResponse(
    val error: ErrorDetail? = null
)

data class ErrorDetail(
    var code: Int? = null,
    var message: String? = null
)