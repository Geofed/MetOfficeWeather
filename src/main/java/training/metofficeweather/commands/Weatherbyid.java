package training.metofficeweather.commands;

import training.metofficeweather.Command;
import training.metofficeweather.data.Locations;

import java.util.HashMap;

public class Weatherbyid implements Command {
    @Override
    public void Execute(String input, HashMap<String, Locations> locationsHashMap) {
        Weather myWeather = new Weather();
        locationsHashMap
                .keySet()
                .stream()
                .filter(e -> locationsHashMap.get(e).id.equals(input))
                .forEach(f -> myWeather.Execute(locationsHashMap.get(f).name, locationsHashMap));
    }
}

