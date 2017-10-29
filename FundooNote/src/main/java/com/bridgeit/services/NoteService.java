package com.bridgeit.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgeit.dao.NoteMapperImpl;
import com.bridgeit.dao.UserMapperImpl;
import com.bridgeit.model.Note;

@Service
public class NoteService {

	@Autowired
	NoteMapperImpl mapper;
	
	public void insertNote(Note newnote)
	{
		mapper.insertNote(newnote);
	}

	public void updateNote(Note note) {
		mapper.updateNote(note);
		
	}

	public void deleteNode(Note note) {
		mapper.deleteNote(note);
		
	}

	public Note getNotebyId(Note note) {
		Note notesend=mapper.getNotebyId(note); 
		return notesend;
	}

	public List<Note> selectAllNotes(Note note) {
		List<Note>notes=mapper.selectAllNotes(note);
		return notes;
	}

	public void archiveNote(Note note) {
		mapper.archiveNote(note);
		
	}
	
	public void trashNote(Note note)
	{
		mapper.trashNote(note);
	}

	public void setRemainder(Note note) {
		mapper.setRemainder(note);
		
	}

	
	
}
