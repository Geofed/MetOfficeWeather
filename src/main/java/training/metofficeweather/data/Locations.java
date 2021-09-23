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
		return  "elevation = '" + elevation + '\'' +
				"\nid = '" + id + '\'' +
				"\nlatitude = '" + latitude + '\'' +
				"\nlongitude = '" + longitude + '\'' +
				"\nname = '" + name + '\'' +
				"\nregion = '" + region + '\'' +
				"\nunitaryAuthArea = '" + unitaryAuthArea + '\'' +
				(obsSource == null ? "" : "\n, obsSource='" + obsSource) + '\'' +
				(nationalPark == null ? "" : "\n, nationalPark='" + nationalPark)+ '\'';
	}
}
