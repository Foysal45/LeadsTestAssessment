package com.example.leadsassessment.api.model


import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("Message")
    var message: String? = ""
)