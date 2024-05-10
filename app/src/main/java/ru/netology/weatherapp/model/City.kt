package ru.netology.weatherapp.model

data class City(
    val id: Int,
    val name: String,
    val title: String,
    val titleDative: String?,
    val titlePrepositional: String?,
    val selected: Boolean = false,
) {
    companion object {
        // 2 Встроенных города
        val MOSCOW = City(
            id = 190,
            name = "moscow",
            title = "Москва",
            titleDative = "Москву",
            titlePrepositional = "Москве",
        )
        val SAINT_PETERSBURG = City(
            id = 227,
            name = "saint_petersburg",
            title = "Санкт-Петербург",
            titleDative = "Санкт-Петербургу",
            titlePrepositional = "Санкт-Петербурге",
        )
    }
}