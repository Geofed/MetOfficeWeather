package training.metofficeweather;

import com.fasterxml.jackson.databind.ObjectMapper;
import training.metofficeweather.commands.Weather;
import training.metofficeweather.data.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static training.metofficeweather.Main.InitJson;
import static training.metofficeweather.Main.InitLocalJson;
import static training.metofficeweather.WeatherApplication.localFlag;

public class WeatherInfo {
    private String locationId;
    private WeatherSiteRep info;
    private String locationName;
    private HashMap<String, Locations> nameAndLocations = new HashMap<>();
    private HashMap<Date, WeatherDataAttributeRep> weatherReps = new HashMap<Date, WeatherDataAttributeRep>();

    // These Hash Maps contain key: day in date format, value: Integer of degrees Celsius. these are different to the Date key above as they don't store time of day.
    private HashMap<Date, Integer> currentLocMaxTemp = new HashMap<>();
    private HashMap<Date, Integer> currentLocMinTemp = new HashMap<>();

    private HashMap<String, Locations> idAndLocations = new HashMap<>();
    public WeatherInfo(String initLocationId, String apiKey) {
        initLocationId = initLocationId.trim();

        // call function to fill the nameandlocation map
        if (localFlag) {
            initLocalMap();
        } else {
            initMap();
        }

        // check input, final value of locationId is expected to be numbers in string form
        this.locationId = checkInput(initLocationId);

        this.locationName = getLocationName();
        System.out.println(this.locationName);


        //this.apiKey = apiKey;
        //this.info = populateInfo(this.locationId);
        try {
            this.info = localFlag ? populateLocalInfo(this.locationId) : populateInfo(this.locationId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH");
        SimpleDateFormat formatDay = new SimpleDateFormat("yyyy-MM-dd");

        for (WeatherDataAtrributePeriod p : info.locationAttributes.weatherDataAttributeLocation.period) {

            Integer tempTempMax = -5000;
            Integer tempTempMin = 5000;

            for (WeatherDataAttributeRep r : p.rep) {
                try {
                    weatherReps.put(format.parse(p.value.substring(0, 10) + "-" + (Integer.parseInt(r.minAfterMidnight) / 60)), r);

                    tempTempMax = (tempTempMax < Integer.parseInt(r.temp)) ? Integer.parseInt(r.temp) : tempTempMax;
                    tempTempMin = (tempTempMin > Integer.parseInt(r.temp)) ? Integer.parseInt(r.temp) : tempTempMin;

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            try {

                currentLocMaxTemp.put(formatDay.parse(p.value.substring(0, 10)), tempTempMax);
                currentLocMinTemp.put(formatDay.parse(p.value.substring(0, 10)), tempTempMin);

            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
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

            // hashmap of < NAME , location>
            locationsRoot.locations.location.forEach(e -> nameAndLocations.put(e.name.toUpperCase(Locale.ROOT), e));
            // hashmap of <id, location>
            locationsRoot.locations.location.forEach(e -> idAndLocations.put(e.id, e));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void initLocalMap() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            LocationsRoot locationsRoot = mapper.readValue(Paths.get("Data/sitelist.json").toFile(), LocationsRoot.class);
            // hashmap of < NAME , id > both entries are strings
            locationsRoot.locations.location.forEach(e -> nameAndLocations.put(e.name.toUpperCase(Locale.ROOT), e));
        } catch (IOException e) {
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

    private WeatherSiteRep populateLocalInfo(String locationId) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            WeatherRoot out = mapper.readValue(Paths.get("Data/" + locationId + ".json").toFile(), WeatherRoot.class);
            return out.weatherSiteRep;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new WeatherSiteRep();
    }

    public String getLocationName() {

        if(!weatherReps.isEmpty()) {
            return this.info.locationAttributes.weatherDataAttributeLocation.name;
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

    public Set<String> getListOfID() {
        return this.idAndLocations.keySet();
    }

    public HashMap<String, Locations> getIdAndLocations() {
        return idAndLocations;
    }
}
