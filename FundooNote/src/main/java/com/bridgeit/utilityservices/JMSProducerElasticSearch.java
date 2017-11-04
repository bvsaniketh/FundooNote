package com.bridgeit.utilityservices;

import java.util.List;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.bridgeit.model.Note;
import com.google.gson.Gson;

@Service
public class JMSProducerElasticSearch {
	
	@Autowired
	NoteService noteservice;
	List<Note> notes;
	
	Logger logger=Logger.getLogger(JMSProducerElasticSearch.class);
	
	/*@Scheduled(fixedDelay=1000000)*/
	public void sendNotesToJMS()
	
	{
		Connection connection=null;
		ConnectionFactory connectionFactory=new ActiveMQConnectionFactory("tcp://localhost:61616");
		
		try {
			
			logger.info("In JMS Producer Elastic Search");
			connection=connectionFactory.createConnection();
			Session session=connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Queue queue=session.createQueue("ElasticSearch");
			MessageProducer producer=session.createProducer(queue);
			
			notes=noteservice.selectAllFundooNotes();
			Gson gson = new Gson();
			String userjson = gson.toJson(notes);
			Message message=session.createTextMessage(userjson);
			
			logger.info("These notes are from JMSProducer" +" " + notes);
			producer.send(message);
			session.close();
		} catch (JMSException e) {
			e.printStackTrace();
		}
		
		finally
		{
			if(connection!=null)
			{
				try {
					connection.close();
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
