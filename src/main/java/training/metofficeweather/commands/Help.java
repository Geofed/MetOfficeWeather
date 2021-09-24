package training.metofficeweather.commands;

import training.metofficeweather.Command;
import training.metofficeweather.data.Locations;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

public class Help implements Command {
	@Override
	public void Execute(String input, HashMap<String, Locations> locationsHashMap) {
		String[] commands = new String[]{"Exit", "Filter", "Find", "Help", "List", "Weather"};
		for (String command : commands) {
			Class<?> commandClass = null;
			try {
				commandClass = Class.forName("training.metofficeweather.commands." +command);
			Object commandInstance = commandClass.newInstance();
			Method m = null;
				m = commandClass.getDeclaredMethod("Help", null);
				System.out.printf("%s: ", command);
				m.invoke(commandInstance, (Object) null);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	@Override
	public void Help() {
		System.out.println("Print this Help message");
	}
}
