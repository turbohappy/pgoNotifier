package pgo.gottacatchemall;

import android.graphics.Bitmap;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bdavis on 8/21/16.
 */
public class PokeNotification {
	public Bitmap bitmap;
	public String title;
	public String shortText;
	public String longText;

	public PokeNotification(Bitmap bitmap, List<Marker> pokemon) {
		this.bitmap = bitmap;
		this.setTitleAndText(pokemon);
	}

	private void setTitleAndText(List<Marker> pokemon) {
		this.title = String.valueOf(pokemon.size()) + " pokemon in your area";
		this.longText = textualize(pokemon);
		this.shortText = "S" + this.longText;
	}

	private String textualize(List<Marker> pokemon) {
		List<String> text = new ArrayList<>();
		for (Marker currPokemon : pokemon) {
			text.add(currPokemon.toNotificationString());
		}
		return TextUtils.join(", ", text);
	}
}
