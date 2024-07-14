package com.nazar.petproject.data.weather.data_sources

import com.nazar.petproject.data.weather.WeatherDataSource
import com.nazar.petproject.data.weather.model.current_weather.CurrentWeather
import com.nazar.petproject.data.weather.model.daily_weather.DailyWeather
import com.nazar.petproject.domain.IResult
import com.nazar.petproject.domain.location.entities.ILocation
import com.nazar.petproject.domain.settings.entities.units.UnitFor
import com.nazar.petproject.domain.weather.WeatherRepository
import com.nazar.petproject.domain.weather.entities.daily_weather.IDailyWeather
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.ktor.getApiResponse
import com.skydoves.sandwich.message
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onException
import com.skydoves.sandwich.onSuccess
import io.ktor.client.HttpClient
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.parameter
import javax.inject.Inject

private const val CURRENT_WEATHER_PARAMETERS = "temperature_2m,relative_humidity_2m,apparent_temperature,is_day,precipitation,rain,showers,snowfall,weather_code,cloud_cover,pressure_msl,surface_pressure,wind_speed_10m,wind_direction_10m,wind_gusts_10m"
private const val DAILY_WEATHER_PARAMETERS = "weather_code,temperature_2m_max,temperature_2m_min,apparent_temperature_max,apparent_temperature_min,sunrise,sunset,daylight_duration,sunshine_duration,uv_index_max,uv_index_clear_sky_max,precipitation_sum,rain_sum,showers_sum,snowfall_sum,precipitation_hours,precipitation_probability_max,wind_speed_10m_max,wind_gusts_10m_max,wind_direction_10m_dominant,shortwave_radiation_sum"

internal class WeatherApiDataSource @Inject constructor(private val httpClient: HttpClient) : WeatherDataSource {

    override suspend fun getCurrentWeather(
        temperatureUnit: UnitFor.Temperature,
        windSpeedUnit: UnitFor.WindSpeed,
        location: ILocation,
    ): IResult<CurrentWeather, WeatherRepository.Exceptions> {
        val response = httpClient.getApiResponse<CurrentWeather> {
            addLatitudeLongitude(location.latitude, location.longitude)
            addComaSeparatedWeatherParameters("current", CURRENT_WEATHER_PARAMETERS)
            addTimeZone(location.timeZoneID)
            addTemperatureAndWindSpeedUnits(temperatureUnit, windSpeedUnit)
        }
        return response.handleSuccessErrorException()
    }

    override suspend fun getDailyWeather(
        temperatureUnit: UnitFor.Temperature,
        windSpeedUnit: UnitFor.WindSpeed,
        location: ILocation,
    ): IResult<IDailyWeather, WeatherRepository.Exceptions> {
        val response = httpClient.getApiResponse<DailyWeather> {
            addLatitudeLongitude(location.latitude, location.longitude)
            addComaSeparatedWeatherParameters("daily", DAILY_WEATHER_PARAMETERS)
            addTimeZone(location.timeZoneID)
            addTemperatureAndWindSpeedUnits(temperatureUnit, windSpeedUnit)
        }
        return response.handleSuccessErrorException()
    }
}

private fun HttpRequestBuilder.addLatitudeLongitude(latitude: Double, longitude: Double) {
    parameter("latitude", latitude)
    parameter("longitude", longitude)
}

private fun HttpRequestBuilder.addTimeZone(timezone: String) {
    parameter("timezone", timezone)
}

private fun HttpRequestBuilder.addComaSeparatedWeatherParameters(key: String, params: String) {
    params.split(",").forEach { parameter ->
        parameter(key, parameter)

    }
}

private fun HttpRequestBuilder.addTemperatureAndWindSpeedUnits(temperatureUnit: UnitFor.Temperature, windSpeedUnit: UnitFor.WindSpeed) {
    when(temperatureUnit) {
        UnitFor.Temperature.CELSIUS -> { /* don't need to add parameter because CELSIUS is the default one */}
        UnitFor.Temperature.FAHRENHEIT -> parameter("temperature_unit", "fahrenheit")
    }

    val windSpeedUnitKey = "wind_speed_unit"
    when(windSpeedUnit) {
        UnitFor.WindSpeed.KILOMETERS_PER_HOUR -> { /* don't need to add parameter */}
        UnitFor.WindSpeed.MILES_PER_HOUR -> parameter(windSpeedUnitKey, "mph")
        UnitFor.WindSpeed.METERS_PER_SECOND -> parameter(windSpeedUnitKey, "ms")
        UnitFor.WindSpeed.KNOTS -> parameter(windSpeedUnitKey, "kn")
    }
}

private fun<T> ApiResponse<T>.handleSuccessErrorException(): IResult<T, WeatherRepository.Exceptions> {
    var result: IResult<T, WeatherRepository.Exceptions>? = null
    this.onSuccess {
        result = IResult.Success(data)
    }.onError {
        result = IResult.Error(WeatherRepository.Exceptions.ApiCallError(payload = message()))
    }.onException {
        result = IResult.Error(WeatherRepository.Exceptions.NetworkException(exception = throwable))
    }
    return result!!
}