package com.nazar.petproject.domain.location.entities

interface ILocation {
    val latitude: Double
    val longitude: Double
    val timeZoneID: String
    val city: String
}