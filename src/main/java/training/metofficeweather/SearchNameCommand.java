package training.metofficeweather;

import training.metofficeweather.data.Locations;

import java.util.HashMap;

public class SearchNameCommand {
    public void Execute(String input, HashMap<String, Locations> locationsHashMap) {
        locationsHashMap.keySet().stream().filter(e -> e.startsWith(input)).
                forEach(f -> System.out.println(f + ", ID:"+ locationsHashMap.get(f).id));
    }
}
