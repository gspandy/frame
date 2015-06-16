package mb.erp.dr.soa.drools.utils;

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

	//KieContainer是一个KieBase的容器,KieBase的创建成本高，所以只创建一次
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
