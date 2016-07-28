package it.ugolandini.drools;

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

    public void bootstrapDrools() {
        // The KieSession was injected so we can use it now
        kSession.insert("Hi There!");
        int rulesFired = kSession.fireAllRules();
        System.out.println("Rules Fired: " + rulesFired);
    }

    public static void main(String[] args) {

        Weld w = new Weld();

        WeldContainer wc = w.initialize();
        App app = wc.instance().select(App.class).get();
        app.bootstrapDrools();

        w.shutdown();
    }
}