package de.fu_berlin.agdb.crepe.json.algebra.operators.test;

import de.fu_berlin.agdb.crepe.json.algebra.JSONAlgebraElement;
import de.fu_berlin.agdb.crepe.json.algebra.notifications.JSONVerboseNotification;
import de.fu_berlin.agdb.crepe.json.algebra.operators.JSONMatchToStation;
import de.fu_berlin.agdb.crepe.json.algebra.operators.logic.JSONAnd;
import de.fu_berlin.agdb.crepe.json.algebra.operators.numeric.JSONEqual;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;

import static de.fu_berlin.agdb.crepe.json.algebra.TestUtil.OBJECT_MAPPER;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class JSONDeserializeTests {
    @Parameter(0)
    public JSONAlgebraElement expected;
    @Parameter(1)
    public String jsonValue;

    @Parameters
    public static Collection<Object> data() {
        return Arrays.asList(
                (Object[]) new Object[][]{
                        {new JSONMockOperator(), "{\"@class\": \"JSONMockOperator\"}"},
                        {new JSONEqual("myAttribute", 0.5), "{\"@class\": \"JSONEqual\", \"attribute\": \"myAttribute\", \"toObject\": 0.5}"},
                        {
                                new JSONAnd(Arrays.asList(new JSONMockOperator(), new JSONMockOperator(), new JSONMockOperator())),
                                "{\"@class\": \"JSONAnd\", \"ofOperands\": [{\"@class\": \"JSONMockOperator\"}, {\"@class\": \"JSONMockOperator\"}, {\"@class\": \"JSONMockOperator\"}]}"
                        },
                        {new JSONVerboseNotification("My message!"), "{\"@class\": \"JSONVerboseNotification\", \"message\": \"My message!\"}"},
                        {new JSONMatchToStation(new JSONMockOperator(), 125), "{\"@class\": \"JSONMatchToStation\", \"matchOperator\": {\"@class\": \"JSONMockOperator\"}, \"toStation\": 125}"}
                        // Add new test values here
                }
        );
    }

    @Test
    public void testDeserialize() throws Exception {
        JSONAlgebraElement deserialized = OBJECT_MAPPER.readValue(jsonValue, expected.getClass());

        assertEquals(
                "Deserialization of " + expected.getClass().getSimpleName() + " object incorrect.",
                expected,
                deserialized
        );
    }

    @Test
    public void testDeserializeAsGenericType() throws Exception {
        JSONAlgebraElement deserialized = OBJECT_MAPPER.readValue(jsonValue, JSONAlgebraElement.class);

        assertEquals(
                "Deserialization of " + expected.getClass().getSimpleName() + " as generic type JSONOperator failed.",
                expected,
                deserialized
        );
    }
}
