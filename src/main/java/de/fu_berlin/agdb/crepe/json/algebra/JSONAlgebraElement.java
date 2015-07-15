package de.fu_berlin.agdb.crepe.json.algebra;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import de.fu_berlin.agdb.crepe.json.algebra.notifications.JSONNotification;

/**
 * This interface is implemented by all elements of the JSON Algebra.
 * The method {@link #getAlgebraElement()} is used to generate an element
 * usable in the original context of {@link de.fu_berlin.agdb.crepe.algebra}.
 *
 * @param <MappedElement> Algebra element specified in {@link de.fu_berlin.agdb.crepe.algebra}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "@class")
@JsonSubTypes({
        @JsonSubTypes.Type(JSONOperator.class),
        @JsonSubTypes.Type(JSONNotification.class),
        @JsonSubTypes.Type(JSONProfile.class)
})
public interface JSONAlgebraElement<MappedElement> {
    /**
     * Generates an instance of the mapped element type.
     */
    public MappedElement getAlgebraElement();
}
