package com.armagancivelek.spacex.model

import com.google.gson.annotations.SerializedName

data class Diameter(
    @SerializedName("meters") val meters: Double,
    @SerializedName("feet") val feet: Double

)


