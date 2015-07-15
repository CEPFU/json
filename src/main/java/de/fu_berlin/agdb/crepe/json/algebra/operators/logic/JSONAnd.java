package de.fu_berlin.agdb.crepe.json.algebra.operators.logic;

import de.fu_berlin.agdb.crepe.algebra.Operator;
import de.fu_berlin.agdb.crepe.algebra.operators.logic.And;
import de.fu_berlin.agdb.crepe.json.algebra.JSONOperator;

import java.util.List;

public class JSONAnd extends JSONOperator<And> {
    private List<JSONOperator<?>> ofOperands;

    public JSONAnd() {

    }

    public JSONAnd(List<JSONOperator<?>> ofOperands) {
        this.ofOperands = ofOperands;
    }

    @Override
    public And getAlgebraElement() {
        if (ofOperands != null && ofOperands.size() >= 2) {

            Operator last = ofOperands.get(0).getAlgebraElement();
            for (int i = 1; i < ofOperands.size(); i++) {
                last = new And(
                        last,
                        ofOperands.get(i).getAlgebraElement()
                );
            }

            // Safe, since the loop above runs at least once!
            return (And) last;
        }
        else // TODO: When the underlying structure supports it, allow 0 or 1 operands
            return null;
    }

    public List<JSONOperator<?>> getOfOperands() {
        return ofOperands;
    }

    public void setOfOperands(List<JSONOperator<?>> ofOperands) {
        this.ofOperands = ofOperands;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JSONAnd jsonAnd = (JSONAnd) o;

        return !(ofOperands != null ? !ofOperands.equals(jsonAnd.ofOperands) : jsonAnd.ofOperands != null);
    }

    @Override
    public int hashCode() {
        return ofOperands != null ? ofOperands.hashCode() : 0;
    }
}
