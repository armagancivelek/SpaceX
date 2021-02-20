package com.armagancivelek.spacex.api

import com.armagancivelek.spacex.model.RocketResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RocketAPI {

    @GET("v3/rockets")
    suspend fun getRockets(): Response<List<RocketResponse>>

    @GET("v3/rockets/{rocket_id}")
    suspend fun getOneRocket(
        @Path("rocket_id") rocketId: String

    ): Response<RocketResponse>

}