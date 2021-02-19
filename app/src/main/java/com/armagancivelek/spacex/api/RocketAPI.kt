package com.armagancivelek.spacex.api

import com.armagancivelek.spacex.model.RocketResponse
import retrofit2.Response
import retrofit2.http.GET

interface RocketAPI {

    @GET("v3/rockets")
    suspend fun getRockets(): Response<List<RocketResponse>>
}