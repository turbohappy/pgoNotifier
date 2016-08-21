package pgo.gottacatchemall;

import android.graphics.Bitmap;

/**
 * Created by bdavis on 8/21/16.
 */
public class PokeNotification {
	public Bitmap bitmap;
	public String title;
	public String shortText;
	public String longText;

	public PokeNotification(Bitmap bitmap, String title, String shortText, String longText) {
		this.bitmap = bitmap;
		this.title = title;
		this.shortText = shortText;
		this.longText = longText;
	}
}
