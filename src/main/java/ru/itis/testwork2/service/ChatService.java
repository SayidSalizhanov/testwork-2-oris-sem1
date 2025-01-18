package ru.itis.testwork2.service;

import org.json.JSONException;
import org.json.JSONObject;
import ru.itis.testwork2.util.HttpClient;
import ru.itis.testwork2.util.HttpClientImpl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class ChatService {

    private final String weatherApiKey = "ec6ae61f58c44f36d3bd7d4f99c9993a";
    private final String exchangeApiKey = "aebe42fc858f83411bf550a6";
    private final String weatherUrl = "http://api.openweathermap.org/data/2.5//weather";
    private final String exchangeUrlFormat = "https://v6.exchangerate-api.com/v6/%s/latest/%s";
    private final HttpClient httpClient = new HttpClientImpl();

    public String getCommands() {
        Map<String, String> commands = new HashMap<>();
        commands.put("list", "выводит список доступных команд");
        commands.put("weather <город>", "выводит погоду в указанном городе");
        commands.put("exchange <валюта>", "выводит курс указанной валюты к рублю");
        commands.put("quit", "выводит на главную страницу");

        String answer;

        answer = "Доступные команды:\n" +
                "-list: выводит список доступных команд\n" +
                "-weather <город>: выводит погоду в указанном городе\n" +
                "-exchange <валюта>: выводит курс указанной валюты к рублю\n" +
                "-quit: выводит на главную страницу\n";

        return answer;
    }

    public String getWeather(String cityName) {
        Map<String, String> params = new HashMap<>();
        params.put("q", cityName);
        params.put("units", "metric");
        params.put("lang", "en");
        params.put("appid", weatherApiKey);

        String answer;

        try {
            String response = httpClient.get(weatherUrl, Map.of(), params);

            JSONObject jsonObject = new JSONObject(response);

            answer = "Погода в городе %s: %s".formatted(cityName, String.valueOf(jsonObject.getJSONObject("main").getDouble("temp")));
        } catch (Exception e) {
            answer = "Такого города не существует";
        }

        return answer;
    }

    public String getExchangeRate(String rate) {
        String response = httpClient.get(exchangeUrlFormat.formatted(exchangeApiKey, rate), Map.of(), Map.of());

        JSONObject jsonObject = new JSONObject(response);

        String answer;
        try {
            answer = "Курс %s к рублю: %s".formatted(rate, String.valueOf(jsonObject.getJSONObject("conversion_rates").get("RUB")));
        } catch (JSONException e) {
            answer = "Такой валюты не существует";
        }

        return answer;
    }
}
