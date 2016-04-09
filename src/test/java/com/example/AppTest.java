package com.example;

import hello.wsdl.GetCityForecastByZIP;
import hello.wsdl.GetCityForecastByZIPResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.test.client.MockWebServiceServer;
import org.springframework.xml.transform.StringSource;

import javax.xml.transform.Source;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.ws.test.client.RequestMatchers.connectionTo;
import static org.springframework.ws.test.client.ResponseCreators.withPayload;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(App.class)
public class AppTest {

    @Autowired
    WeatherService weatherService;

    @Autowired
    private WebServiceTemplate webServiceTemplate;

    @Test
    public void foo() {

        Source requestPayload = new StringSource(
            "    <GetCityForecastByZIP xmlns=\"http://ws.cdyne.com/WeatherWS/\">\n" +
                "      <ZIP>string</ZIP>\n" +
                "    </GetCityForecastByZIP>");
        Source responsePayload = new StringSource(
            "<GetCityForecastByZIPResponse xmlns=\"http://ws.cdyne.com/WeatherWS/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
                "      <GetCityForecastByZIPResult>\n" +
                "        <Success>boolean</Success>\n" +
                "        <ResponseText>string</ResponseText>\n" +
                "        <State>string</State>\n" +
                "        <City>blah</City>\n" +
                "        <WeatherStationCity>string</WeatherStationCity>\n" +
                "        <ForecastResult>\n" +
                "          <Forecast>\n" +
                "            <Date>dateTime</Date>\n" +
                "            <WeatherID>short</WeatherID>\n" +
                "            <Desciption>string</Desciption>\n" +
                "            <Temperatures xsi:nil=\"true\" />\n" +
                "            <ProbabilityOfPrecipiation xsi:nil=\"true\" />\n" +
                "          </Forecast>\n" +
                "          <Forecast>\n" +
                "            <Date>dateTime</Date>\n" +
                "            <WeatherID>short</WeatherID>\n" +
                "            <Desciption>string</Desciption>\n" +
                "            <Temperatures xsi:nil=\"true\" />\n" +
                "            <ProbabilityOfPrecipiation xsi:nil=\"true\" />\n" +
                "          </Forecast>\n" +
                "        </ForecastResult>\n" +
                "      </GetCityForecastByZIPResult>\n" +
                "    </GetCityForecastByZIPResponse>");


        MockWebServiceServer server = MockWebServiceServer.createServer(webServiceTemplate);
        server
            .expect(connectionTo("http://wsf.cdyne.com/WeatherWS/Weather.asmx"))
//            .andExpect(payload(requestPayload))
            .andRespond(withPayload(responsePayload));
        GetCityForecastByZIP getCityForecastByZIP = new GetCityForecastByZIP();

        GetCityForecastByZIPResponse forecastByZIP = weatherService.getForecastByZIP(getCityForecastByZIP);
        server.verify();
        assertThat(forecastByZIP.getGetCityForecastByZIPResult().getCity(), is("anystring"));
    }
}
