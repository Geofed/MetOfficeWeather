package training.metofficeweather;

import com.fasterxml.jackson.databind.ObjectMapper;
import training.metofficeweather.data.Locations;
import training.metofficeweather.data.LocationsRoot;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    private static LocationsRoot locationsRoot;
    private static HashMap<String, Locations> locationsHashMap;
    public static boolean localFlag = false;

    public static void main(String args[]) throws IOException {
        // In order to initialise from local test data instead of the met office api use the -l flag in program arguments
        try {
            if (args[0].equals("-l")) {
                localFlag = true;
            }
        } catch (Exception e) {
            System.out.println("Getting your weather from the clouds ☁ ☁ ☁");
        }

        locationsRoot = localFlag ? InitLocalJson() : InitJson();
        locationsHashMap = InitMap(locationsRoot);

        ReadEvaluatePrintLoop();
    }

    private static HashMap<String, Locations> InitMap(LocationsRoot locationsRoot) {
        HashMap<String, Locations> locationsMap = new HashMap<>();
        locationsRoot.locations.location.forEach(e -> locationsMap.put(e.name.toUpperCase(Locale.ROOT), e));
        return locationsMap;
    }

    public static LocationsRoot InitJson() throws IOException {
        URL url = new URL("http://datapoint.metoffice.gov.uk/public/data/val/wxfcs/all/json/sitelist?key=" + System.getenv("API_KEY"));
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("accept", "application/json");
        InputStream responseStream = connection.getInputStream();
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(responseStream, LocationsRoot.class);
    }

    static LocationsRoot InitLocalJson() throws IOException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            LocationsRoot metData = mapper.readValue(Paths.get("Data/sitelist.json").toFile(), LocationsRoot.class);
            return metData;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static void ReadEvaluatePrintLoop() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("View available commands with 'help'");
        
        while (true) {

            System.out.print(">>> ");
            String rawInput = scanner.nextLine();
            String[] input = rawInput.split(" ", 2);

            if (input.length == 1) input = new String[]{input[0], ""};

            try {

                Class<?> commandClass = Class.forName("training.metofficeweather.commands." + input[0].substring(0, 1).toUpperCase() + input[0].substring(1).toLowerCase());
                Object commandInstance = commandClass.newInstance();
                Method m = commandClass.getDeclaredMethod("Execute", String.class, HashMap.class);
                m.invoke(commandInstance, input[1],  locationsHashMap);

            } catch (Exception e) {

                //e.printStackTrace();
                System.out.println("Invalid command, type 'Help' to see available commands  :)");
            }
        }
    }
}
