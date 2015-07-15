package de.fu_berlin.agdb.crepe.json.algebra.operators.test;

import de.fu_berlin.agdb.crepe.algebra.Operator;
import de.fu_berlin.agdb.crepe.algebra.operators.MockOperator;
import de.fu_berlin.agdb.crepe.json.algebra.JSONOperator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.runners.Parameterized.Parameter;
import static org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class JSONOperatorGeneratedEqualityTest {
    @Parameter(0)
    public Operator expected;
    @Parameter(1)
    public JSONOperator testObject;

    @Parameters
    public static Collection<Object> data() {
        return Arrays.asList(
                (Object[]) new Object[][]{
                        {new MockOperator(), new JSONMockOperator()}
                }
        );
    }

    @Test
    public void testEqualityOfGeneratedElements() throws Exception {
        Object generated = testObject.getAlgebraElement();

        assertEquals(
                "Generated algebra element of " + testObject.getClass().getSimpleName() + " does not match expected object.",
                expected,
                generated
        );
    }
}

