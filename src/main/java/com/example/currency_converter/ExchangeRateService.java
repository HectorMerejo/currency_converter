package com.example.currency_converter;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExchangeRateService {
    private static final String API_KEY = "8d7e0efcae0bf70bfe12541277224cae";
    private static final String API_URL = "http://api.exchangeratesapi.io/v1/latest?access_key=" + API_KEY;
    private static final Logger logger = LoggerFactory.getLogger(ExchangeRateService.class);

    public static double getExchangeRate(String sourceCurrency, String targetCurrency) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(API_URL);

            try (CloseableHttpResponse response = httpClient.execute(request)) {
                return parseExchangeRate(response, sourceCurrency, targetCurrency);
            }

        } catch (Exception e) {
            //creates a logger instance
            //logs an error message along with the exception
            logger.error("An error occurred while fetching exchange rate", e);
            return -1; // Handle error case appropriately
        }
    }

    private static double parseExchangeRate(HttpResponse response, String sourceCurrency, String targetCurrency) {
        try {
            HttpEntity entity = response.getEntity();
            String responseString = EntityUtils.toString(entity);

            // Print the JSON response to the console
//            System.out.println("API Response: " + responseString);

            JSONObject jsonResponse = new JSONObject(responseString);

            if(jsonResponse.has("rates")) {
                JSONObject ratesObject = jsonResponse.getJSONObject("rates");

                if(ratesObject.has(sourceCurrency) && ratesObject.has(targetCurrency)) {
                    double sourceRate = ratesObject.getDouble(sourceCurrency);
                    double targetRate = ratesObject.getDouble(targetCurrency);

                    // Calculate the exchange rate between source and target currencies
                    return targetRate / sourceRate;
                }
            }
            // Return -1 to indicate failure or an invalid exchange rate
            return -1;
        }catch (Exception e) {
            // Handle IO exception
            logger.error("IOException occurred while parsing exchange rate.", e);
            return -1;
        }

    }
}
