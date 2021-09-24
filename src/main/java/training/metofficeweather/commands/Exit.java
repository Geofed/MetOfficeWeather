package training.metofficeweather.commands;

import training.metofficeweather.Command;
import training.metofficeweather.data.Locations;

import java.util.HashMap;

public class Exit implements Command {
	@Override
	public void Execute(String input, HashMap<String, Locations> locationsHashMap) {
		System.exit(1);
	}
}
