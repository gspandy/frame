package mb.mba.test;

import mb.mba.core.bean.Message;
import mb.mba.drools.KieSessionFactory;

import org.kie.api.runtime.KieSession;

public class DroolsTest {

	public static void main(String[] args) {
		Message msg = new Message();
		msg.setCode("ZZZZ");
		KieSession kieSession = KieSessionFactory.getKieSession("test-rules");
		kieSession.insert(msg);
		kieSession.fireAllRules();
		kieSession.dispose();
		System.out.println(msg.getCode());
	}

}
