package ru.netology.weatherapp.api

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class OffsetDateTimeGsonAdapter @Inject constructor() : TypeAdapter<OffsetDateTime>() {
    companion object {
        private val serverFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssX")
    }

    override fun write(out: JsonWriter, value: OffsetDateTime) {
        out.value(serverFormat.format(value))
    }

    override fun read(`in`: JsonReader): OffsetDateTime =
        OffsetDateTime.parse(`in`.nextString(), serverFormat)
}
