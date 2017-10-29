package com.bridgeit.Controller;

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

import com.bridgeit.ElasticSearch.ElasticSearch;
import com.bridgeit.JSON.Response;
import com.bridgeit.Model.Note;
import com.bridgeit.Model.Register;
import com.bridgeit.Services.NoteService;

@Controller
@RequestMapping("auth/")
public class NoteController {

	private static final Logger logger = Logger.getLogger(NoteController.class);
	@Autowired
	NoteService service;

	@Autowired
	ElasticSearch elasticsearch;

	Response resp = new Response();

	@RequestMapping(value = "insertNote", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Response> insertNote(@RequestBody Note note1) {
		logger.info("Before Inserting Note");
		Note note = new Note();
		Register user = new Register();
		user.setUser_id(note1.getUser().getUser_id());
		note.setTitle("BatMan");
		note.setDescription("To breathe and is purest");
		note.setLastaccessdate(new Date());
		note.setUser(note1.getUser());

		/*
		 * logger.info(note1); service.insertNote(note1);
		 */
		logger.info(note);
		service.insertNote(note);
		logger.info("After Insertion of the Note");
		return new ResponseEntity<Response>(resp, HttpStatus.OK);

	}

	@RequestMapping(value = "updateNote", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Response> updateNote(@RequestBody Note note2) {
		logger.info("Before Update Note");
		/*
		 * Note note=new Note(); Register user=new Register(); user.setUser_id(2);
		 */
		/*
		 * note2.setTitle("Goblet of Fire");
		 * note2.setDescription("One of HarryPotter's Series"); note2.setNotes_id(17);
		 */
		// note.setUser(user);

		service.updateNote(note2);
		logger.info("After Update Note");
		return new ResponseEntity<Response>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "deleteNote", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Response> deleteNote(@RequestBody Note note3) {
		logger.info("Before Delete Note");
		/*
		 * Note note=new Note(); note.setNotes_id(7); Register user=new Register();
		 * user.setUser_id(2); note.setUser(user);
		 */

		service.deleteNode(note3);
		logger.info("After deleting note");
		return new ResponseEntity<Response>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "getNotebyId", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Response> getNotebyId(@RequestBody Note note4) {
		logger.info("Before getNotebyId");
		/*
		 * Note note=new Note(); note.setNotes_id(8);
		 */

		logger.info("sfasfasfsadsadasdsadasdsa");
		logger.info(note4);
		note4 = service.getNotebyId(note4);
		logger.info(note4);
		logger.info("After getNotebyId");
		resp.setMessage(note4.toString());
		return new ResponseEntity<Response>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "indexAllNotes", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Response> indexNotes(@RequestBody Note note5) {
		logger.info("Before selectAllNotes");

		List<Note> notes = service.selectAllNotes(note5);
		logger.info("After selectAllNotes");
		logger.info(notes);
		elasticsearch.indexAllNotes(notes);
		return new ResponseEntity<Response>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "serchAllNotesElastic", method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public ResponseEntity<Response> selectAllNotesElastic(@RequestBody Map<String, Object> searchMap) {

		int userid = (Integer) searchMap.get("userid");
		String searchString = (String) searchMap.get("searchString");
	
		logger.info("Searching Elastic searcAllNotes");
		logger.info(searchString);
		elasticsearch.searchElasticNotes(searchString, userid);
		logger.info("After Search Elastic All Notes");
		return new ResponseEntity<Response>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "archiveNote", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Response> archiveNote(@RequestBody Note note6) {
		logger.info("Archiving the selected user node");
		service.archiveNote(note6);
		logger.info("After archiving the node");
		resp.setStatus(note6.getUser().getUser_id());
		resp.setMessage("Archived");
		return new ResponseEntity<Response>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "trashNote", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Response> trashNote(@RequestBody Note note7) {
		logger.info("Trashing the selected user node");
		service.trashNote(note7);
		logger.info("After trashing the node");
		resp.setStatus(note7.getUser().getUser_id());
		resp.setMessage("Archived");
		return new ResponseEntity<Response>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "deleteFromTrash", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Response> deleteFromTrash(@RequestBody Note note) {

		logger.info("Deleting Permanently the selected user node");
		logger.info(note.isTrash() && note.isDeletefromtrash());
		if (note.isTrash() && note.isDeletefromtrash()) {
			service.deleteNode(note);
			logger.info("Deleted the node permanently");
			resp.setStatus(note.getUser().getUser_id());
			resp.setMessage("Node deleted from trash");
		} else {
			logger.info("Not deleted");
		}
		return new ResponseEntity<Response>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "setRemainder", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Response> setRemainder(@RequestBody Note note9) {
		logger.info("Setting Remainder");
		logger.info(note9);
		service.setRemainder(note9);
		logger.info("Remainder Set");
		resp.setStatus(100);
		resp.setMessage("Remainder for the user " + note9.getUser().getUser_id() + " and note number of a "
				+ note9.getNotes_id() + " has been set succesfully to the note by the user");
		return new ResponseEntity<Response>(resp, HttpStatus.OK);
	}

}
