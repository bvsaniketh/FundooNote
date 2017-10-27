package com.bridgeit.Model;

import java.util.Date;

/*import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;*/

/*@Document(indexName="fundoonotestest",type="notestest")*/
public class Note {
	
	/*@Id*/
	private int notes_id;
	private String title;
	private String description;
	private Register user;
	private Date lastaccessdate;
	private boolean archive;
	private boolean trash;
	private boolean deletefromtrash;
	private Date Remainder;
	
	public int getNotes_id() {
		return notes_id;
	}
	public void setNotes_id(int notes_id) {
		this.notes_id = notes_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Register getUser() {
		return user;
	}
	public void setUser(Register user) {
		this.user = user;
	}
	public Date getLastaccessdate() {
		return lastaccessdate;
	}
	public void setLastaccessdate(Date lastaccessdate) {
		this.lastaccessdate = lastaccessdate;
	}
	public boolean isArchive() {
		return archive;
	}
	public void setArchive(boolean archive) {
		this.archive = archive;
	}
	public boolean isTrash() {
		return trash;
	}
	public void setTrash(boolean trash) {
		this.trash = trash;
	}
	public boolean isDeletefromtrash() {
		return deletefromtrash;
	}
	public void setDeletefromtrash(boolean deletefromtrash) {
		this.deletefromtrash = deletefromtrash;
	}
	public Date getRemainder() {
		return Remainder;
	}
	public void setRemainder(Date remainder) {
		Remainder = remainder;
	}
	
	@Override
	public String toString() {
		return "Note [notes_id=" + notes_id + ", title=" + title + ", description=" + description + ", user=" + user
				+ ", lastaccessdate=" + lastaccessdate + ", archive=" + archive + ", trash=" + trash
				+ ", deletefromtrash=" + deletefromtrash + ", Remainder=" + Remainder + "]";
	}
	
	

	

}
	
	
	
	
	

