package pgo.gottacatchemall;

/**
 * Created by bdavis on 8/20/16.
 */
public class Marker {
	public String name;
	public String color;
	public double lat;
	public double lng;

	public Marker(String name, String color, double lat, double lng) {
		this.name = name;
		this.color = color;
		this.lat = lat;
		this.lng = lng;
	}

	private static final String SPACER = "%7C";

	public String toUrlString() {
		String output = "&markers=size:mid" + SPACER;
		output += "color:" + color + SPACER;
		output += "label:" + name.substring(0, 1) + SPACER;
		output += String.valueOf(lat) + "," + String.valueOf(lng);
		return output;
	}
}
