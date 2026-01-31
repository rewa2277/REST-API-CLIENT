import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
public class SimpleWeatherAPI {

    public static void main(String[] args) {
        try {
            String city = "Mumbai";
            String apiKey = "29ea3e10c2b60fd3024cb94306766ae5";   // Put your OpenWeather API key here

            String url = "https://api.openweathermap.org/data/2.5/weather?q="
                    + city + "&appid=" + apiKey + "&units=metric";

            // Create HTTP Client
            HttpClient client = HttpClient.newHttpClient();

            // Create HTTP Request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            // Send request
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            String json = response.body();

            // Simple manual parsing (basic)
            String cityName = extractValue(json, "\"name\":\"", "\"");
            String temp = extractValue(json, "\"temp\":", ",");
            String humidity = extractValue(json, "\"humidity\":", ",");
            String condition = extractValue(json, "\"description\":\"", "\"");

            // Display structured output
            System.out.println("=================================");
            System.out.println("        Weather Report           ");
            System.out.println("=================================");
            System.out.println("City        : " + cityName);
            System.out.println("Temperature : " + temp + " °C");
            System.out.println("Humidity    : " + humidity + " %");
            System.out.println("Condition   : " + condition);
            System.out.println("=================================");

        } catch (Exception e) {
            System.out.println("Error fetching weather data!");
            e.printStackTrace();
        }
    }

    // Method to extract value from JSON string
    public static String extractValue(String json, String key, String endChar) {
        int startIndex = json.indexOf(key);
        if (startIndex == -1) return "N/A";

        startIndex = startIndex + key.length();
        int endIndex = json.indexOf(endChar, startIndex);

        return json.substring(startIndex, endIndex);
    }
}