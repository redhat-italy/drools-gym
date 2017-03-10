package it.ugolandini.drools;

import it.ugolandini.drools.model.BitmaskExample;
import it.ugolandini.drools.model.Customer;
import it.ugolandini.drools.model.Event;
import org.drools.core.util.BitMaskUtil;
import org.drools.core.util.bitmask.BitMask;
import org.drools.core.util.bitmask.OpenBitSet;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kie.api.cdi.KReleaseId;
import org.kie.api.cdi.KSession;
import org.kie.api.runtime.KieSession;

import javax.inject.Inject;

import static org.jboss.shrinkwrap.api.ShrinkWrap.create;


@RunWith(Arquillian.class)
public class RulesTest {

    public RulesTest() {
    }

    @Deployment
    public static JavaArchive createDeployment() {

        return create(JavaArchive.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Inject
    @KSession
    @KReleaseId(groupId = "it.ugolandini.drools", artifactId = "drools-gym-kjar", version = "1.0-SNAPSHOT")

    private KieSession kSession;

    @Test
    public void simpleRules() {
        Assert.assertNotNull(kSession);
        Customer ugo = new Customer(1, "ugo", 46);
        Customer andrea = new Customer(2, "andrea", 54);

        kSession.insert(ugo);
        kSession.insert(andrea);

        Assert.assertEquals(Customer.Category.NA, andrea.getCategory());
        Assert.assertEquals(Customer.Category.NA, ugo.getCategory());

        int rulesfired = kSession.fireAllRules();

        Assert.assertEquals(Customer.Category.SILVER, andrea.getCategory());
        Assert.assertEquals(Customer.Category.PLATINUM, ugo.getCategory());
        Assert.assertEquals(2, rulesfired);
    }

    @Test
    public void bitSetRules() {
        Assert.assertNotNull(kSession);
        BitmaskExample b0 = new BitmaskExample(15);
        BitmaskExample b04 = new BitmaskExample(25);
        BitmaskExample b046 = new BitmaskExample(35);

        kSession.insert(b0);
        kSession.insert(b04);
        kSession.insert(b046);

        BitMask mask046 = new OpenBitSet(8);
        mask046.set(Event.EVENT0.ordinal()).set(Event.EVENT4.ordinal()).set(Event.EVENT6.ordinal());

        int rulesfired = kSession.fireAllRules();
        Assert.assertEquals(6, rulesfired);

        Assert.assertTrue(b0.getBitMask().isSet(Event.EVENT0.ordinal()));
        Assert.assertFalse(b0.getBitMask().isSet(Event.EVENT4.ordinal()));

        Assert.assertTrue(b04.isEventFired(Event.EVENT0));
        Assert.assertFalse(b04.isEventFired(Event.EVENT1));
        Assert.assertFalse(b04.isEventFired(Event.EVENT2));
        Assert.assertFalse(b04.isEventFired(Event.EVENT3));
        Assert.assertTrue(b04.isEventFired(Event.EVENT4));

        Assert.assertEquals(mask046, b046.getBitMask());
    }


}
