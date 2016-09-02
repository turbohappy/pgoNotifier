package pgo.gottacatchemall;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import java.util.List;

/**
 * Created by bdavis on 8/30/16.
 */
public class Notifier {
	private static final int NOTIFICATION_ID = 72;
	private Context context;

	public Notifier(Context context) {
		this.context = context;
	}

	public void createOrUpdateNotification(PokeNotification pokeNotification, PokeNotification previousNotification) {
		NotificationManager notificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);

		if (pokeNotification == null) {
			notificationManager.cancel(NOTIFICATION_ID);
			return;
		}

		NotificationCompat.Builder builder = createNotificationBuilder(pokeNotification);
		if (hasNewPokemon(pokeNotification, previousNotification)) {
			builder.setVibrate(new long[]{0, 400, 200, 400, 200, 400, 200, 400})
					.setLights(Color.RED, 3000, 3000)
					.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
		} else {
			builder.setVibrate(new long[]{1})
					.setLights(Color.RED, 0, 0)
					.setSound(null);
		}
		Notification notification = builder.build();
		notification.bigContentView = createBigContentView(pokeNotification);
		notificationManager.notify(NOTIFICATION_ID, notification);
	}

	private NotificationCompat.Builder createNotificationBuilder(PokeNotification pokeNotification) {
		return new NotificationCompat.Builder(this.context)
				.setSmallIcon(R.drawable.ic_info_black_24dp)
				.setContentTitle(pokeNotification.title)
				.setContentIntent(PendingIntent.getActivity(this.context, 0, new Intent(), 0))
				.setAutoCancel(false);
	}

	private RemoteViews createBigContentView(PokeNotification pokeNotification) {
		RemoteViews contentView = new RemoteViews(this.context.getPackageName(), R.layout.notification);
		contentView.setImageViewBitmap(R.id.image, pokeNotification.bitmap);
		contentView.setTextViewText(R.id.title, pokeNotification.title);
		contentView.setTextViewText(R.id.text, pokeNotification.longText);
		return contentView;
	}

	public boolean hasNewPokemon(PokeNotification newNotification, PokeNotification oldNotification) {
		if (oldNotification == null) {
			return true;
		}

		for (Marker currPokemon : newNotification.pokemon) {
			if (!hasMatchingPokemon(currPokemon, oldNotification.pokemon)) {
				return true;
			}
		}
		return false; //default
	}

	private boolean hasMatchingPokemon(Marker needle, List<Marker> haystack) {
		if (needle == null) {
			return false;
		}
		for (Marker currHay : haystack) {
			if (needle.time == currHay.time) {
				return true;
			}
		}
		return false; //default
	}
}
