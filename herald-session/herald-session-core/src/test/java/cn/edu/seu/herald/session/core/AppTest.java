package cn.edu.seu.herald.session.core;

import cn.edu.seu.herald.session.Session;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName ) {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite( AppTest.class );
    }

    public void testJaxb() {
        try {
            String uuid = "550e8400-e29b-41d4-a716-446655440000";
            long currentTime = System.currentTimeMillis();
            Session session = new Session(uuid, currentTime);
            Session innerSession = new Session("nil", 0);
            session.setAttribute("subsession", innerSession);
            session.setAttribute("foobar", new Foobar());

            JAXBContext jc = JAXBContext.newInstance(Session.class);
            Marshaller marshaller = jc.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(session, System.out);
        } catch (JAXBException ex) {
            ex.printStackTrace();
            TestCase.fail();
        }
    }

    public void testCache() {
        try {
            ApplicationContext context = new ClassPathXmlApplicationContext(
                    "classpath*:/cn/edu/seu/herald/session/"
                    + "herald-session-cache.xml");
            boolean isTypeMatch = context.isTypeMatch("sessionCacheAccess",
                    SessionCacheAccess.class);
            TestCase.assertTrue(isTypeMatch);
            SessionCacheAccess sessionCacheAccess = (SessionCacheAccess) context
                    .getBean("sessionCacheAccess");

            // store
            String uuid = "550e8400-e29b-41d4-a716-446655440000";
            long currentTime = System.currentTimeMillis();
            Session newSession = new Session(uuid, currentTime);
            newSession.setUri("uri");
            newSession.setAttribute("foo", "bar");
            sessionCacheAccess.storeSession(newSession);

            // retrieve
            Session cachedSession = sessionCacheAccess.getSessionById(uuid);
            TestCase.assertEquals(cachedSession.getId(), uuid);
            TestCase.assertEquals(cachedSession.getCreationTime(), currentTime);
            TestCase.assertEquals(cachedSession.getUri(), "uri");
            TestCase.assertEquals(cachedSession.getAttribute("foo"), "bar");

            // update
            cachedSession.setAttribute("foo", "another");
            sessionCacheAccess.updateSession(cachedSession);
            Session updatedSession = sessionCacheAccess.getSessionById(uuid);
            TestCase.assertEquals(updatedSession.getAttribute("foo"),
                    "another");
        } catch (Exception ex) {
            ex.printStackTrace();
            TestCase.fail();
        }
    }

    private static class Foobar {
        @Override
        public String toString() {
            return "I'm foobar";
        }
    }

}
