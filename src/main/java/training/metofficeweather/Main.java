package training.metofficeweather;

import com.fasterxml.jackson.databind.ObjectMapper;
import training.metofficeweather.data.Root;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String args[]) throws IOException {
        // Create a neat value object to hold the URL
        URL url = new URL("http://datapoint.metoffice.gov.uk/public/data/val/wxfcs/all/json/sitelist?key=" + System.getenv("API_KEY"));

        // Open a connection(?) on the URL(??) and cast the response(???)
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        // Now it's "open", we can set the request method, headers etc.
        connection.setRequestProperty("accept", "application/json");
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/93.0.4577.82 Safari/537.36");

        // This line makes the request
        InputStream responseStream = connection.getInputStream();

        ObjectMapper mapper = new ObjectMapper();
        Root root = mapper.readValue(responseStream, Root.class);

        // Finally we have the response

//        PrintAllLocations(root);   <-- works
        UserInterface(root);     // asks for user input and print out desired location

    }

    public static void PrintAllLocations(Root root) {

        root.locations.location.forEach(e -> {
            System.out.println(e.name + ", ");
        });

    }

    public static void UserInterface(Root root){
        Scanner scanner = new Scanner(System.in);

        System.out.println("What location are you interested in?:");
        String input = scanner.nextLine();

        // need to search for input location and print the required info
        root.locations.location.forEach(e -> {

            // shud print all related detail to location input
            if (input.toUpperCase(Locale.ROOT).equals(e.name.toUpperCase(Locale.ROOT))) {
                System.out.println(e);
            }
                });

    }


}	
