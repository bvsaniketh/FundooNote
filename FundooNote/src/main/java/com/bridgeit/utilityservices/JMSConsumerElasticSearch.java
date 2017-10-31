package com.bridgeit.utilityservices;

import java.lang.reflect.Type;
import java.util.List;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.bridgeit.model.Note;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class JMSConsumerElasticSearch {
	
	Connection connection = null;
	Session session = null;
	
	Logger logger=Logger.getLogger(JMSConsumerElasticSearch.class);
	String notesDetails;
	Gson gson=new Gson();
	
	@Autowired
	ElasticSearch elasticSearch;
	
	public void init()
	
	{
		try {
		logger.info("Inside init method of JMSConsumerElasticSearch");
	
			this.recieveNotesFromElastic();
		} catch (JMSException e) {
			
			e.printStackTrace();
		}
		
	}
	
	public void recieveNotesFromElastic() throws JMSException
	{
		
		logger.info("Inside JMS Consumer Elastic Search");
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
		connection = connectionFactory.createConnection();
		connection.start();
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		Queue queue = session.createQueue("ElasticSearch");
		MessageConsumer consumer = session.createConsumer(queue);
		
		consumer.setMessageListener(new MessageListener() {
			
			@Override
			public void onMessage(Message msg) {
				
				logger.info("Insider onMessage of JMSConsumerELasticSearch Class");
				if(!(msg instanceof TextMessage))
					throw new RuntimeException("No Text Message Available");
				
				TextMessage jmsmessage=(TextMessage) msg;
				try {
					notesDetails=jmsmessage.getText();
					logger.info(jmsmessage.getText());
					
					Type listType=new TypeToken<List<Note>>() {}.getType();
					List<Note>notes=gson.fromJson(notesDetails,listType);
					
					logger.info("Data Available from Queue in Consumer "+ notes);
					elasticSearch.indexAllNotes(notes);		
					
				} catch (JMSException e) {
					
					e.printStackTrace();
				}
				
			}
		});
		MessageListener msgListener=consumer.getMessageListener();
		logger.info("Message Listener " + msgListener);
	}
	
	public void stop()
	{
		logger.info("In stop block");
		if(session!=null)
		{
			try {
				session.close();
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
		
		if(connection!=null)
		{
			try {
				connection.close();
			} catch (JMSException e) {
				
				e.printStackTrace();
			}
		}
		
	}
/*	
	public void finalize() throws Throwable
	{
		this.stop();	
	}
	
	*/
}
