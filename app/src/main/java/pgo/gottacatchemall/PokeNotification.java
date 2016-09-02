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
	public String longText;
	public List<Marker> pokemon;

	public PokeNotification(Bitmap bitmap, List<Marker> pokemon) {
		this.bitmap = bitmap;
		this.pokemon = pokemon;
		this.setTitleAndText(this.pokemon);
	}

	private void setTitleAndText(List<Marker> pokemon) {
		this.title = String.valueOf(pokemon.size()) + " pokemon in your area";
		this.longText = textualize(pokemon);
	}

	private String textualize(List<Marker> pokemon) {
		List<String> text = new ArrayList<>();
		for (Marker currPokemon : pokemon) {
			text.add(currPokemon.toNotificationString());
		}
		return TextUtils.join(", ", text);
	}
}
