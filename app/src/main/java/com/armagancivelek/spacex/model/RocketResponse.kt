package com.armagancivelek.spacex.model

import com.google.gson.annotations.SerializedName

data class RocketResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("active") val active: Boolean,
    @SerializedName("cost_per_launch") val cost_per_launch: Int,
    @SerializedName("success_rate_pct") val success_rate_pct: Int,
    @SerializedName("first_flight") val first_flight: String,
    @SerializedName("country") val country: String,
    @SerializedName("company") val company: String,
    @SerializedName("height") val height: Height,
    @SerializedName("diameter") val diameter: Diameter,
    @SerializedName("flickr_images") val flickr_images: List<String>,
    @SerializedName("wikipedia") val wikipedia: String,
    @SerializedName("description") val description: String,
    @SerializedName("rocket_id") val rocket_id: String,
    @SerializedName("rocket_name") val rocket_name: String,
    @SerializedName("rocket_type") val rocket_type: String

)

