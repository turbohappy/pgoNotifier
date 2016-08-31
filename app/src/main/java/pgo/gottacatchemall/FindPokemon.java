package pgo.gottacatchemall;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bdavis on 8/21/16.
 */
public class FindPokemon {
	//	private static final double KM_PER_HOUR_MAX = 16.0;
	private static final double KM_PER_HOUR_MAX = 6.0;

	public List<Marker> find(Marker currLoc) throws IOException, JSONException {
		List<Marker> output = new ArrayList<>();
		JSONObject data = readPokemonData(currLoc);
		JSONArray pokemons = data.getJSONArray("pokemons");
		for (int i = 0; i < pokemons.length(); i++) {
			JSONObject pokemon = pokemons.getJSONObject(i);
			Marker pokemonMarker = new Marker(pokemon.getString("pokemon_name"), pokemon.getDouble
					("latitude"), pokemon.getDouble("longitude"), pokemon.getLong("expires"), pokemon.getInt
					("pokemon_id"));
			if (PokeFilter.desiredPokemon(pokemonMarker.id)) {
				pokemonMarker.distance = calculateDistanceInKm(currLoc, pokemonMarker);
				pokemonMarker.timeDiffMins = (pokemonMarker.time - currLoc.time) / 60.0;
				double speed = neededSpeedInKph(pokemonMarker.distance, pokemonMarker.timeDiffMins);
				if (speed < KM_PER_HOUR_MAX) {
					pokemonMarker.color = calculateMarkerColor(speed);
					output.add(pokemonMarker);
				}
			}
		}
		return output;
	}

	private String calculateMarkerColor(double speed) {
		if (speed < (KM_PER_HOUR_MAX / 4.0)) {
			return Marker.GREEN;
		} else if (speed < (KM_PER_HOUR_MAX / 2.0)) {
			return Marker.YELLOW;
		} else {
			return Marker.RED;
		}
	}

	private JSONObject readPokemonData(Marker currLoc) throws IOException, JSONException {
		String url = "https://skiplagged.com/api/pokemon.php?bounds=";
		double latRad = latitudeRadius();
		double lngRad = longitudeRadius(currLoc.lat);
		url += String.valueOf(currLoc.lat > 0 ? currLoc.lat - latRad : currLoc.lat + latRad) + ",";
		url += String.valueOf(currLoc.lng > 0 ? currLoc.lng - lngRad : currLoc.lng + lngRad) + ",";
		url += String.valueOf(currLoc.lat > 0 ? currLoc.lat + latRad : currLoc.lat - latRad) + ",";
		url += String.valueOf(currLoc.lng > 0 ? currLoc.lng + lngRad : currLoc.lng - lngRad);

		String data = convertStreamToString((InputStream) new URL(url).getContent());
		return new JSONObject(data);
	}

	private double latitudeRadius() {
		return KM_PER_HOUR_MAX * (15.0 / 60) / 110.574;
	}

	private double longitudeRadius(double lat) {
		return KM_PER_HOUR_MAX * (15.0 / 60) / (111.320 * Math.cos(lat));
	}

	private String convertStreamToString(java.io.InputStream is) {
		java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
		return s.hasNext() ? s.next() : "";
	}

	private static final double R = 6372.795477598; //km

	private double neededSpeedInKph(double dist, double timeDiffMins) {
		double timeDiffInHrs = timeDiffMins / 60;
		double speedInKph = dist / timeDiffInHrs;
		return speedInKph;
	}

	private double calculateDistanceInKm(Marker from, Marker to) {
		double distance = R * Math.acos(Math.sin(toRad(from.lat)) * Math.sin(toRad(to.lat)) + Math.cos(toRad(from.lat)
		) * Math.cos(toRad(to.lat)) * Math.cos(toRad(from.lng) - toRad(to.lng)));
		return distance;
	}

	private double toRad(double degrees) {
		return degrees * Math.PI / 180;
	}
}
