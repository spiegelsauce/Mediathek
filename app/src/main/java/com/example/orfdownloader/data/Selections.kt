package com.example.orfdownloader.data

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Selections @Inject constructor() {
    lateinit var station: RadioStations
    var show: ShowDetails? = null
}