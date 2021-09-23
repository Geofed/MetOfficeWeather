package training.metofficeweather;

import training.metofficeweather.data.Locations;

import java.util.HashMap;

public class HelpCommand implements Command {
	@Override
	public void Execute(String input, HashMap<String, Locations> locationsHashMap) {
		System.out.println("Avaliable Commands: \n\tList: List all avaliable locations \n\tFind: find specific site by name");
	}
}
