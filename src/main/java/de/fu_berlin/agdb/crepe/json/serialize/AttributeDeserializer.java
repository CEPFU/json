package de.fu_berlin.agdb.crepe.json.serialize;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import de.fu_berlin.agdb.crepe.data.Attribute;

import java.io.IOException;

public class AttributeDeserializer extends JsonDeserializer<Attribute> {
    @Override
    public Attribute deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        Object value = null;
        JsonToken token = jp.nextValue();
        while (token != null && token != JsonToken.NOT_AVAILABLE && token != JsonToken.END_OBJECT) {
            switch (jp.getCurrentName()) {
                case "value":
                    switch (token) {
                        case VALUE_STRING:
                            value = jp.getValueAsString();
                            break;
                        case VALUE_NUMBER_INT:
                            value = jp.getValueAsInt();
                            break;
                        case VALUE_NUMBER_FLOAT:
                            value = jp.getValueAsDouble();
                            break;
                        case VALUE_TRUE:
                            value = Boolean.TRUE;
                            break;
                        case VALUE_FALSE:
                            value = Boolean.FALSE;
                            break;
                        default:
                            try {
                                value = jp.readValueAs(Object.class);
                            } catch (IOException ignored) {}
                            break;
                    }
                    break;
            }
            token = jp.nextValue();
        }

        return new Attribute(value);
    }
}
