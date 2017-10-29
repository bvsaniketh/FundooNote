package com.bridgeit.ElasticSearch;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import java.util.List;
import org.apache.log4j.Logger;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
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
	int i = 0;

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

	public void indexAllNotes(List<Note> notes) {
		System.out.println("Indexing..");
		try {
			client = new PreBuiltTransportClient(Settings.EMPTY)
					.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));
		} catch (UnknownHostException e) {

			e.printStackTrace();
		}

		for (Note note : notes) {
			logger.info(note);

			XContentBuilder builder;
			try {

				builder = jsonBuilder().startObject().field("userid", note.getUser().getUser_id())
						.field("title", note.getTitle()).field("description", note.getDescription()).endObject();
				String json = builder.string();
				client.prepareIndex("fundoo", "notes").setSource(json).execute().actionGet();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		logger.info("In ElasticSearch" + notes);
		logger.info("In ElasticSearch Getting notes ");
	}

	public void searchElasticNotes(String searchString, int userid) {
		TransportClient client = null;
		System.out.println("UID..."+userid);
		try {

			client = new PreBuiltTransportClient(Settings.EMPTY)
					.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));

			SearchResponse response = client.prepareSearch("fundoo").setTypes("notes")
					.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
					.setQuery(QueryBuilders.matchQuery("userid", userid))
					/*.setQuery(QueryBuilders.matchQuery("description", searchString))*/
					.setQuery(QueryBuilders.matchQuery("title", searchString))
					.get();

			System.out.println(response.toString());
		} catch (UnknownHostException e) {

			e.printStackTrace();
		}

		logger.info("Search UnderProcess");
		
		/*
		logger.info(searchString);
		response = client.prepareSearch("fundoonote").setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
				.setQuery(QueryBuilders.matchQuery("content", searchString)).get();
		logger.info(response.toString());
		*/
		logger.info("Search Performed");
	}
}
