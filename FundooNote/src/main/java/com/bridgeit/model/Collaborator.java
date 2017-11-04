package com.bridgeit.model;


public class Collaborator {
	

	Register user;
	Note note;
	String collaboratorEmail;
	int collaborated_Id;
	
	public Register getUser() {
		return user;
	}
	public void setUser(Register user) {
		this.user = user;
	}
	public Note getNote() {
		return note;
	}
	public void setNote(Note note) {
		this.note = note;
	}
	public String getCollaboratorEmail() {
		return collaboratorEmail;
	}
	public void setCollaboratorEmail(String collaboratorEmail) {
		this.collaboratorEmail = collaboratorEmail;
	}
	public int getCollaborated_Id() {
		return collaborated_Id;
	}
	public void setCollaborated_Id(int collaborated_Id) {
		this.collaborated_Id = collaborated_Id;
	}
	
	 @Override
	public String toString() {
		return "Collaborator [user=" + user + ", note=" + note + ", collaboratorEmail=" + collaboratorEmail
				+ ", collaborated_Id=" + collaborated_Id + "]";
	}
	
	
	
	
	

}
