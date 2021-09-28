package training.metofficeweather;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
<<<<<<< Updated upstream
import training.metofficeweather.data.Locations;
=======
import org.springframework.ui.Model;
>>>>>>> Stashed changes
import training.metofficeweather.data.LocationsRoot;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
<<<<<<< Updated upstream
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;
=======

import static training.metofficeweather.Main.InitLocalJson;
import static training.metofficeweather.WeatherApplication.localFlag;
>>>>>>> Stashed changes

@Controller
public class WeatherController {

    @GetMapping
    String getLocations(Model model, @RequestParam("page") Optional<Integer> optionalpage, @RequestParam("placeName") Optional<String> placeName, @RequestParam("regionSelector") Optional<String> region, @RequestParam("seaLevel") Optional<Integer> seaLevel) {
        int page = optionalpage.orElse(0);
        List<Locations> locations = null;
        try {
            locations = InitJson().locations.location; //.subList(page*50, (page+1)*50);
        } catch (IOException e) {
            e.printStackTrace();
        }
<<<<<<< Updated upstream
        if (placeName.isPresent()) {
            locations = locations.stream().filter(e -> (e.name.toUpperCase(Locale.ROOT).startsWith(placeName.get().toUpperCase(Locale.ROOT)))).collect(Collectors.toList());
        }
        if (region.isPresent()) {
            if (!region.get().equals("any")) {
                locations = locations.stream().filter(e -> (e.region.equals(region.get()))).collect(Collectors.toList());
            }
        }
        if (seaLevel.isPresent()) {
            locations = locations.stream().filter(e -> (Integer.parseInt(e.elevation.replace(".0", "")) > seaLevel.get())).collect(Collectors.toList());
        }
        model.addAttribute("page", page);
        int bottomLimit = Math.max((page - 5), 0);
        int topLimit = Math.min((page+5), 121);
        model.addAttribute("bottomLimit", bottomLimit);
        model.addAttribute("topLimit", topLimit);
        model.addAttribute("apiKey", System.getenv("MAP_API_KEY"));
        if (locations.size() > 50) {
            model.addAttribute("locations", locations.subList(page*50, (page+1)*50));
        } else {
            model.addAttribute("locations", locations);
=======

        SiteInfo siteInfo = new SiteInfo(locations);

        return ModelAndView("index", "siteInfo", siteInfo);
    }
     */

    @GetMapping
    String getLocations(Model model) {
        try {
            model.addAttribute("locations", localFlag ? InitLocalJson().locations.location : InitJson().locations.location);
        } catch (IOException e) {
            e.printStackTrace();
>>>>>>> Stashed changes
        }
        return "index";
    }

    @RequestMapping("/weatherInfo")
    ModelAndView weatherInfo(@RequestParam("locationId") String locationId) {
        return new ModelAndView("info", "weatherInfo", new WeatherInfo(locationId, System.getenv("MAP_API_KEY"))) ;
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
