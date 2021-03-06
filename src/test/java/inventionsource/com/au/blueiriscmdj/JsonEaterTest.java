package inventionsource.com.au.blueiriscmdj;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class JsonEaterTest
{
    private static final Logger log = (Logger) LogManager.getLogger(JsonEaterTest.class.getName());

    private String _jsonResultStr = "{'session': '5b29593655f0117b5e36214f072b3e9f', " +
            "'data': {'dio': False, 'schedules': ['Default'], " +
            "'audio': True, 'profiles': ['Inactive', 'AtHome', 'OnTheRoad', " +
            "'Profile 3', 'Profile 4', 'Profile 5', " +
            " 'Profile 6', 'Profile 7'], 'user': 'admin'}, 'result': 'success'}";

    @Before
    public void setUp() throws Exception {
        Log4j2Config log4j = new Log4j2Config("test.log","debug");
        new Constants4Tests();
    }

    @Test
    public void GetResultElementTest() throws Exception {
        String expectedsession ="5b29593655f0117b5e36214f072b3e9f";
        try {
            boolean hasToBeSuccess= true;
            assertTrue("CheckResultSuccess ",
                    JsonEater.CheckResultSuccess(_jsonResultStr,hasToBeSuccess));

            JsonObject jsonObject = JsonEater.GetJsonObject(_jsonResultStr);

            assertNotNull("assertNotNull jsonObject ", jsonObject);
            String session = jsonObject.get("session").getAsString();
            assertNotNull("assertNotNull session ", session);
            assertTrue("session = expectedsession ", session.compareTo(expectedsession) == 0);
        } catch (Exception e) {
            log.error("Error: " + e);
            throw e;
        }
    }
    @Test
    public void GetDataElementTest()  throws Exception {
        try {
            boolean hasToBeSuccess= true;
            assertTrue("CheckResultSuccess ",
                    JsonEater.CheckResultSuccess(_jsonResultStr,hasToBeSuccess));

            JsonElement dataElement = (new JsonEater()).GetDataElement(_jsonResultStr);
            assertNotNull("assertNotNull jsonElement ", dataElement);
            boolean audio = dataElement.getAsJsonObject().get("audio").getAsBoolean();
            assertNotNull("assertNotNull session ", audio);
            assertTrue("audio = true ", audio);
        } catch (Exception e) {
            log.error("Error: " + e);
            throw e;
        }
    }
}
