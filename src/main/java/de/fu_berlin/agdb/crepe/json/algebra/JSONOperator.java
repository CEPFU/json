package de.fu_berlin.agdb.crepe.json.algebra;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import de.fu_berlin.agdb.crepe.algebra.Operator;
import de.fu_berlin.agdb.crepe.json.algebra.operators.JSONMatchToStation;
import de.fu_berlin.agdb.crepe.json.algebra.operators.logic.JSONAnd;

import static com.fasterxml.jackson.annotation.JsonSubTypes.*;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "@class")
@JsonSubTypes({
        @Type(JSONMatch.class),
        @Type(JSONAnd.class),
        @Type(JSONMatchToStation.class)
})
public abstract class JSONOperator<T extends Operator> implements JSONAlgebraElement<T> {
}
