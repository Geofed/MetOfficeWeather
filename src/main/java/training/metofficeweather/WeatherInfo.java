package training.metofficeweather;

import com.fasterxml.jackson.databind.ObjectMapper;
import training.metofficeweather.data.WeatherRoot;
import training.metofficeweather.data.WeatherSiteRep;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherInfo {
    private final String locationId;
    private WeatherSiteRep info;
    private String apiKey;

    public WeatherInfo(String locationId, String apiKey) {
        this.locationId = locationId;
        this.apiKey = apiKey;
        this.info = populateInfo(locationId);
    }

    private WeatherSiteRep populateInfo(String locationId) {
        try {
            URL url = new URL("http://datapoint.metoffice.gov.uk/public/data/val/wxfcs/all/json/" + locationId + "?res=3hourly&key=" + System.getenv("API_KEY"));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("accept", "application/json");
            InputStream responseStream = connection.getInputStream();
            ObjectMapper mapper = new ObjectMapper();
            WeatherRoot out = mapper.readValue(responseStream, WeatherRoot.class);
            return out.weatherSiteRep;
        }
        catch (Exception e){
           e.printStackTrace();
        }
        return new WeatherSiteRep();
    }

    public String getLocationId() {
        return locationId;
    }

    public WeatherSiteRep getInfo() {
        return info;
    }

    public void setInfo(WeatherSiteRep info) {
        this.info = info;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}
