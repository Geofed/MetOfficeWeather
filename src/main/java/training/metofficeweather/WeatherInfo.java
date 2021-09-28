package training.metofficeweather;

import com.fasterxml.jackson.databind.ObjectMapper;
import training.metofficeweather.data.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class WeatherInfo {
    private String locationId;
    private WeatherSiteRep info;
    private HashMap<String, Locations> nameAndLocations = new HashMap<>();
    private HashMap<Date, WeatherDataAttributeRep> weatherReps = new HashMap<Date, WeatherDataAttributeRep>();

    public WeatherInfo(String initLocationId, String apiKey) {

        // call function to fill the nameandlocation map
        initMap();

        // check input, final value of locationId is expected to be numbers in string form
        this.locationId = checkInput(initLocationId);

        //this.apiKey = apiKey;
        this.info = populateInfo(this.locationId);

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
/*
        weatherReps.keySet().stream().sorted().forEach( d -> {
            System.out.println(d.toString() + " " + weatherReps.get(d).toString());
        });

 */
/*
        for (String name: nameAndLocations.keySet()) {
            System.out.println(nameAndLocations.get(name).id);
        }

 */
    }

    private String checkInput(String initLocationId) {
        // check if string is of name or id format
        Boolean nameOrNot = initLocationId.matches("[a-zA-Z]+");
        // if a name is input, will have to search for location id

        if (nameOrNot) {
            this.locationId = searchID(initLocationId);
        } else {
            return initLocationId;
        }
        //System.out.println(this.locationId);
        return this.locationId;
    }

    private String searchID(String name) {
        return nameAndLocations.get(name.toUpperCase()).id;
    }

    private void initMap() {
        try {
            URL url = new URL("http://datapoint.metoffice.gov.uk/public/data/val/wxfcs/all/json/sitelist?key=" + System.getenv("API_KEY"));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("accept", "application/json");
            InputStream responseStream = connection.getInputStream();
            ObjectMapper mapper = new ObjectMapper();
            LocationsRoot locationsRoot = mapper.readValue(responseStream, LocationsRoot.class);

            // hashmap of < NAME , id > both entries are strings
            locationsRoot.locations.location.forEach(e -> nameAndLocations.put(e.name.toUpperCase(Locale.ROOT), e));
        } catch (Exception e) {
            e.printStackTrace();
        }

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

    public String getLocationName() {
        for (String n: nameAndLocations.keySet()) {
            if (nameAndLocations.get(n).id.equals(this.locationId)) {
                return n;
            }
        }

        return "";
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
