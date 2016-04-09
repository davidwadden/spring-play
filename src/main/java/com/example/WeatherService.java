package com.example;

import hello.wsdl.GetCityForecastByZIP;
import hello.wsdl.GetCityForecastByZIPResponse;

public interface WeatherService {
    GetCityForecastByZIPResponse getForecastByZIP(GetCityForecastByZIP getCityForecastByZIP);

}
