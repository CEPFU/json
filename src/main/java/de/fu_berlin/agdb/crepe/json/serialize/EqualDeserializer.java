package de.fu_berlin.agdb.crepe.json.serialize;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import de.fu_berlin.agdb.crepe.algebra.Operator;
import de.fu_berlin.agdb.crepe.algebra.operators.numeric.Equal;

import java.io.IOException;

public class EqualDeserializer extends JsonDeserializer<Equal> {

    @Override
    public Equal deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        String attribute = null;
        Object compareWith = null;
        Operator op1 = null, op2 = null;

        JsonToken token = jp.nextValue();

        // TODO: Check for an IEvent as second parameter??
        while (token != null && token != JsonToken.NOT_AVAILABLE && token != JsonToken.END_OBJECT) {
            String currentName = jp.getCurrentName();
            if (currentName != null) {
                switch (currentName) {
                    case "attribute":
                        attribute = jp.getText();
                        break;
                    case "compareWith":
                        if (token.isScalarValue()) {
                            switch (token) {
                                case VALUE_STRING:
                                    compareWith = jp.getText();
                                    break;
                                case VALUE_NUMBER_INT:
                                    compareWith = jp.getIntValue();
                                    break;
                                case VALUE_NUMBER_FLOAT:
                                    compareWith = jp.getDoubleValue();
                                    break;
                                case VALUE_TRUE:
                                    compareWith = Boolean.TRUE;
                                    break;
                                case VALUE_FALSE:
                                    compareWith = Boolean.FALSE;
                                    break;
                            }
                        } else {
                            compareWith = jp.readValueAs(Object.class);
                        }
                        break;
                    case "firstOperator":
                        op1 = jp.readValueAs(Operator.class);
                        break;
                    case "secondOperator":
                        op2 = jp.readValueAs(Operator.class);
                        break;
                }
            }
            token = jp.nextValue();
        }

        if (attribute != null && !attribute.isEmpty()) {
            if (op1 != null && op2 != null)
                return new Equal(attribute, op1, op2);
            else if (compareWith != null) {
                return new Equal(attribute, compareWith);
            }
        }

        return null;
    }
}
