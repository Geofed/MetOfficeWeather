package training.metofficeweather;

import training.metofficeweather.data.Locations;

import java.util.HashMap;

public class ExitCommand implements Command{
	@Override
	public void Execute(String input, HashMap<String, Locations> locationsHashMap) {
		System.exit(1);
	}
}
