package pgo.gottacatchemall;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;

/**
 * Created by bdavis on 8/19/16.
 */
public class AlarmReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		final Notifier notifier = new Notifier(context);
		final FindPokemonTask findPokemonTask = new FindPokemonTask(new FindPokemonTask.FindPokemonTaskFinished() {
			@Override
			public void finished(PokeNotification pokeNotification) {
				notifier.createOrUpdateNotification(pokeNotification);
			}
		});
		SingleShotLocationProvider.requestSingleUpdate(context,
				new SingleShotLocationProvider.LocationCallback() {
					@Override
					public void onNewLocationAvailable(Location location) {
						findPokemonTask.execute(location);
					}
				});
	}

}
