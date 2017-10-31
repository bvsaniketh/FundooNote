package com.bridgeit.businessservices;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgeit.controller.NoteController;
import com.bridgeit.json.Response;
import com.bridgeit.model.Note;
import com.bridgeit.utilityservices.NoteService;

@Service
public class NoteBusinessService {

	@Autowired
	NoteService service;
	
	private static final Logger logger = Logger.getLogger(NoteBusinessService.class);

	public Response trashService(Note note) {
		
		Response resp=new Response();
		logger.info("Deleting Permanently the selected user node");
		logger.info(note.isTrash() && note.isDeletefromtrash());
		if (note.isTrash() && note.isDeletefromtrash()) {
			service.deleteNode(note);
			logger.info("Deleted the node permanently");
			resp.setStatus(note.getUser().getUser_id());
			resp.setMessage("Node deleted from trash");
			return resp;
		} else {
			resp.setMessage("Not deleted");
			logger.info("Not deleted");
		}
		return null;
		
	}

	
	
}
