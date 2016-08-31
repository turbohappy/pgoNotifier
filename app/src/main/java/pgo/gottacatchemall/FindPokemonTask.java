package pgo.gottacatchemall;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

/**
 * Created by bdavis on 8/30/16.
 */
public class FindPokemonTask extends AsyncTask<Location, Void, PokeNotification> {
	private Exception exception;
	private FindPokemonTaskFinished finished;

	public FindPokemonTask(FindPokemonTaskFinished finished) {
		this.finished = finished;
	}

	@Override
	protected PokeNotification doInBackground(Location... location) {
		try {
			Marker currLoc = new Marker("Home", location[0].getLatitude(), location[0].getLongitude()
					, System.currentTimeMillis() / 1000, -1);
			currLoc.color = Marker.BLUE;
			List<Marker> pokemon = new FindPokemon().find(currLoc);
			if (pokemon == null || pokemon.size() == 0) {
				return null;
			} else {
				Bitmap map = getMap(mapUrl(currLoc, pokemon));
				return new PokeNotification(map, pokemon);
			}
		} catch (Exception e) {
			this.exception = e;
			return null;
		}
	}

	protected void onPostExecute(PokeNotification pokeNotification) {
		// TODO: check this.exception

		finished.finished(pokeNotification);
	}

	private String mapUrl(Marker currLoc, List<Marker> pokemons) {
		String output = "http://maps.googleapis.com/maps/api/staticmap?size=1200x600&";
		output += "maptype=roadmap&format=png&visual_refresh=true";

		output += "&center=" + String.valueOf(currLoc.lat) + "," + String.valueOf(currLoc.lng);
		output += currLoc.toUrlString();
		for (Marker pokemon : pokemons) {
			output += pokemon.toUrlString();
		}

		return output;
	}

	private Bitmap getMap(String mapUrl) throws IOException {
		Bitmap bitmap = BitmapFactory.decodeStream((InputStream) new URL(mapUrl).getContent());
		return bitmap;
	}

	public interface FindPokemonTaskFinished {
		public void finished(PokeNotification pokeNotification);
	}
}
