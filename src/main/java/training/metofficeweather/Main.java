package training.metofficeweather;

import com.fasterxml.jackson.databind.ObjectMapper;
import training.metofficeweather.data.Locations;
import training.metofficeweather.data.LocationsRoot;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    private static LocationsRoot locationsRoot;
    private static HashMap<String, Locations> locationsHashMap;
    public static void main(String args[]) throws IOException {
        locationsRoot = InitJson();
        locationsHashMap = InitMap(locationsRoot);

        ReadEvaluatePrintLoop();
    }

    private static HashMap<String, Locations> InitMap(LocationsRoot locationsRoot) {
        HashMap<String, Locations> locationsMap = new HashMap<>();
        locationsRoot.locations.location.forEach(e -> locationsMap.put(e.name.toUpperCase(Locale.ROOT), e));
        return locationsMap;
    }

    private static LocationsRoot InitJson() throws IOException {
        URL url = new URL("http://datapoint.metoffice.gov.uk/public/data/val/wxfcs/all/json/sitelist?key=" + System.getenv("API_KEY"));
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("accept", "application/json");
        InputStream responseStream = connection.getInputStream();
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(responseStream, LocationsRoot.class);
    }

    public static void ReadEvaluatePrintLoop() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("View avaliable commands with 'help'");

        FindCommand find = new FindCommand();
        ListCommand list = new ListCommand();
        HelpCommand help = new HelpCommand();
        WeatherCommand weather = new WeatherCommand();

        while (true) {
            System.out.print(">>> ");
            String[] input = scanner.nextLine().split(" ", 2);
            switch (input[0]) {
                case "find":
                    find.Execute(input[1], locationsHashMap);
                    break;
                case "list":
                    list.Execute(null, locationsHashMap);
                    break;
                case "weather":
                    weather.Execute(input[1], locationsHashMap);
                    break;
                case "help":
                    help.Execute(null, null);
                    break;
            }
        }
    }
}
