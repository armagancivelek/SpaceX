package com.armagancivelek.spacex.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.armagancivelek.spacex.model.RocketResponse
import com.armagancivelek.spacex.repository.RocketRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class RocketViewModel(application: Application) : AndroidViewModel(application) {

    val repo = RocketRepository()
    val rockets: MutableLiveData<NetworkResult<List<RocketResponse>>> = MutableLiveData()
    val rocket: MutableLiveData<NetworkResult<RocketResponse>> = MutableLiveData()

    init {
        getRockets()
    }

    fun getOneRocket(rocketId: String) = viewModelScope.launch {
        rocket.postValue(NetworkResult.Loading())
        val response: Response<RocketResponse>
        try {
            response = repo.getOneRocket(rocketId)
            rocket.postValue(handleOneRocket(response))


        } catch (e: Exception) {

            Log.d("ABC", "Hata: ${e}")


        }
    }
    fun getRockets() = viewModelScope.launch {
        rockets.postValue(NetworkResult.Loading())
        val response: Response<List<RocketResponse>>

        try {

            response = repo.getRoockets()
            rockets.postValue(handleRocketResponse(response))


        } catch (e: Exception) {


        }

    }

    private fun handleOneRocket(response: Response<RocketResponse>): NetworkResult<RocketResponse> =
        when {
            response.isSuccessful -> NetworkResult.Success(
                response.body()!!
            )


            response.message().toString().contains("timeout") -> NetworkResult.Error(
                "Zaman aşımı",
                response.body()
            )

            else -> NetworkResult.Error("hata", response.body())
        }

    private fun handleRocketResponse(response: Response<List<RocketResponse>>): NetworkResult<List<RocketResponse>> =
        when {
            response.isSuccessful && !response.body().isNullOrEmpty() -> NetworkResult.Success(
                response.body()!!
            )

            response.code() == 402 -> NetworkResult.Error("Api key limited", response.body())

            response.body().isNullOrEmpty() -> NetworkResult.Error(
                "Veriler bulunamadı",
                response.body()
            )

            response.message().toString().contains("timeout") -> NetworkResult.Error(
                "Zaman aşımı",
                response.body()
            )

            else -> NetworkResult.Error("hata", response.body())


        }


}