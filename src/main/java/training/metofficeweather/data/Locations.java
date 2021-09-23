package training.metofficeweather.data;

public class Locations {
	public String elevation;
	public String id;
	public String latitude;
	public String longitude;
	public String name;
	public String region;
	public String unitaryAuthArea;
	public String obsSource;
	public String nationalPark;

	@Override
	public String toString() {
		return "Locations{" + "elevation='" + elevation + '\'' +
				", id='" + id + '\'' +
				", latitude='" + latitude + '\'' +
				", longitude='" + longitude + '\'' +
				", name='" + name + '\'' +
				", region='" + region + '\'' +
				", unitaryAuthArea='" + unitaryAuthArea + '\'' +
				", obsSource='" + obsSource + '\'' +
				", nationalPark='" + nationalPark + '\'' +
				'}';
	}
}
