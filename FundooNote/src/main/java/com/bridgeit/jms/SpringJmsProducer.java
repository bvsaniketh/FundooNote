package com.bridgeit.jms;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.log4j.Logger;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.bridgeit.controller.GoogleLoginController;

@Service
public class SpringJmsProducer {

	private Destination destination;
	private JmsTemplate jmsTemplate;
	
	private static Logger logger=Logger.getLogger(SpringJmsProducer.class);
	
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
	
	public void sendMessage(final String msg)
	
	{
		logger.info("Producer is sending a message");
		jmsTemplate.send(destination, new MessageCreator() {
			
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage(msg);
			}
		});
	}
	
}
