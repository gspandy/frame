package mb.mba.drools;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

/**
 * 创建KieSession
 * 
 * @author cyyu
 *
 */
public  class KieSessionFactory {

	private static KieContainer KCONTAINER = null;
	
	private static KieContainer getKieContainer(){
		if (KCONTAINER == null) {
			KieServices KS = KieServices.Factory.get();
			KCONTAINER = KS.getKieClasspathContainer();
		}
		return KCONTAINER;
	}
	
	public static KieSession getKieSession(String rules){
		KieSession session = getKieContainer().newKieSession(rules);
		return session;
	}
	
}
