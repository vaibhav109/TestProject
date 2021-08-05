package com.capgemini.services;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.capgemini.entities.Candidate;
import com.capgemini.entities.Employee;
import com.capgemini.entities.InterviewSchedule;
import com.capgemini.entities.Login;
import com.capgemini.entities.PanelMember;
import com.capgemini.exception.EmployeeByIdNotFoundException;
import com.capgemini.exception.EmployeeNotFoundException;
import com.capgemini.exception.NoSuchCandidateException;
import com.capgemini.exception.NoSuchEmployeeException;
import com.capgemini.exception.NoSuchInterviewScheduleException;
import com.capgemini.exception.NoSuchPanelIdException;
import com.capgemini.exception.PasswordIncorrectException;
import com.capgemini.repository.CandidateRepository;
import com.capgemini.repository.EmployeeRepository;
import com.capgemini.repository.InterviewScheduleRepository;
import com.capgemini.repository.LoginRepository;
import com.capgemini.repository.PanelMemberRepository;

@Service("AdministratorService")
public class AdministratorServiceImpl implements AdministratorService 
{   
	//Reference variable of CandidateRepository
	@Autowired
	private CandidateRepository repositoryOfCandidate;
    
	//Reference variable of PanelMemberRepository
	@Autowired
	private PanelMemberRepository repositoryOfPanelMember;
	
	//Reference variable of InterviewScheduleRepository
	@Autowired
	private InterviewScheduleRepository repositoryOfInterviewSchedule;
    
	//Reference variable of EmployeeRepository
	@Autowired
	private EmployeeRepository repositoryOfEmployee;
	
	@Autowired
	private LoginRepository repositoryOflogin;

	private JavaMailSender javaMailSender;
	
	//Logger Object created, used to log messages for a specific application component
    private static final Logger LOGGER = LoggerFactory.getLogger(AdministratorServiceImpl.class);
    

	@Override
	public String checkLogin(int userId, String password, String type) throws PasswordIncorrectException {
		Login login = repositoryOflogin.findById(userId).orElse(null);
		if (login != null) {
			int id1 = login.getLoginId();
			String password2 = login.getPassword();
			String type2 = login.getUserType();
			if (userId == id1 && password.equals(password2) && type.equalsIgnoreCase(type2)) {
				return "Login successful";
			}
			throw new PasswordIncorrectException("Given password is incorrect or type is incorrect");
		}
		return "Login Unsucessful";
	}
    
	@Override
	@Transactional
	public boolean addCandidate(Candidate candidate) 
	{   LOGGER.trace("Entering Inside addCandidate Method");
		boolean result = false;
		candidate = repositoryOfCandidate.save(candidate);
		if (candidate.getCandidateId() > 0)
		result = true;
		LOGGER.info("Candidate Added Successfully");
		return result;
	}
	
	@Override
	public Candidate viewCandidateById(int candidateId) throws NoSuchCandidateException
	{
		LOGGER.trace("Entering Inside viewCandidateById Method");
		if (repositoryOfCandidate.existsById(candidateId)) {
			LOGGER.info("Candidate Found Successfully");
			return repositoryOfCandidate.findById(candidateId).get();
		}
		LOGGER.error("Candidate Not Found");
		throw new NoSuchCandidateException("Candidate with id " + candidateId + " not found.");
	}
    
	@Override
	public boolean scheduleInterview(InterviewSchedule interviewSchedule) 
	{
		LOGGER.trace("Entering Inside scheduleInterview Method");
		boolean result = false;
		interviewSchedule
				.setCandidate(repositoryOfCandidate.findById(interviewSchedule.getCandidate().getCandidateId()).get());
		interviewSchedule.setPanelMember(
				repositoryOfPanelMember.findById(interviewSchedule.getPanelMember().getPanelId()).get());
		interviewSchedule = repositoryOfInterviewSchedule.save(interviewSchedule);
		if (interviewSchedule.getInterviewId() > 0)
			result = true;
		LOGGER.info("Interview Scheduled Successfully");
		return result;
	}
    
	@Override
	public int updateInterviewScheduleOfTechRatig(int interviewId, String techRating) throws NoSuchInterviewScheduleException
	{   
		LOGGER.trace("Entering Inside updateInterviewScheduleOfTechRatig Method");
		if (repositoryOfInterviewSchedule.existsById(interviewId)) {
			LOGGER.info("Tech Rating Updated Successfully");
			return repositoryOfInterviewSchedule.updateTechRatingInInterviewSchedule(interviewId, techRating);
		}
		LOGGER.error("Interview Id Not Found");
		throw new NoSuchInterviewScheduleException("Interview with id " + interviewId + " not found.");
	}
    
	@Override
	public int updateInterviewScheduleOfHrRating(int interviewId, String hrRating) throws NoSuchInterviewScheduleException 
	{   
		LOGGER.trace("Entering Inside updateInterviewScheduleOfHrRating Method");
		if (repositoryOfInterviewSchedule.existsById(interviewId)) {
			LOGGER.info("HR Rating Updated Successfully");
			return repositoryOfInterviewSchedule.updateHRRatingInInterviewSchedule(interviewId, hrRating);
		}
		LOGGER.error("Interview Id Not Found");
		throw new NoSuchInterviewScheduleException("Interview with id " + interviewId + " not found.");
	}
    
	@Override
	public int updateInterviewScheduleOfFinalStatus(int interviewId, String finalStatus) throws NoSuchInterviewScheduleException 
	{   
		LOGGER.trace("Entering Inside updateInterviewScheduleOfFinalStatus Method");
		if (repositoryOfInterviewSchedule.existsById(interviewId)) {
			LOGGER.info("Final Status Updated Successfully");
			return repositoryOfInterviewSchedule.updateFinalStatusInInterviewSchedule(interviewId, finalStatus);
		}
		LOGGER.error("Interview Id Not Found");
		throw new NoSuchInterviewScheduleException("Interview with id " + interviewId + " not found.");
	}
    
	@Override
	public InterviewSchedule viewInterviewById(int interviewId) throws NoSuchInterviewScheduleException
	{
		LOGGER.trace("Entering Inside viewInterviewById method");
		if (repositoryOfInterviewSchedule.existsById(interviewId)) {
			LOGGER.info("Interview Details Based on Interview Id");
			return repositoryOfInterviewSchedule.findById(interviewId).get();
		}
		LOGGER.error("Interview Id Not Found");
		throw new NoSuchInterviewScheduleException("Interview Schedule with id " + interviewId + " not found.");
	}
    
	@Override
	public boolean cancelInterview(int interviewId, boolean deleteStatus) throws NoSuchInterviewScheduleException
	{   
		LOGGER.trace("Entering Inside cancelInterview method");
		if (repositoryOfInterviewSchedule.existsById(interviewId)) {
			repositoryOfInterviewSchedule.softDeleteOfInterviewSchedule(interviewId, deleteStatus);
			LOGGER.info("Scheduled Interview Is Canceled Successfully");
			return true;
		}
		LOGGER.error("Interview Id Not Found");
		throw new NoSuchInterviewScheduleException("Interview Schedule with id " + interviewId + " not found.");
	}
    
	@Override
	public List<InterviewSchedule> activeInterview()
	{
		LOGGER.trace("Entering Inside activeInterview method");
		LOGGER.info("Active Interview Found Successfully");
		return repositoryOfInterviewSchedule.findAllActiveSchedule();
	}
    
	//This Method will Show the list of all Inactive Interview
	@Override
	public List<InterviewSchedule> inactiveInterview() 
	{
		LOGGER.trace("Entering Inside inactiveInterview method");
		LOGGER.info("Inactive Interview Found Successfully");
		return repositoryOfInterviewSchedule.findAllInactiveSchedule();
	}
    
	@Override
	public boolean addPanelMember(PanelMember panelMember)
	{   LOGGER.trace("Entering Inside addPanelMember method");
		boolean result = false;
		panelMember.setCandidate(repositoryOfCandidate.findById(panelMember.getCandidate().getCandidateId()).get());
		panelMember.setEmployee(repositoryOfEmployee.findById(panelMember.getEmployee().getEmployeeId()).get());
		panelMember = repositoryOfPanelMember.save(panelMember);

		if (panelMember.getPanelId() > 0)
			result = true;
		LOGGER.info("Panel Member Added Successfully");
		return result;
	}
	
	@Override
	public boolean addEmployee(Employee employee) 
	{
		LOGGER.trace("Entering Inside addEmployee method");
		boolean result = false;
		employee = repositoryOfEmployee.save(employee);
		if (employee.getEmployeeName().isEmpty())
		{
			return false;
		} 
		else if (employee.getEmployeeId() > 0) 
		{
			result = true;
		}
		LOGGER.info("Employee Added Successfully");
		return result;
	}
	
	@Override
	public Employee searchEmployeeById(int employeeId) throws EmployeeByIdNotFoundException
	{   
		LOGGER.trace("Entering Inside searchEmployeeById method");
		if (repositoryOfEmployee.existsById(employeeId)) {
			LOGGER.info("Employee Found Successfully");
			return repositoryOfEmployee.findById(employeeId).get();
		}
		LOGGER.error("Employee Id Not Found");
		throw new EmployeeByIdNotFoundException("Employee with id " + employeeId + " not found.");
	}
	
	@Override
	public List<Employee> searchEmployeeByName(String name) throws EmployeeNotFoundException 
	{
		LOGGER.trace("Entering Inside searchEmployeeByName method");
		if (repositoryOfEmployee.readAllEmployeesByEmployeeName(name).isEmpty()) {
			LOGGER.error("Employee Name Not Found");
			throw new EmployeeNotFoundException("Employee Name is Empty");
		}
		LOGGER.info("Employee Found Successfully");
		return repositoryOfEmployee.readAllEmployeesByEmployeeName(name);
	}
    
	@Override
	public boolean deletePanelMember(int panelId, boolean deleteStatus) throws NoSuchPanelIdException 
	{   
		LOGGER.trace("Entering Inside deletePanelMember method");
		if (repositoryOfPanelMember.existsById(panelId)) {
			repositoryOfPanelMember.softDeletePanelMember(panelId, deleteStatus);
			LOGGER.info("Panel Member Deleted Successfully");
			return true;
		}
		LOGGER.error("Panel Member Not Found");
		throw new NoSuchPanelIdException("PanelMember with id " + panelId + " not found.");
	}
	
	@Override
	public List<PanelMember> activePanelMember() throws NullPointerException 
	{
		LOGGER.trace("Entering Inside activePanelMember method");
		LOGGER.info("Active Panel Member Found Successfully");
		return repositoryOfPanelMember.findAllActivePanelMember();
	}
    
	@Override
	public List<PanelMember> InActivePanelMember()
	{
		LOGGER.trace("Entering Inside InActivePanelMember method");
		LOGGER.info("Inactive Panel Member Found Successfully");
		return repositoryOfPanelMember.findAllInactivePanelMember();
	}
    
	@Override
	public List<PanelMember> listAllExistingPanelMember() throws NullPointerException
	{
		LOGGER.trace("Entering Inside listAllExistingPanelMember method");
		LOGGER.info(" All Panel Members Found Successfully");
		return repositoryOfPanelMember.findAll();
	}

	@Autowired
	@Override
	public void mailService(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
		
	}

	@Override
	public void sendInterviewScheduledEmail(InterviewSchedule interviewSchedule) {
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(interviewSchedule.getCandidate().getEmail());
		mail.setSubject("URGENT ATTENTION Interview Scheduled");
		mail.setText("Your Interview is scheduled on "+interviewSchedule.getInterviewDate()+" at "+interviewSchedule.getInterviewTime());
		
		javaMailSender.send(mail);
		
	}

	@Override
	public void sendInterviewStatusEmail(InterviewSchedule interviewSchedule) {
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(interviewSchedule.getCandidate().getEmail());
		mail.setSubject("URGENT ATTENTION Interview Scheduled");
		mail.setText("Your Interview status is "+interviewSchedule.getFinalStatus());
		
		javaMailSender.send(mail);
		
	}

	@Override
	public PanelMember viewPanelMemberById(int panelId) throws NoSuchPanelIdException 
	{   
		LOGGER.trace("Entering viewPanelMemberById method");
		if(repositoryOfPanelMember.existsById(panelId)) 
		{   
			LOGGER.info("Panel Member Found Successfully");
			return repositoryOfPanelMember.findById(panelId).get();
		}
		LOGGER.error("Panel Member Not Found");
		throw new NoSuchPanelIdException("panel with id "+panelId+" not found");
	}

	

}
