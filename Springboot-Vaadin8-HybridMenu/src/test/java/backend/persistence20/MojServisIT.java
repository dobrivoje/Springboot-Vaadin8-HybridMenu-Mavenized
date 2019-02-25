package backend.persistence20;

import backend.persistence20.disk.CompactDisc;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/applicationContext.xml")
@ActiveProfiles("dev")
public class MojServisIT {

    @Autowired
    private MojServis servis;

//    @Autowired
//    private CompactDisc cdServis;

    @Autowired
    private ApplicationContext context;

    @Before
    public void setUp() {
    }

    // @Test
    public void testGetIme() {
        System.err.println("-----------------------");
        CompactDisc cServis = (CompactDisc) context.getBean("cdServis");
        cServis.play();
        System.err.println("-----------------------");

//        cdServis.play();

        System.err.println(Arrays.toString(context.getEnvironment().getActiveProfiles()));

        String algGenerated = servis.getHello();
        assertEquals(algGenerated, "nina prtenjak");
    }
    
    @Test
    public void testKonstruktor() {
        System.err.println("-----------------------");
        MojServis cServis = (MojServis) context.getBean("s1");
        System.err.println(cServis.getHello());
        System.err.println("-----------------------");

        System.err.println(Arrays.toString(context.getEnvironment().getActiveProfiles()));

        String algGenerated = servis.getHello();
        assertEquals(algGenerated, "nina prtenjak");
    }
}
