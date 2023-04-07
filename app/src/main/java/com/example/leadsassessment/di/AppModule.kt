package com.example.leadsassessment.di

import com.example.leadsassessment.api.endpoint.ApiInterface
import com.example.leadsassessment.repository.AppRepository
import com.example.leadsassessment.ui.home_categoy_list.HomeViewModel
import com.example.leadsassessment.api.baseUrl.AppConstant
import com.example.leadsassessment.utils.RetrofitUtils.createCache
import com.example.leadsassessment.utils.RetrofitUtils.createOkHttpClient
import com.example.leadsassessment.utils.RetrofitUtils.getGson
import com.example.leadsassessment.utils.RetrofitUtils.retrofitInstance
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {

    single { getGson() }
    single { createCache(get()) }
    single { createOkHttpClient(get()) }
    single(named("normal")) { createOkHttpClient(get()) }

    single(named("api")) { retrofitInstance(AppConstant.BASE_URL, get(), get()) }

    single { ApiInterface(get(named("api"))) }

    single { AppRepository(get()) }

    viewModel { HomeViewModel(get()) }



}