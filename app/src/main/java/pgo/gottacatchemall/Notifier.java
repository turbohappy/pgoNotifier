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

		NotificationCompat.Builder builder = new NotificationCompat.Builder(this.context)
				.setSmallIcon(R.drawable.ic_info_black_24dp)
				.setContentTitle(pokeNotification.title)
				.setContentIntent(PendingIntent.getActivity(this.context, 0, new Intent(), 0))
				.setAutoCancel(false)
				.setVibrate(new long[]{0, 1000, 500, 1000, 500, 1000})
				.setLights(Color.RED, 3000, 3000)
				.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

		Notification notification = builder.build();

		RemoteViews contentView = new RemoteViews(this.context.getPackageName(), R.layout.notification);
		contentView.setImageViewBitmap(R.id.image, pokeNotification.bitmap);
		contentView.setTextViewText(R.id.title, pokeNotification.title);
		contentView.setTextViewText(R.id.text, pokeNotification.longText);
		notification.bigContentView = contentView;

		// Build Notification with Notification Manager
		notificationManager.notify(NOTIFICATION_ID, notification);
	}
}
