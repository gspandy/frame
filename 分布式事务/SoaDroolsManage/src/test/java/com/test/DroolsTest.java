package com.test;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;

/**
 * This is a sample class to launch a rule.
 */
public class DroolsTest {

    public static  void main(String[] args) {
        try {
            // load up the knowledge base
        	KieServices ks = KieServices.Factory.get();
	        KieServices.Factory.get().getRepository();
    	    KieContainer kContainer = ks.getKieClasspathContainer();
//    	    KieSession kSession = kContainer.newKieSession("tbn-rules");
//        	
//            // go !
//            Message message = new Message();
//            message.setMessage("this is what i belive");
//            message.setStatus(Message.HELLO);
//            kSession.insert(message);
//            kSession.fireAllRules();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }


}
