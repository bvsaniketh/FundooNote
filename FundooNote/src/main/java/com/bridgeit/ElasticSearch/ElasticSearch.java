package com.bridgeit.ElasticSearch;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.List;import org.apache.activemq.broker.region.policy.QueryBasedSubscriptionRecoveryPolicy;
import org.apache.log4j.Logger;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.stereotype.Service;

import static org.elasticsearch.common.xcontent.XContentFactory.*;

import com.bridgeit.Model.Note;
import com.bridgeit.Model.NoteTest;

@Service
public class ElasticSearch {

	static NoteTest test = new NoteTest(1, "Anibo", "Fire and Wings in Mumbai");
	static NoteTest test1 = new NoteTest(2, "Bharabo", "Water and Swims in Chennai");
	static NoteTest test2 = new NoteTest(3, "Vijaybo", "Thala and King in Thirunelveli");
	static Logger logger = Logger.getLogger(ElasticSearch.class);
	SearchResponse response;
	TransportClient client;
	int i=0;
	

	public static void main(String args[]) throws IOException {
		TransportClient client = null;
		client = new PreBuiltTransportClient(Settings.EMPTY)
				.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));
		logger.info("Connected to LocalHost");

		/*
		 * client.prepareIndex("note", "user",
		 * "1").setSource("value",test).execute().actionGet();
		 * client.prepareIndex("note","user","2").setSource("value",test1).execute().
		 * actionGet();
		 * client.prepareIndex("note","user","3").setSource("value",test2).execute().
		 * actionGet();
		 */
		logger.info("Added");

		/*
		 * IndexResponse response=client.prepareIndex("note", "user", "3")
		 * .setSource(jsonBuilder().startObject() .field("id","3")
		 * .field("title","ThalaVijay")
		 * .field("description","Next Level Kid The King is!!") .endObject()).get();
		 */
		SearchResponse response = client.prepareSearch("note").setTypes("user")
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH).setQuery(QueryBuilders.matchQuery("value", "Bharabo"))
				.get();

		logger.info(response.toString());

	}

	public void getAllNotes(List<Note> notes) {
		
		try {
			 client = new PreBuiltTransportClient(Settings.EMPTY)
					.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));
		} catch (UnknownHostException e) {

			e.printStackTrace();
		}
		
		
		
		for(Note notesitr:notes)
		{
			logger.info(notesitr);
			client.prepareIndex("fundoonote", "fundoouser").setSource("content",notesitr).execute().actionGet();
			
		}
		/*Iterator<Note> itr = notes.iterator();
		while (itr.hasNext()) {
			
			client.prepareIndex("fundoonote", "fundoouser",).		
		}*/
		
		
		logger.info("In ElasticSearch" + notes);
		logger.info("In ElasticSearch Getting notes ");
	}
	
	public void searchElasticNotes(String searchString)
	{
		logger.info("Search UnderProcess");
		logger.info(searchString);
		response=client.prepareSearch("fundoonote").setTypes("fundoouser")
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
				.setQuery(QueryBuilders.matchQuery("content", searchString))
				.get();
		logger.info(response.toString());
		logger.info("Search Performed");
	}
}
