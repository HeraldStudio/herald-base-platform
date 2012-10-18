package cn.edu.seu.herald.session.core;

import cn.edu.seu.herald.session.Session;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
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
    
    public void testMemcached() {
        ApplicationContext context =
                new ClassPathXmlApplicationContext(
                "classpath:/cn/edu/seu/herald/session/herald-session-cache.xml");
        MemcachedClient memcachedClient =
                (MemcachedClient) context.getBean("memcachedClient");
    }
    
    private static class Foobar {
        @Override
        public String toString() {
            return "I'm foobar";
        }
    }

}
