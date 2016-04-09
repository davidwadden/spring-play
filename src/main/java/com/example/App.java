package com.example;

import hello.wsdl.GetCityForecastByZIP;
import hello.wsdl.GetCityForecastByZIPResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;

@SpringBootApplication
public class App {

    @Autowired
    WeatherService weatherService;

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner() {
        return args -> {
            GetCityForecastByZIP getForecastByZIP = new GetCityForecastByZIP();
            getForecastByZIP.setZIP("60654");
            final GetCityForecastByZIPResponse forecastByZIP = weatherService.getForecastByZIP(getForecastByZIP);
            System.out.println("forecastByZIP.getGetCityForecastByZIPResult().getCity() = " + forecastByZIP.getGetCityForecastByZIPResult().getCity());
        };
    }

    @Bean
    WebServiceTemplate webServiceTemplate(Jaxb2Marshaller marshaller) {
        WebServiceTemplate webServiceTemplate = new WebServiceTemplate();
        webServiceTemplate.setDefaultUri("http://wsf.cdyne.com/WeatherWS/Weather.asmx");
        webServiceTemplate.setMarshaller(marshaller);
        webServiceTemplate.setUnmarshaller(marshaller);
        return webServiceTemplate;
    }

    @Bean
    Jaxb2Marshaller marshaller() {
        final Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setPackagesToScan("hello.wsdl");
        return marshaller;
    }

}