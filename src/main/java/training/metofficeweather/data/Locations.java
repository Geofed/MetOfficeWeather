package training.metofficeweather.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
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

	public String getElevation() {
		return elevation;
	}

	public void setElevation(String elevation) {
		this.elevation = elevation;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getUnitaryAuthArea() {
		return unitaryAuthArea;
	}

	public void setUnitaryAuthArea(String unitaryAuthArea) {
		this.unitaryAuthArea = unitaryAuthArea;
	}

	public String getObsSource() {
		return obsSource;
	}

	public void setObsSource(String obsSource) {
		this.obsSource = obsSource;
	}

	public String getNationalPark() {
		return nationalPark;
	}

	public void setNationalPark(String nationalPark) {
		this.nationalPark = nationalPark;
	}
}
