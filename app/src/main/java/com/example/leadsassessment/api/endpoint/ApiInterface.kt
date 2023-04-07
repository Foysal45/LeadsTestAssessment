package com.example.leadsassessment.api.endpoint

import com.example.leadsassessment.api.model.ErrorResponse
import com.haroldadmin.cnradapter.NetworkResponse
import com.example.leadsassessment.api.model.product_category.CategoryBaseModel
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    companion object {
        operator fun invoke(retrofit: Retrofit): ApiInterface {
            return retrofit.create(ApiInterface::class.java)
        }
    }


    @GET("category/")
    suspend fun getCategoryList(@Query("categoryId") categoryId: Int?): NetworkResponse<CategoryBaseModel, ErrorResponse>

}