package com.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class JMSReceiverAsync implements MessageListener{

	@Override
	public void onMessage(Message message) {
        TextMessage textMsg = (TextMessage)message;  
        try {
			textMsg.acknowledge();
			 System.out.println("-------------------:"+textMsg.getText());  
		} catch (JMSException e) {
			e.printStackTrace();
		} 
	}

}
