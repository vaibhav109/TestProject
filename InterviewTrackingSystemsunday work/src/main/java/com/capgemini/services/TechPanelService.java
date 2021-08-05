package com.capgemini.services;

import java.util.List;
import com.capgemini.entities.Candidate;
import com.capgemini.entities.PanelMember;
import com.capgemini.exception.CandidateNotFoundException;
import com.capgemini.exception.NoSuchInterviewIdException;
import com.capgemini.exception.NoSuchPanelIdException;
import com.capgemini.exception.NoSuchPanelTypeFound;
import com.capgemini.exception.PasswordIncorrectException;

public interface TechPanelService 
{

	public List<Candidate> viewInterviewMemebers()throws CandidateNotFoundException; 			//list all candidates
	
	public int updateCandidateTechRating(int interviewId,String techRating)throws NoSuchInterviewIdException;		//update candidate techRating
	
	public Candidate viewCandidateById(int candidateId)throws CandidateNotFoundException;			//view candidate by id
	
	public PanelMember viewPanelMemberById(int panelId) throws NoSuchPanelIdException;		//view panel member by Id
	
	public List<PanelMember> viewPanelMemberByType(String employeeType)throws NoSuchPanelTypeFound; 		//view panel members by type

	String checkLogin(int userId, String password, String type) throws PasswordIncorrectException;
}
