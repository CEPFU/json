package de.fu_berlin.agdb.crepe.json.algebra.notifications;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import de.fu_berlin.agdb.crepe.algebra.notifications.Notification;
import de.fu_berlin.agdb.crepe.json.algebra.JSONAlgebraElement;
import de.fu_berlin.agdb.crepe.json.algebra.JSONOperator;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "@class")
@JsonSubTypes({@JsonSubTypes.Type(JSONVerboseNotification.class)})
public abstract class JSONNotification<T extends Notification> implements JSONAlgebraElement<T> {
    protected JSONOperator<?> rule;
    // Let's ignore this for now
    // protected JSONAlgebra algebra;

    public JSONOperator<?> getRule() {
        return rule;
    }

    public void setRule(JSONOperator<?> rule) {
        this.rule = rule;
    }
}
