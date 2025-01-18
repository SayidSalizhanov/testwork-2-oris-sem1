package ru.itis.testwork2.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class HttpClientImpl implements HttpClient {

    public static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String get(String url, Map<String, String> headers, Map<String, String> params) {
        try {
            StringBuilder urlWithParams = new StringBuilder(url);
            if (!params.isEmpty()) {
                urlWithParams.append("?");
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    try {
                        String encodedKey = URLEncoder.encode(entry.getKey(), "UTF-8");
                        String encodedValue = URLEncoder.encode(entry.getValue(), "UTF-8");
                        urlWithParams.append(encodedKey).append("=").append(encodedValue).append("&");
                    } catch (UnsupportedEncodingException e) {
                        throw new RuntimeException(e);
                    }
                }
                urlWithParams.deleteCharAt(urlWithParams.length() - 1);
            }

            URL getUrl = new URL(urlWithParams.toString());
            HttpURLConnection connection = (HttpURLConnection) getUrl.openConnection();
            connection.setRequestMethod("GET");
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                connection.setRequestProperty(entry.getKey(), entry.getValue());
            }
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            String response = readResponse(connection);
            connection.disconnect();
            return response;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String post(String url, Map<String, String> headers, Map<String, String> data) {
        try {
            URL postUrl = new URL(url);
            HttpURLConnection postConnection = (HttpURLConnection) postUrl.openConnection();
            postConnection.setRequestMethod("POST");
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                postConnection.setRequestProperty(entry.getKey(), entry.getValue());
            }
            postConnection.setDoOutput(true);

            String jsonInput = objectMapper.writeValueAsString(data);
            try (OutputStream outputStream = postConnection.getOutputStream()) {
                byte[] input = jsonInput.getBytes(StandardCharsets.UTF_8);
                outputStream.write(input, 0, input.length);
            }

            String response = readResponse(postConnection);
            postConnection.disconnect();
            return response;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String put(String url, Map<String, String> headers, Map<String, String> data) {
        try {
            URL putUrl = new URL(url);
            HttpURLConnection putConnection = (HttpURLConnection) putUrl.openConnection();
            putConnection.setRequestMethod("PUT");
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                putConnection.setRequestProperty(entry.getKey(), entry.getValue());
            }
            putConnection.setDoOutput(true);

            String jsonInput = objectMapper.writeValueAsString(data);
            try (OutputStream outputStream = putConnection.getOutputStream()) {
                byte[] input = jsonInput.getBytes(StandardCharsets.UTF_8);
                outputStream.write(input, 0, input.length);
            }

            String response = readResponse(putConnection);
            putConnection.disconnect();
            return response;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String delete(String url, Map<String, String> headers, Map<String, String> data) {
        try {
            URL postUrl = new URL(url);
            HttpURLConnection postConnection = (HttpURLConnection) postUrl.openConnection();
            postConnection.setRequestMethod("DELETE");
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                postConnection.setRequestProperty(entry.getKey(), entry.getValue());
            }

            String response = readResponse(postConnection);
            postConnection.disconnect();
            return response;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String readResponse(HttpURLConnection connection) throws IOException {
        if (connection != null) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                StringBuilder content = new StringBuilder();
                String input;
                while ((input = reader.readLine()) != null) {
                    content.append(input);
                }
                return content.toString();
            }
        }
        return null;
    }
}