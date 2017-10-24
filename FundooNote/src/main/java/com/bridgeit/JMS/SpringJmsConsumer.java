package com.bridgeit.JMS;
 

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

import org.springframework.jms.core.JmsTemplate;

public class SpringJmsConsumer {

	private Destination destination;
	private JmsTemplate jmsTemplate;
	
	public Destination getDestination() {
		return destination;
	}
	public void setDestination(Destination destination) {
		this.destination = destination;
	}
	public JmsTemplate getJmsTemplate() {
		return jmsTemplate;
	}
	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}
	
	public String receiveMessage() throws JMSException
	{
		TextMessage textmessage=(TextMessage) jmsTemplate.receive(destination);
		return textmessage.getText();
	}
}
