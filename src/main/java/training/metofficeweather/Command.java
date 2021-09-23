package training.metofficeweather;

import training.metofficeweather.data.Locations;

import java.util.HashMap;

public interface Command {
	public void Execute(String input, HashMap<String, Locations> locationsHashMap);
}
