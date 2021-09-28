package training.metofficeweather;

import com.fasterxml.jackson.databind.ObjectMapper;
import training.metofficeweather.data.WeatherDataAtrributePeriod;
import training.metofficeweather.data.WeatherDataAttributeRep;
import training.metofficeweather.data.WeatherRoot;
import training.metofficeweather.data.WeatherSiteRep;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class WeatherInfo {
    private final String locationId;
    private WeatherSiteRep info;
    private HashMap<Date, WeatherDataAttributeRep> weatherReps = new HashMap<Date, WeatherDataAttributeRep>();

    public WeatherInfo(String locationId, String apiKey) {
        this.locationId = locationId;
        //this.apiKey = apiKey;
        this.info = populateInfo(locationId);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH");

        for (WeatherDataAtrributePeriod p : info.locationAttributes.weatherDataAttributeLocation.period) {
            for (WeatherDataAttributeRep r : p.rep) {
                try {
                    weatherReps.put(format.parse(p.value.substring(0, 10) + "-" + (Integer.parseInt(r.minAfterMidnight) / 60)), r);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }

        weatherReps.keySet().stream().sorted().forEach( d -> {
            System.out.println(d.toString() + " " + weatherReps.get(d).toString());
        });
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

    public HashMap<Date, WeatherDataAttributeRep> getWeatherReps() {
        return weatherReps;
    }

    public List<Date> getSortedKeys() {
        List<Date> keys = new ArrayList<>(weatherReps.keySet());
        Collections.sort(keys);
        return keys;
    }

}
