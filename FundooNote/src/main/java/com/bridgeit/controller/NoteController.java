package com.bridgeit.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bridgeit.businessservices.NoteBusinessService;

import com.bridgeit.json.Response;
import com.bridgeit.model.Collaborator;
import com.bridgeit.model.Note;
import com.bridgeit.model.Register;
import com.bridgeit.utilityservices.ElasticSearch;
import com.bridgeit.utilityservices.JMSProducerElasticSearch;
import com.bridgeit.utilityservices.NoteService;
import com.bridgeit.utilityservices.UserService;

@Controller
@RequestMapping("auth/")
public class NoteController {

	private static final Logger logger = Logger.getLogger(NoteController.class);
	@Autowired
	NoteService noteService;

	@Autowired
	UserService userService;
	
	@Autowired 
	NoteBusinessService noteBusinessService;
	@Autowired
	ElasticSearch elasticSearch;
	
	@Autowired
	JMSProducerElasticSearch jmsElasticSearch;

	Response resp = new Response();

	@RequestMapping(value = "insertNote", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Response> insertNote(@RequestBody Note note1) {
		logger.info("Before Inserting Note");
		logger.info(note1);
		noteService.insertNote(note1);
		logger.info("After Insertion of the Note");
		return new ResponseEntity<Response>(resp, HttpStatus.OK);

	}

	@RequestMapping(value = "updateNote", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Response> updateNote(@RequestBody Note note2) {
		logger.info("Before Update Note");
		noteService.updateNote(note2);
		logger.info("After Update Note");
		return new ResponseEntity<Response>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "deleteNote", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Response> deleteNote(@RequestBody Note note3) {
		
		logger.info("Before Delete Note");
		noteService.deleteNode(note3);
		logger.info("After deleting note");
		logger.info("Deleting in Elastic Search");
		elasticSearch.deleteElasticNotes(note3.getNotes_id());
		logger.info("Deleted from Elastic Search");
		return new ResponseEntity<Response>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "getNotebyId", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Response> getNotebyId(@RequestBody Note note4) {
		logger.info("Before getNotebyId");

		logger.info(note4);
		note4 = noteService.getNotebyId(note4);
		logger.info(note4);
		logger.info("After getNotebyId");
		resp.setMessage(note4.toString());
		return new ResponseEntity<Response>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "indexAllNotes", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Response> indexNotes(@RequestBody Note note5) {
		logger.info("Before selectAllNotes");

		//List<Note> notes = service.selectAllNotes(note5);
		List<Note> notes = noteService.selectAllFundooNotes();
		logger.info("After selectAllNotes");
		logger.info(notes);
/*		List<Note> notes = service.selectAllNotes(note5);
		logger.info(notes);*/
		jmsElasticSearch.sendNotesToJMS();
		/*elasticSearch.indexAllNotes(notes);*/
		return new ResponseEntity<Response>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "selectAllFundooNotes", method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public ResponseEntity<Response> selectAllFundooNotes(@RequestBody Note note5) {

	
		logger.info("Fetching All Notes");
		List<Note> notes=noteService.selectAllFundooNotes();
		logger.info(notes);
		/*elasticSearch.indexAllNotes(notes);*/
		/*elasticSearch.searchElasticNotes(searchString, userid);*/
		logger.info("After Search Elastic All Notes");
		return new ResponseEntity<Response>(resp, HttpStatus.OK);
		
	}

	
	@RequestMapping(value = "searchAllNotesElastic", method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public ResponseEntity<Response> selectAllNotesElastic(@RequestBody Map<String, Object> searchMap) {

		int userid = (Integer) searchMap.get("userid");
		String searchString = (String) searchMap.get("searchString");
	
		logger.info("Searching Elastic searcAllNotes");
		logger.info(searchString);
		elasticSearch.searchElasticNotes(searchString, userid);
		logger.info("After Search Elastic All Notes");
		
		
		return new ResponseEntity<Response>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "archiveNote", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Response> archiveNote(@RequestBody Note note6) {
		logger.info("Archiving the selected user node");
		noteService.archiveNote(note6);
		logger.info("After archiving the node");
		resp.setStatus(note6.getUser().getUser_id());
		resp.setMessage("Archived");
		return new ResponseEntity<Response>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "trashNote", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Response> trashNote(@RequestBody Note note7) {
		logger.info("Trashing the selected user node");
		noteService.trashNote(note7);
		logger.info("After trashing the node");
		resp.setStatus(note7.getUser().getUser_id());
		resp.setMessage("Archived");
		return new ResponseEntity<Response>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "deleteFromTrash", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Response> deleteFromTrash(@RequestBody Note note) {
		
		Response resp=noteBusinessService.trashService(note);
		logger.info(resp);
		return new ResponseEntity<Response>(resp, HttpStatus.OK);
	}

	
	@RequestMapping(value = "setRemainder", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Response> setRemainder(@RequestBody Note note9) {
		logger.info("Setting Remainder");
		logger.info(note9);
		noteService.setRemainder(note9);
		logger.info("Remainder Set");
		resp.setStatus(100);
		resp.setMessage("Remainder for the user " + note9.getUser().getUser_id() + " and note number of a "
				+ note9.getNotes_id() + " has been set succesfully to the note by the user");
		return new ResponseEntity<Response>(resp, HttpStatus.OK);
	}
		
	@RequestMapping(value="collaborate",method =RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Response>setCollaborate(/*@RequestBody Map<String,String> notemap ,*/@RequestBody Collaborator collaborator)
	{
		List<Register> userList=new ArrayList<Register>();
		int collab_id,notes_id;
		boolean userValue;
		
		logger.info("Inside Colloboration");
		


	
		
		Register reg=userService.checkUserByEmail(collaborator.getCollaboratorEmail());
		logger.info(reg);
		collab_id=reg.getUser_id();
		notes_id=collaborator.getNote().getNotes_id();
		
		userList=noteService.selectColabNotes(collaborator.getUser().getUser_id());
		userValue=userList.contains(notes_id);
		logger.info(userValue);
		
		if(!userValue)
		{
		collaborator.setCollaborated_Id(reg.getUser_id());
		logger.info("After Insertion into the database");
		resp.setStatus(1000);
		resp.setMessage("Collaboration Done");
		}
		else
		{
			logger.info("Already Collaborated");
		}
		
		return new ResponseEntity<Response>(resp,HttpStatus.OK);
	}
	
	
}
