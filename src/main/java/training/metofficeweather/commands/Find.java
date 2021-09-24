package training.metofficeweather.commands;

import training.metofficeweather.Command;
import training.metofficeweather.data.Locations;

import java.util.HashMap;
import java.util.stream.Stream;

public class Find implements Command {
	@Override
	public void Execute(String input, HashMap<String, Locations> locationsHashMap) {
		Stream<String> keyStream = locationsHashMap.keySet().stream();
		boolean match = keyStream.anyMatch(e -> e.equals(input));
		if (match) {
			keyStream.filter(e -> e.equals(input)).forEach(System.out::println);
		}
	}
}
