package com.bridgeit.JMS;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import com.bridgeit.Services.Email;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Service
public class SpringJmsConsumer {

	@Autowired
	Email email;
	
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
		/*String userjson=textmessage.getText();*/
		JsonObject json= (JsonObject) new JsonParser().parse(textmessage.getText());
		String user_email=json.get("email").getAsString();
		System.out.println(user_email +"This is the user's email for sending mail");
		return textmessage.getText();
	}
}
