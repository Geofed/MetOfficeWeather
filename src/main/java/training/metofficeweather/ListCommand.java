package training.metofficeweather;

import training.metofficeweather.data.Locations;

import java.util.HashMap;

public class ListCommand implements Command{
	@Override
	public void Execute(String input, HashMap<String, Locations> locationsHashMap) {
//		locationsHashMap.keySet().forEach(System.out::println);
		locationsHashMap.keySet().forEach(e -> System.out.println(e + ", ID:"+ locationsHashMap.get(e).id));
	}
}
