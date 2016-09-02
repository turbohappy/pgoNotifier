package pgo.gottacatchemall;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class NotifierTest {
	private Notifier notifier;

	@Before
	public void setUp() {
		notifier = new Notifier(null);
	}

	@Test
	public void hasNewPokemon_no() throws Exception {
		List<Marker> pokemon = new ArrayList<>();
		pokemon.add(new Marker("Seel", 70.1, -40.1, 12345, 17));

		PokeNotification newNotification = new PokeNotification(null, pokemon);
		PokeNotification oldNotification = new PokeNotification(null, pokemon);

		boolean result = notifier.hasNewPokemon(newNotification, oldNotification);
		assertEquals(result, false);
	}
}