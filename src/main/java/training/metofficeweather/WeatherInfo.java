package training.metofficeweather;

import training.metofficeweather.data.WeatherSiteRep;

public class WeatherInfo {
    private final String locationId;
    private WeatherSiteRep info;

    public WeatherInfo(String locationId) {
        this.locationId = locationId;
    }

    public String getLocationId() {
        return locationId;
    }
}
