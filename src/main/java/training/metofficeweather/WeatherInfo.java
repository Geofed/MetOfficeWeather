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
    private HashMap<Date, WeatherDataAttributeRep> weatherReps = new HashMap<>();
    private ArrayList<Day> monday;
    private ArrayList<Day> tuesday;
    private ArrayList<Day> wednesday;
    private ArrayList<Day> thursday;
    private ArrayList<Day> friday;
    private ArrayList<Day> saturday;
    private ArrayList<Day> sunday;
    private ArrayList<ArrayList<Day>> days;
    private String lat;
    private String lon;
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
        this.info = populateInfo(locationId);
        this.lat = info.locationAttributes.weatherDataAttributeLocation.lat;
        this.lon = info.locationAttributes.weatherDataAttributeLocation.lon;
        this.sunday = new ArrayList<>();
        this.monday = new ArrayList<>();
        this.tuesday = new ArrayList<>();
        this.wednesday = new ArrayList<>();
        this.thursday = new ArrayList<>();
        this.friday = new ArrayList<>();
        this.saturday = new ArrayList<>();
        this.days = new ArrayList<>();
        this.days.add(monday);
        this.days.add(tuesday);
        this.days.add(wednesday);
        this.days.add(thursday);
        this.days.add(friday);
        this.days.add(saturday);
        this.days.add(sunday);

        //this.info = populateInfo(this.locationId);
        try {
            this.info = localFlag ? populateLocalInfo(this.locationId) : populateInfo(this.locationId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH");
        SimpleDateFormat formatDay = new SimpleDateFormat("yyyy-MM-dd");

        for (WeatherDataAtrributePeriod weatherDataAtrributePeriod : info.locationAttributes.weatherDataAttributeLocation.period) {
            weatherDataAtrributePeriod.rep.forEach(e -> {
                Date date = null;
                try {
                    date = format.parse(weatherDataAtrributePeriod.value.substring(0, 10) + "-" + (Integer.parseInt(e.minAfterMidnight) / 60));
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                switch (cal.get(Calendar.DAY_OF_WEEK)) {
                    case 0: sunday.add(new Day(DayofTheWeek.SUNDAY, date, e)); break;
                    case 1: monday.add(new Day(DayofTheWeek.MONDAY, date, e)); break;
                    case 2: tuesday.add(new Day(DayofTheWeek.TUESDAY, date, e)); break;
                    case 3: wednesday.add(new Day(DayofTheWeek.WEDNESDAY, date, e)); break;
                    case 4: thursday.add(new Day(DayofTheWeek.THURSDAY, date, e)); break;
                    case 5: friday.add(new Day(DayofTheWeek.FRIDAY, date, e)); break;
                    case 6: saturday.add(new Day(DayofTheWeek.SATURDAY, date, e)); break;
                }
            });
        }

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


    public ArrayList<Day> getDayHours(DayofTheWeek day) {
        switch (day) {
            case SUNDAY -> {
                return (ArrayList<Day>) sunday.subList(1, sunday.size());
            }
            case MONDAY -> {
                return (ArrayList<Day>) monday.subList(1, monday.size());
            }
            case TUESDAY -> {
                return (ArrayList<Day>) tuesday.subList(1, tuesday.size());
            }
            case WEDNESDAY -> {
                return (ArrayList<Day>) wednesday.subList(1, wednesday.size());
            }
            case THURSDAY -> {
                return (ArrayList<Day>) thursday.subList(1, thursday.size());
            }
            case FRIDAY -> {
                return (ArrayList<Day>) friday.subList(1, friday.size());
            }
            case SATURDAY -> {
                return (ArrayList<Day>) saturday.subList(1, saturday.size());
            }
        }
        return null;
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

    public ArrayList<Day> getMonday() {
        return monday;
    }

    public ArrayList<Day> getTuesday() {
        return tuesday;
    }

    public ArrayList<Day> getWednesday() {
        return wednesday;
    }

    public ArrayList<Day> getThursday() {
        return thursday;
    }

    public ArrayList<Day> getFriday() {
        return friday;
    }

    public ArrayList<Day> getSaturday() {
        return saturday;
    }

    public ArrayList<Day> getSunday() {
        return sunday;
    }

    public ArrayList<ArrayList<Day>> getDays() {
        return days;
    }

    public String getLat() {
        return lat;
    }

    public String getLon() {
        return lon;
    }
     public String getApiKey() {
        return System.getenv("MAP_API_KEY");
     }

    public Set<String> getListOfID() {
        return this.idAndLocations.keySet();
    }

    public HashMap<String, Locations> getIdAndLocations() {
        return idAndLocations;
    }

}
