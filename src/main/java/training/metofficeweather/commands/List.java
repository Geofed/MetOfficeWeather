package training.metofficeweather.commands;

import training.metofficeweather.Command;
import training.metofficeweather.data.Locations;

import java.util.HashMap;

public class List implements Command {
	@Override
	public void Execute(String input, HashMap<String, Locations> locationsHashMap) {
		locationsHashMap.keySet().forEach(e -> System.out.println(e + ",ID:" + locationsHashMap.get(e).id));
	}
}
