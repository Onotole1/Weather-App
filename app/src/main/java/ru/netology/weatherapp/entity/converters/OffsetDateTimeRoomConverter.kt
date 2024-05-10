package ru.netology.weatherapp.entity.converters

import androidx.room.TypeConverter
import java.time.OffsetDateTime

class OffsetDateTimeRoomConverter {
    @TypeConverter
    fun fromString(value: String?): OffsetDateTime? {
        return value?.let { OffsetDateTime.parse(it) }
    }

    @TypeConverter
    fun dateToString(date: OffsetDateTime?): String? {
        return date?.toString()
    }
}
