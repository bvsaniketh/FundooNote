package com.bridgeit.utilityservices;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgeit.model.Note;
import com.bridgeit.model.NoteTest;
import com.bridgeit.utilityservices.NoteService;
import com.google.gson.Gson;

import static org.elasticsearch.common.xcontent.XContentFactory.*;

@Service
public class ElasticSearch {

	static NoteTest test = new NoteTest(1, "Anibo", "Fire and Wings in Mumbai");

	static Logger logger = Logger.getLogger(ElasticSearch.class);
	SearchResponse response;
	TransportClient client;
	int i = 0;
	Note note;
	List<Note> notesSearch = new ArrayList<Note>();

	@Autowired
	NoteService noteservice;

	public void indexAllNotes(List<Note> notes) {
		System.out.println("Indexing..");
		System.out.println(notes);
		logger.info(notes);
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

				builder = jsonBuilder()
						.startObject()
						.field("userid", note.getUser().getUser_id())
						.field("title", note.getTitle()).field("description", note.getDescription())
						.field("notes_id", note.getNotes_id()).endObject();
				String json = builder.string();
				client.prepareIndex("fundoo", "notes").setId(String.valueOf(note.getNotes_id())).setSource(json)
						.execute().actionGet();
				
				
				logger.info("In ElasticSearch Getting notes ");
				
			} catch (IOException e) {

				e.printStackTrace();
			}
			catch (NullPointerException e)
			{
				e.printStackTrace();
			}
/*			
			finally
			{
				if(client!=null)
				{
					client.close();
				}
			}*/

		}


	}

	public void searchElasticNotes(String searchString, int userid) {
		TransportClient client = null;
		System.out.println("UID..." + userid);
		try {

			client = new PreBuiltTransportClient(Settings.EMPTY)
					.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));

			QueryBuilder queryBuilder = QueryBuilders.boolQuery()
										.must(QueryBuilders.matchQuery("userid", userid))
										.must(QueryBuilders.boolQuery()
										.should(QueryBuilders
										.matchQuery("description", searchString))
										.should(QueryBuilders.matchQuery("title", searchString)));

			SearchResponse response = client.prepareSearch("fundoo").setTypes("notes")
					.setSearchType(SearchType.DFS_QUERY_THEN_FETCH).setQuery(queryBuilder).get();

			SearchHits hits = response.getHits();

			SearchHit[] hitArray = hits.getHits();

			
			for (SearchHit hit : hitArray) {

				String jsonContent = hit.getSourceAsString();
				System.out.println("Content1: " + jsonContent + "\n");
				note = new Gson().fromJson(jsonContent, Note.class);
				notesSearch.add(note);

			}

			logger.info(notesSearch);
			
			logger.info("Search UnderProcess");

			logger.info("Search Performed");
			
			List<Note> notes=noteservice.selectAllFundooNotes();
			logger.info(notes);
			
		} catch (UnknownHostException e) {

			e.printStackTrace();
		}


		
		finally
		{
			if(client!=null)
			{
				client.close();
			}
		}
	}
	
	
	public void deleteElasticNotes(int notesid)
	{
		try {
			
			client = new PreBuiltTransportClient(Settings.EMPTY)
					.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));
			
			DeleteResponse response=client.prepareDelete("fundoo","notes",String.valueOf(notesid)).get();
			logger.info(response);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		finally
		{
			if(client!=null)
			{
				client.close();
			}
		}
	}

}
