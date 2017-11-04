package com.bridgeit.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.bridgeit.model.Collaborator;
import com.bridgeit.model.Note;
import com.bridgeit.model.Register;
import com.bridgeit.mybatisutility.MyBatisUtil;

@Service
public class NoteMapperImpl{
	
	private static final Logger logger=Logger.getLogger(NoteMapperImpl.class);
	SqlSession session;

	public void insertNote(Note newnote)
	
	{
		session=MyBatisUtil.getSqlSessionFactory().openSession();
		try
		{
		NoteMapper notemapper=session.getMapper(NoteMapper.class);
		notemapper.insertNote(newnote);
		session.commit();
		}
		
		finally
		{
		session.close();
		}
	}

	public void updateNote(Note note)
	
	{
		session=MyBatisUtil.getSqlSessionFactory().openSession();
		
		try
		{
		NoteMapper notemapper=session.getMapper(NoteMapper.class);
		notemapper.updateNote(note);
		session.commit();
		}
		finally
		{
		session.close();
		}
	}

	public void deleteNote(Note note)
	
	{
		 session=MyBatisUtil.getSqlSessionFactory().openSession();
		
		try
		{
		NoteMapper notemapper=session.getMapper(NoteMapper.class);
		notemapper.deleteNote(note);
		session.commit();
		}
		finally
		{
		session.close();
		}
	}

	public Note getNotebyId(Note note)
	
	{
		 session=MyBatisUtil.getSqlSessionFactory().openSession();
		
		try
		{
		NoteMapper notemapper=session.getMapper(NoteMapper.class);
		Note notesend=notemapper.getNotebyId(note);
		session.commit();
		logger.info(notesend);
		return notesend;
		}
		finally
		{
		session.close();
		}
		
	}

	public List<Note> selectAllNotes(Note note)
	
	{
	session=MyBatisUtil.getSqlSessionFactory().openSession();
	List<Note>notes;
	
		try
		{
			NoteMapper notemapper=session.getMapper(NoteMapper.class);
			notes =notemapper.selectAllNotes(note);
			session.commit();
			logger.info(notes);
			return notes;
		}
		finally
		{
			session.close();
		}
	}	

	public void archiveNote(Note note) 
	
	{
		session=MyBatisUtil.getSqlSessionFactory().openSession();
		try
		{
		
		NoteMapper notemapper=session.getMapper(NoteMapper.class);
		logger.info(note);
		notemapper.archiveNote(note);
		session.commit();
		logger.info(note +" has been archived by the user");
		}
		finally
		{
		session.close();
		}
		
	}
	
	public void trashNote(Note note) 
	
	{
		session=MyBatisUtil.getSqlSessionFactory().openSession();
		try
		{
		
		NoteMapper notemapper=session.getMapper(NoteMapper.class);
		logger.info(note);
		notemapper.trashNote(note);
		session.commit();
		logger.info(note +" has been archived by the user");
		}
		finally
		{
		session.close();
		}
		
	}

	public void setRemainder(Note note) 
	
	{
		session=MyBatisUtil.getSqlSessionFactory().openSession();
		
		try
		{
		NoteMapper notemapper=session.getMapper(NoteMapper.class);
		logger.info(note);
		notemapper.setRemainder(note);
		session.commit();
		logger.info("Remainder set in the database");
		}
		finally
		{
			session.close();
		}
		
	}

	public List<Note> selectAllFundooNotes() 
	
	{
		session=MyBatisUtil.getSqlSessionFactory().openSession();
		List<Note> notes;
		try
		{
		NoteMapper notemapper=session.getMapper(NoteMapper.class);
		notes=notemapper.selectAllFundooNotes();
		session.commit();
		logger.info("Fetched all the notes from database");
		}
		finally
		{
			session.close();
		}
		return notes;
	}
	
	public void collabNote(Collaborator collaborator)
	{
		session=MyBatisUtil.getSqlSessionFactory().openSession();
		logger.info("Inside note mapper impl");
		try
		{
		NoteMapper notemapper=session.getMapper(NoteMapper.class);
		notemapper.collabNote(collaborator);
		session.commit();
		logger.info("Collaborated into the database");
		}
		finally
		{
			session.close();
		}
		
	}
	
	public List<Register> selectColabNotes(int notes_id)
	
	
	{
	session=MyBatisUtil.getSqlSessionFactory().openSession();
	List<Register>notes;
	
		try
		{	logger.info(notes_id);
			NoteMapper notemapper=session.getMapper(NoteMapper.class);
			notes =notemapper.selectColabNotes(notes_id);
			session.commit();
			logger.info(notes);
			return notes;
		}
		finally
		{
			session.close();
		}
	}	
	

}
