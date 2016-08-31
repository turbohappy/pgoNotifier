package pgo.gottacatchemall;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

/**
 * Created by bdavis on 8/30/16.
 */
public class Notifier {
	private static int NOTIFICATION_ID = 72;
	private Context context;
	private NotificationManager notificationManager;

	public Notifier(Context context) {
		this.context = context;
		notificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
	}

	public void createOrUpdateNotification(PokeNotification pokeNotification) {
		if (pokeNotification == null) {
			notificationManager.cancel(NOTIFICATION_ID);
			return;
		}
		showNotification(pokeNotification);
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

		// Build Notification with Notification Manager
		notificationManager.notify(NOTIFICATION_ID, builder.build());
	}
}
