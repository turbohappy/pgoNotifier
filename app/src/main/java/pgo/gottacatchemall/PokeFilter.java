package pgo.gottacatchemall;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by bdavis on 8/23/16.
 */
public class PokeFilter {
	private static Integer[] goodIdsArr = {/*10,13,16, */   4, 5, 6, 25, 26, 63, 64, 65, 66, 67, 68, 86, 87, 88, 89,
	/*92,93,94,*/106, 107, 108, 113, 131, 138, 139, 140, 141, 142, 143, 144, 145, 146, 147, 148, 149};
	private static List<Integer> goodIds = Arrays.asList(goodIdsArr);

	public static List<Marker> filterPokemon(List<Marker> in) {
		List<Marker> out = new ArrayList<>();
		Map<String, Marker> unique = new HashMap<>();
		for (Marker curr : in) {
			String combinedId = String.valueOf(curr.id) + "-" + String.valueOf(curr.time);
			if (!unique.containsKey(combinedId)) {
				unique.put(combinedId, curr);
				if (goodIds.contains(curr.id)) {
					out.add(curr);
				}
			}
		}
		return out;
	}
}
