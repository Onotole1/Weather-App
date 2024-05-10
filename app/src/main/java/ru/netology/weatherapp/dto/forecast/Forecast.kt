package ru.netology.weatherapp.dto.forecast

import com.google.gson.annotations.SerializedName
import java.time.OffsetDateTime

data class Forecast(
    @SerializedName("astronomy")
    val astronomy: Astronomy,
    @SerializedName("date")
    val date: OffsetDateTime,
    @SerializedName("hours")
    val hours: List<Hour>,
    @SerializedName("links")
    val links: Links
)