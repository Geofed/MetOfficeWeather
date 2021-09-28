package training.metofficeweather;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WeatherApplication {
	public static boolean localFlag = false;

	public static void main(String[] args) {
		try {
			if (args[0].equals("-l")) {
				localFlag = true;
			}
		} catch (Exception e) {
			System.out.println("Getting your weather from the clouds ☁ ☁ ☁");
		}
		SpringApplication.run(WeatherApplication.class, args);
	}

}
