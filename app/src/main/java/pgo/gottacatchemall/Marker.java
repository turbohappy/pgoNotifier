package pgo.gottacatchemall;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bdavis on 8/20/16.
 */
public class Marker {
	public static String RED = "0xff0000";
	public static String GREEN = "0x00ff00";
	public static String BLUE = "0x0000ff";
	public static String YELLOW = "0xffff00";

	public String name;
	public double lat;
	public double lng;
	public long time;
	public int id;

	public double distance;
	public double timeDiffMins;
	public String color;

	public Marker(String name, double lat, double lng, long time, int id) {
		this.name = name;
		this.lat = lat;
		this.lng = lng;
		this.time = time;
		this.id = id;
	}

	private static final String SPACER = "%7C";

	public String toUrlString() {
		List<String> output = new ArrayList<>();
		output.add("&markers=size:mid");
		output.add("color:" + color);
		output.add("label:" + name.substring(0, 1));
		output.add(String.valueOf(lat) + "," + String.valueOf(lng));
		return TextUtils.join(SPACER, output);
	}

	public String toNotificationString() {
		return name + "(" + String.format("%.1f", distance) + "km/" + String.format("%.1f", timeDiffMins) + "mins)";
	}
}
