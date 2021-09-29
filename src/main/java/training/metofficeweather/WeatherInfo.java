package training.metofficeweather;

import com.fasterxml.jackson.databind.ObjectMapper;
import training.metofficeweather.data.*;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class WeatherInfo {
    private final String locationId;
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

    public WeatherInfo(String locationId, String apiKey) {
        this.locationId = locationId;
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


        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH");

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
}
