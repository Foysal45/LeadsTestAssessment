package com.example.leadsassessment.repository

import com.example.leadsassessment.api.endpoint.ApiInterface

class AppRepository(private val apiInterface: ApiInterface) {


   suspend fun getCategoryList(categoryId: Int?) = apiInterface.getCategoryList(categoryId)

}