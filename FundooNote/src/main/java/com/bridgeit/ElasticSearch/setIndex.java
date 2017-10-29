package com.bridgeit.ElasticSearch;

import java.io.IOException;
import java.net.InetAddress;
import static org.elasticsearch.common.xcontent.XContentFactory.*;
import java.net.UnknownHostException;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import com.bridgeit.Model.Note;
import com.bridgeit.Model.Register;

public class setIndex {

	public static void main(String[] args) throws IOException {

		try {
			Note note = new Note();
			Register user = new Register();

			note.setTitle("My Note");
			note.setNotes_id(56);
			note.setDescription("This is a Note");
			user.setName("bms");
			user.setEmail("bms@email.com");
			user.setUser_id(233);
			note.setUser(user);

			TransportClient client = new PreBuiltTransportClient(Settings.EMPTY)
					.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));

			XContentBuilder builder = jsonBuilder().startObject().field("userid", note.getUser().getUser_id())
					.field("title", note.getTitle()).field("description", note.getDescription()).endObject();
			String json = builder.string();
			IndexResponse response = client.prepareIndex("test", "type").setSource(json).execute().actionGet();
		} finally {

			System.out.println("Done...");
		}

	}
}
