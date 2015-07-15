package de.fu_berlin.agdb.crepe.json.algebra.operators.numeric;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import de.fu_berlin.agdb.crepe.algebra.Match;
import de.fu_berlin.agdb.crepe.algebra.operators.numeric.Equal;
import de.fu_berlin.agdb.crepe.json.algebra.JSONMatch;
import de.fu_berlin.agdb.crepe.json.algebra.JSONOperator;

import java.io.IOException;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "@class")
@JsonDeserialize(using = JSONEqual.JSONEqualDeserializer.class)
public class JSONEqual extends JSONMatch<Match> {
    private String attribute;
    private Object toObject;
    private List<JSONOperator<?>> ofOperands;

    public JSONEqual(String attribute, List<JSONOperator<?>> ofOperands) {
        this.attribute = attribute;
        this.ofOperands = ofOperands;
    }

    public JSONEqual(String attribute, Object toObject) {
        this.attribute = attribute;
        this.toObject = toObject;
    }

    @Override
    public Match getAlgebraElement() {
        if (ofOperands != null && ofOperands.size() >= 2) {
            // TODO: When the underlying structure supports it, add all available operands
            return new Equal(
                    attribute,
                    ofOperands.get(0).getAlgebraElement(),
                    ofOperands.get(1).getAlgebraElement()
            );
        } else {
            return new Equal(attribute, toObject);
        }
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public Object getToObject() {
        return toObject;
    }

    public void setToObject(Object toObject) {
        this.toObject = toObject;
    }

    public List<JSONOperator<?>> getOfOperands() {
        return ofOperands;
    }

    public void setOfOperands(List<JSONOperator<?>> ofOperands) {
        this.ofOperands = ofOperands;
    }

    public static class JSONEqualDeserializer extends JsonDeserializer<JSONEqual> {
        private static final String PROPERTY_ATTRIBUTE = "attribute";
        private static final String PROPERTY_COMPARE_TO = "toObject";
        private static final String PROPERTY_OPERANDS = "ofOperands";
        private static final Class<JSONEqual> targetType = JSONEqual.class;

        @Override
        public JSONEqual deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            String attribute = null;
            Object compareTo = null;
            List<JSONOperator<?>> operands = null;

            JsonToken token = jp.nextValue();

            // TODO: Check for an IEvent as second parameter??
            while (token != null && token != JsonToken.NOT_AVAILABLE && token != JsonToken.END_OBJECT) {
                String currentName = jp.getCurrentName();
                if (currentName != null) {
                    switch (currentName) {
                        case PROPERTY_ATTRIBUTE:
                            attribute = jp.getText();
                            break;
                        case PROPERTY_COMPARE_TO:
                            if (token.isScalarValue()) {
                                switch (token) {
                                    case VALUE_STRING:
                                        compareTo = jp.getText();
                                        break;
                                    case VALUE_NUMBER_INT:
                                        compareTo = jp.getIntValue();
                                        break;
                                    case VALUE_NUMBER_FLOAT:
                                        compareTo = jp.getDoubleValue();
                                        break;
                                    case VALUE_TRUE:
                                        compareTo = Boolean.TRUE;
                                        break;
                                    case VALUE_FALSE:
                                        compareTo = Boolean.FALSE;
                                        break;
                                }
                            } else {
                                compareTo = jp.readValueAs(Object.class);
                            }
                            break;
                        case PROPERTY_OPERANDS:
                            operands = jp.readValueAs(new TypeReference<List<JSONOperator<?>>>() {});
                            break;
                    }
                }
                token = jp.nextValue();
            }

            String msg = null;

            if (attribute != null && !attribute.isEmpty()) {
                if (operands != null && operands.size() >= 2)
                    return new JSONEqual(attribute, operands);
                else if (compareTo != null)
                    return new JSONEqual(attribute, compareTo);
                else {
                    msg = String.format(
                            "Could not deserialize instance of %s, need to either specify '%s' or '%s'.",
                            targetType, PROPERTY_COMPARE_TO, PROPERTY_OPERANDS
                    );
                }
            } else {
                msg = String.format(
                        "Could not deserialize instance of %s, property '%s' is missing.",
                        targetType, PROPERTY_ATTRIBUTE
                );
            }

            throw new InvalidFormatException(
                    msg,
                    jp.getCurrentLocation(),
                    null,
                    JSONEqual.class
            );
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JSONEqual jsonEqual = (JSONEqual) o;

        if (attribute != null ? !attribute.equals(jsonEqual.attribute) : jsonEqual.attribute != null) return false;
        if (toObject != null ? !toObject.equals(jsonEqual.toObject) : jsonEqual.toObject != null) return false;
        return !(ofOperands != null ? !ofOperands.equals(jsonEqual.ofOperands) : jsonEqual.ofOperands != null);

    }

    @Override
    public int hashCode() {
        int result = attribute != null ? attribute.hashCode() : 0;
        result = 31 * result + (toObject != null ? toObject.hashCode() : 0);
        result = 31 * result + (ofOperands != null ? ofOperands.hashCode() : 0);
        return result;
    }
}
