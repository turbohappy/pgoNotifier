package pgo.gottacatchemall;

/**
 * Created by bdavis on 8/20/16.
 */
public class Marker {
	public static String RED = "0xff0000";
	public static String GREEN = "0x00ff00";
	public static String BLUE = "0x0000ff";
	public static String YELLOW = "0xffff00";

	public String name;
	public String color;
	public double lat;
	public double lng;
	public long time;
	public int id;

	public Marker(String name, String color, double lat, double lng, long time, int id) {
		this.name = name;
		this.color = color;
		this.lat = lat;
		this.lng = lng;
		this.time = time;
		this.id = id;
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
