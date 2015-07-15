package de.fu_berlin.agdb.crepe.json.algebra;

import de.fu_berlin.agdb.crepe.json.algebra.notifications.JSONVerboseNotification;
import de.fu_berlin.agdb.crepe.json.algebra.operators.numeric.JSONEqual;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class JSONProfileTest {
    @Test
    public void testSimpleProfile() throws Exception {
        String json = "{\n" +
                "        \"rule\": {\n" +
                "            \"@class\": \"JSONEqual\",\n" +
                "            \"attribute\": \"myAttribute\",\n" +
                "            \"toObject\": 0.5\n" +
                "        },\n" +
                "        \"notifications\": [\n" +
                "            {\n" +
                "                \"@class\": \"JSONVerboseNotification\",\n" +
                "                \"message\": \"This is a message!\"\n" +
                "            }\n" +
                "        ]\n" +
                "    }";

        JSONProfile expected = new JSONProfile();
        expected.setRule(new JSONEqual("myAttribute", 0.5));
        JSONVerboseNotification notification = new JSONVerboseNotification("This is a message!");
        expected.setNotifications(Collections.singletonList(notification));

        JSONProfile deserialized = TestUtil.OBJECT_MAPPER.readValue(json, JSONProfile.class);

        assertEquals(expected, deserialized);
    }
}
