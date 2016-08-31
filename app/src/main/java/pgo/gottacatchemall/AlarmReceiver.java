package pgo.gottacatchemall;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

/**
 * Created by bdavis on 8/19/16.
 */
public class AlarmReceiver extends BroadcastReceiver {
	private int nId = 1;
	private Context context;

	@Override
	public void onReceive(Context context, Intent intent) {
		this.context = context;
		SingleShotLocationProvider.requestSingleUpdate(context,
				new SingleShotLocationProvider.LocationCallback() {
					@Override
					public void onNewLocationAvailable(Location location) {
						new MapTask().execute(location);
					}
				});
	}

	private void showNotification(PokeNotification pokeNotification) {
		// Create Notification using NotificationCompat.Builder
		NotificationCompat.Builder builder = new NotificationCompat.Builder(this.context)
				.setSmallIcon(R.drawable.ic_info_black_24dp)
				.setContentTitle(pokeNotification.title)
				.setTicker(pokeNotification.shortText)
				.setContentText(pokeNotification.shortText)
				.setContentIntent(PendingIntent.getActivity(this.context, 0, new Intent(), 0))
				.setStyle(new NotificationCompat.BigPictureStyle()
						.bigPicture(pokeNotification.bitmap)
						.setSummaryText(pokeNotification.longText))
				.setAutoCancel(false);

		// Create Notification Manager
		NotificationManager notificationmanager = (NotificationManager) this.context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		// Build Notification with Notification Manager
		notificationmanager.notify(0, builder.build());
	}

	class MapTask extends AsyncTask<Location, Void, PokeNotification> {
		private Exception exception;

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

			if (pokeNotification != null) {
				showNotification(pokeNotification);
			}
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
	}
}
