package it.ugolandini.drools;

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
    public void hello() {
        Assert.assertNotNull(kSession);
        kSession.insert("Hi There From Test!");
        Assert.assertEquals(1, kSession.fireAllRules());
    }
}
