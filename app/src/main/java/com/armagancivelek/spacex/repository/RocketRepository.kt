package com.armagancivelek.spacex.repository

import com.armagancivelek.spacex.api.RetrofitInstance

class RocketRepository {
    suspend fun getRoockets() = RetrofitInstance.api.getRockets()
}