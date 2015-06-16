package de.fu_berlin.agdb.crepe.json.serialize;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import de.fu_berlin.agdb.crepe.algebra.Operator;
import de.fu_berlin.agdb.crepe.algebra.Profile;
import de.fu_berlin.agdb.crepe.algebra.notifications.Notification;
import de.fu_berlin.agdb.crepe.algebra.windows.EndlessWindow;
import de.fu_berlin.agdb.crepe.algebra.windows.IWindow;

import java.io.IOException;

public class ProfileDeserializer extends JsonDeserializer<Profile> {

    @Override
    public Profile deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        Operator rule = null;
        Notification[] notifications = new Notification[0];

        JsonToken token = jp.nextValue();
        while (token != null && token != JsonToken.NOT_AVAILABLE && token != JsonToken.END_OBJECT) {
            String currentName = jp.getCurrentName();
            if (currentName != null) {
                switch (currentName) {
                    case "rule":
                        rule = jp.readValueAs(Operator.class);

                        break;
                    case "notifications":
                        notifications = new Notification[]{};
                        break;
                }
            }
            token = jp.nextValue();
        }

        IWindow window = new EndlessWindow();

        return new Profile(rule, window.newInstance(), notifications);
    }
}
