package training.metofficeweather.commands;

import training.metofficeweather.Command;
import training.metofficeweather.data.Locations;

import java.util.HashMap;
import java.util.Locale;

public class Findbyid implements Command {
    @Override
    public void Execute(String input, HashMap<String, Locations> locationsHashMap) {
            locationsHashMap
                    .keySet()
                    .stream()
                    .filter(e -> locationsHashMap.get(e).id.equals(input)).
                    forEach(f -> System.out.println(locationsHashMap.get(f)));

    }
}
