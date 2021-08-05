package com.capgemini.services;

import java.util.List;

import org.springframework.mail.javamail.JavaMailSender;

import com.capgemini.entities.Candidate;
import com.capgemini.entities.Employee;
import com.capgemini.entities.InterviewSchedule;
import com.capgemini.entities.PanelMember;
import com.capgemini.entities.User;
import com.capgemini.exception.EmployeeByIdNotFoundException;
import com.capgemini.exception.EmployeeNotFoundException;
import com.capgemini.exception.NoSuchCandidateException;
import com.capgemini.exception.NoSuchInterviewScheduleException;
import com.capgemini.exception.NoSuchPanelIdException;
import com.capgemini.exception.PasswordIncorrectException;

public interface AdministratorService
{
	public boolean loginUser(User user);
	
	public  User registration(User user);

	public boolean addCandidate(Candidate candidate);
	
	public Candidate viewCandidateById(int candidateId) throws NoSuchCandidateException;
	
	public boolean scheduleInterview(InterviewSchedule interviewSchedule);
	
	public int updateInterviewScheduleOfTechRatig(int  interviewId,String techRating);
	
	public int updateInterviewScheduleOfHrRating(int  interviewId,String hrRating);
	
	public int updateInterviewScheduleOfFinalStatus(int  interviewId,String finalStatus);
	
	public InterviewSchedule viewInterviewById(int interviewId) throws NoSuchInterviewScheduleException;

	public boolean cancelInterview(int interviewId, boolean deleteStatus) throws NoSuchInterviewScheduleException;
	
	public List<InterviewSchedule> activeInterview();
	
	public List<InterviewSchedule> inactiveInterview();
	
	public boolean addPanelMember(PanelMember panelMember);
	
	public PanelMember viewPanelMemberById(int panelId) throws NoSuchPanelIdException;				//view panel member by id
	
	
	public Employee searchEmployeeById(int employeeId) throws EmployeeByIdNotFoundException;
	
	public List<Employee> searchEmployeeByName(String employeeName)throws EmployeeNotFoundException;
	
	public boolean deletePanelMember(int panelId, boolean deleteStatus) throws NoSuchPanelIdException;
	
	public List<PanelMember> activePanelMember();
	
	public List<PanelMember> InActivePanelMember();
	
	public List<PanelMember> listAllExistingPanelMember();
	
	public boolean addEmployee(Employee employee);

	
	public void mailService(JavaMailSender javaMailSender);
	public void sendInterviewScheduledEmail(InterviewSchedule interviewSchedule);
	public void sendInterviewStatusEmail(InterviewSchedule interviewSchedule);
}