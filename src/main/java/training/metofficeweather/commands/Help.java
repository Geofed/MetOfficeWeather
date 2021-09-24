package training.metofficeweather.commands;

import training.metofficeweather.Command;
import training.metofficeweather.data.Locations;

import java.util.HashMap;

public class Help implements Command {
	@Override
	public void Execute(String input, HashMap<String, Locations> locationsHashMap) {
		System.out.println("Avaliable Commands: \n\tList: List all avaliable locations \n\tFind: find specific site by name");
	}
}
