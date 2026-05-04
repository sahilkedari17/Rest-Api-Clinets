import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class WeatherApp1 {

    public static void main(String[] args) {
        try {
            String urlString =
                "https://api.open-meteo.com/v1/forecast?latitude=18.52&longitude=73.85&current_weather=true";

            URL url = URI.create(urlString).toURL(); // fixed (no deprecated warning)

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(conn.getInputStream())
            );

            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            reader.close();

            String json = response.toString();

            // safer extraction
            String current = json.split("\"current_weather\":\\{")[1];

            String temp = current.split("\"temperature\":")[1].split(",")[0].trim();
            String wind = current.split("\"windspeed\":")[1].split(",")[0].trim();

            System.out.println("=== Weather Report ===");
            System.out.println("Temperature: " + temp + " °C");
            System.out.println("Wind Speed: " + wind + " km/h");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}