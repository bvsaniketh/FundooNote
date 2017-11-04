package com.bridgeit.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.bridgeit.model.Collaborator;
import com.bridgeit.model.Note;
import com.bridgeit.model.Register;

@Component
public interface NoteMapper {

	public void insertNote(Note newnote);
	public void updateNote(Note modifynote);
	public void deleteNote(Note note);
	public Note getNotebyId(Note note);
	public List<Note> selectAllNotes(Note note);
	public void archiveNote(Note note);
	public void trashNote(Note note);
	/*public void deletePermNote(Note note);*/
	public void setRemainder(Note note);
	public List<Note> selectAllFundooNotes();
	public void collabNote(Collaborator collaborator);
	public List<Register> selectColabNotes(int notes_id);
}
