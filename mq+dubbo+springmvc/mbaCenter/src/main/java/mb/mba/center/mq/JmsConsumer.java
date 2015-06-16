package mb.mba.center.mq;

public class JmsConsumer {

	public void onMessageForTest(String message){
		System.out.println(">>>>>>>>>>>>>>>>>"+message);
	}
}
