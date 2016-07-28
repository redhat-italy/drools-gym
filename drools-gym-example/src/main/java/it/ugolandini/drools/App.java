package it.ugolandini.drools;

import it.ugolandini.drools.model.Customer;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.kie.api.cdi.KReleaseId;
import org.kie.api.cdi.KSession;
import org.kie.api.runtime.KieSession;

import javax.inject.Inject;

public class App {

    @Inject
    @KSession()
    @KReleaseId(groupId = "it.ugolandini.drools", artifactId = "drools-gym-kjar", version = "1.0-SNAPSHOT")

    private KieSession kSession;

    public void runRules() {
        Customer ugo = new Customer(1, "ugo", 46);
        Customer andrea = new Customer(2, "andrea", 54);

        kSession.insert(ugo);
        kSession.insert(andrea);
        System.out.println("Rules Fired: " + kSession.fireAllRules());
    }

    public static void main(String[] args) {

        Weld w = new Weld();

        WeldContainer wc = w.initialize();
        App app = wc.instance().select(App.class).get();
        app.runRules();

        w.shutdown();
    }
}