package training.metofficeweather;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.Model;
import org.springframework.web.servlet.View;
import training.metofficeweather.data.Locations;
import training.metofficeweather.data.LocationsRoot;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

@Controller
public class WeatherController {

    /*
    @RequestMapping("/weather")
    ModelAndView home() {
        List<Locations> locations = null;
        try {
            locations= InitJson().locations.location;
        } catch (IOException e) {
            e.printStackTrace();
        }

        SiteInfo siteInfo = new SiteInfo(locations);

        return ModelAndView("index", "siteInfo", siteInfo);
    }
     */

    @GetMapping
    String getLocations(Model model) {
        try {
            model.addAttribute("locations", InitJson().locations.location);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "index";
    }

    @RequestMapping("/weatherInfo")
    ModelAndView weatherInfo(@RequestParam("locationId") String locationId) {
        return new ModelAndView("info", "weatherInfo", new WeatherInfo(locationId)) ;
    }

    private static LocationsRoot InitJson() throws IOException {
        URL url = new URL("http://datapoint.metoffice.gov.uk/public/data/val/wxfcs/all/json/sitelist?key=" + System.getenv("API_KEY"));
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("accept", "application/json");
        InputStream responseStream = connection.getInputStream();
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(responseStream, LocationsRoot.class);
    }
}
