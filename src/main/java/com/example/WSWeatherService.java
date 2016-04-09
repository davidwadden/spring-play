package com.example;

import hello.wsdl.GetCityForecastByZIP;
import hello.wsdl.GetCityForecastByZIPResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.client.core.SoapActionCallback;

@Service
class WSWeatherService implements WeatherService {

    @Autowired
    private WebServiceTemplate webServiceTemplate;

    @Override
    public GetCityForecastByZIPResponse getForecastByZIP(GetCityForecastByZIP getCityForecastByZIP) {

        return (GetCityForecastByZIPResponse) webServiceTemplate.marshalSendAndReceive(getCityForecastByZIP, new SoapActionCallback("http://ws.cdyne.com/WeatherWS/GetCityForecastByZIP"));
    }
}
