package pgo.gottacatchemall;

import java.util.Arrays;
import java.util.List;

/**
 * Created by bdavis on 8/23/16.
 */
public class PokeFilter {
	private static Integer[] goodIdsArr = {10,13,16,    4,5,6,25,26,63,64,65,66,67,68,86,87,88,89,92,93,94,106,107,108,113,131,138,139,140,141,142,143,144,145,146,147,148,149 };
	private static List<Integer> goodIds = Arrays.asList(goodIdsArr);

	public static boolean desiredPokemon(int id) {
		return goodIds.contains(id);
	}
}
