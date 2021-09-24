package training.metofficeweather.commands;

import training.metofficeweather.Command;
import training.metofficeweather.data.Locations;

import java.util.HashMap;
import java.util.Locale;
/*
may be combined with find method

filters for locations that starts with ...
 */
public class Filter implements Command {
    @Override
    public void Execute(String input, HashMap<String, Locations> locationsHashMap) {
        locationsHashMap.keySet().stream().filter(e -> e.toUpperCase().startsWith(input.toUpperCase(Locale.ROOT))).
                forEach(f -> System.out.println(f + ", ID:"+ locationsHashMap.get(f).id));
    }
}
