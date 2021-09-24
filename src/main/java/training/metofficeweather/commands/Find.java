package training.metofficeweather.commands;

import training.metofficeweather.Command;
import training.metofficeweather.data.Locations;

import java.util.HashMap;
import java.util.Locale;
import java.util.stream.Stream;

public class Find implements Command {
	@Override
	public void Execute(String input, HashMap<String, Locations> locationsHashMap) {
		boolean match = locationsHashMap.keySet().stream().anyMatch(e -> e.equals(input.toUpperCase(Locale.ROOT)));
		if (match) {
			System.out.println("Found a match");
			locationsHashMap
					.keySet()
					.stream()
					.filter(e -> e.toUpperCase(Locale.ROOT).equals(input.toUpperCase(Locale.ROOT)))
					.forEach(e -> System.out.println(locationsHashMap.get(e)));
		}
	}
}
