package com.capgemini.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.entities.Candidate;
import com.capgemini.entities.Employee;
import com.capgemini.entities.InterviewSchedule;
import com.capgemini.entities.PanelMember;
import com.capgemini.exception.EmployeeByIdNotFoundException;
import com.capgemini.exception.EmployeeNotFoundException;
import com.capgemini.exception.NoSuchCandidateException;
import com.capgemini.exception.NoSuchEmployeeException;
import com.capgemini.exception.NoSuchInterviewScheduleException;
import com.capgemini.exception.NoSuchPanelIdException;
import com.capgemini.exception.NoSuchPanelTypeFound;
import com.capgemini.exception.PasswordIncorrectException;
import com.capgemini.services.AdministratorService;

@RestController
@RequestMapping(path = "admin")
public class AdministratorController 
{   
	//Reference variable of Administrator Service
	@Autowired
	private AdministratorService service;
	
	@Autowired
	InterviewSchedule interview;

        //login Admin
	 //http://localhost:9090/project-api/admin/checkLogin/Admin/1/Admin123
	@GetMapping(path = "checkLogin/{type}/{userId}/{password}")
	public ResponseEntity<String> login(@PathVariable("type") String type, @PathVariable("userId") int userId,
			@PathVariable("password") String password) throws PasswordIncorrectException {
		String result = null;
		if (type.equalsIgnoreCase("Admin")) {
			result = service.checkLogin(userId, password, type);
		}

		return new ResponseEntity<String>(result, HttpStatus.OK);
	}
    
    
	//Add Candidate to the DataBase
	//http://localhost:9090/project-api/admin/saveCandidate  - POST
	@PostMapping(path = "saveCandidate")
	public ResponseEntity<String> saveCandidate(@RequestBody Candidate candidate)
	{
		ResponseEntity<String> response = null;
		boolean result = service.addCandidate(candidate);
		if (result)
		{
			response = new ResponseEntity<String>("Candidate with id " + candidate.getCandidateId() + " is created.", HttpStatus.CREATED);
		}
		return response;
	}
    
	//Search Candidate by Id
	//http://localhost:9090/project-api/admin/searchById/1  - GET
	@GetMapping(path = "searchById/{candidateId}")
	public ResponseEntity<Candidate> getCandidateById(@PathVariable("candidateId") int candidateId) 
	{
		ResponseEntity<Candidate> response = null;
		Candidate Candidate = service.viewCandidateById(candidateId);
		response = new ResponseEntity<Candidate>(Candidate, HttpStatus.OK);
		return response;
	}
	
	//Search Interview By Id
	//http://localhost:9090/project-api/admin/searchInterview/1 - GET
	@GetMapping(path = "searchInterview/{interviewId}")
	public ResponseEntity<InterviewSchedule> getInterviewById(@PathVariable("interviewId") int interviewId)
	{
		ResponseEntity<InterviewSchedule> response = null;
		InterviewSchedule interview = service.viewInterviewById(interviewId);
		try
		{
			service.sendInterviewStatusEmail(interview);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		response = new ResponseEntity<InterviewSchedule>(interview, HttpStatus.OK);
		return response;
	}
	
	//Schedule Interview 
	//http://localhost:9090/project-api/admin/saveinterviewSchedule  - POST
	@PostMapping(path = "saveinterviewSchedule")
	public ResponseEntity<String> saveinterviewSchedule(@RequestBody InterviewSchedule interviewSchedule)
	{
			ResponseEntity<String> response = null;
			boolean result = service.scheduleInterview(interviewSchedule);
			if (result)
			{
				response = new ResponseEntity<String>(" Interview with  " + interviewSchedule.getInterviewId() + " is schedule.", HttpStatus.CREATED);
				try
				{
					service.sendInterviewScheduledEmail(interviewSchedule);
				}
				catch(Exception e)
				{
					System.out.println(e);
				}
			}
			return response;
	}

    //Update the Tech Rating 
	//http://localhost:9090/project-api/admin/updateInterviewScheduleOfTechRatig/1/4  - PUT
	@PutMapping(path = "updateInterviewScheduleOfTechRatig/{interviewId}/{techRating}")
	public ResponseEntity<String> updateInterviewScheduleOfTechRatig(@PathVariable(value = "techRating") String techRating,@PathVariable(value = "interviewId") int interviewId) 
	{
		ResponseEntity<String> response = null;
		InterviewSchedule interviewSchedule = null;
		int result = service.updateInterviewScheduleOfTechRatig(interviewId, techRating);
		if (result > 0)
		{
			response = new ResponseEntity<String>(" Upadate Interview Schedule with Tech Rating", HttpStatus.OK);
		}
		return response;
	}
    
	//Update the HR Rating
	//http://localhost:9090/project-api/admin/updateInterviewScheduleOfHrRating/1/4 - PUT
	@PutMapping(path = "updateInterviewScheduleOfHrRating/{interviewId}/{hrRating}")
	public ResponseEntity<String> updateInterviewScheduleOfHrRating(@PathVariable(value = "hrRating") String hrRating,
			@PathVariable(value = "interviewId") int interviewId) 
	{
		ResponseEntity<String> response = null;
		InterviewSchedule interviewSchedule = null;
		int result = service.updateInterviewScheduleOfHrRating(interviewId, hrRating);
		if (result > 0)
		{
			response = new ResponseEntity<String>("  Upadate Interview Schedule with HR Rating", HttpStatus.OK);
		}
		return response;
	}
    
	//Update the Final Status
	//http://localhost:9090/project-api/admin/updateInterviewScheduleOfFinalStatus/1/Ok - PUT
	@PutMapping(path = "updateInterviewScheduleOfFinalStatus/{interviewId}/{finalStatus}")
	public ResponseEntity<String> updateInterviewScheduleOfFinalStatus(@PathVariable(value = "finalStatus") String finalStatus,@PathVariable(value = "interviewId") int interviewId)
	{
		ResponseEntity<String> response = null;
		InterviewSchedule interviewSchedule = null;
		int result = service.updateInterviewScheduleOfFinalStatus(interviewId, finalStatus);
		if (result > 0)
		{
			response = new ResponseEntity<String>("  Upadate Interview Schedule with FinalStatus", HttpStatus.OK);
			
		}
		return response;
	}
    
	//Cancel the Scheduled Interview
	//http://localhost:9090/project-api/admin/deactivateById/true/1 - DELETE
	@DeleteMapping(path = "deactivateById/{deleteStatus}/{interviewId}")
	public ResponseEntity<String> deactivateInterviewSchedule(@RequestBody @PathVariable("interviewId") int interviewID,@PathVariable("deleteStatus") boolean deleteStatus) 
	{
		ResponseEntity<String> response = null;
		boolean result = service.cancelInterview(interviewID, deleteStatus);
		if (result)
		{
			response = new ResponseEntity<String>("Interview Schedule with id " + interviewID + " is deleted.",HttpStatus.OK);
		}
		return response;
	}
	
	//Add Panel Members to the DataBase
	//http://localhost:9090/project-api/admin/savePanelMember - POST
	@PostMapping(path = "savePanelMember")
	public ResponseEntity<String> savePanelMember(@RequestBody PanelMember panelMember) 
	{
		ResponseEntity<String> response = null;
		boolean result = service.addPanelMember(panelMember);
		if (result)
		{
			response = new ResponseEntity<String>("PanelMember with id " + panelMember.getPanelId() + " is created.",HttpStatus.CREATED);
		}
			return response;
	}
	
	//http://localhost:9090/project-api/admin/searchByPanelMemberId/1  - GET
		@GetMapping(path = "searchByPanelMemberId/{panelId}")
		public ResponseEntity<PanelMember> getPanelMemberById(@PathVariable("panelId") int panelId)  
		{
			ResponseEntity<PanelMember> response = null;
			PanelMember panelMember = service.viewPanelMemberById(panelId);
			response = new ResponseEntity<PanelMember>(panelMember, HttpStatus.OK);
			return response;
		}
	
	//Employee Based On ID
	//http://localhost:9090/project-api/admin/searchByEmployeeId/1 - GET
	@GetMapping(path = "searchByEmployeeId/{employeeId}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable("employeeId") int employeeId) 
	{
		ResponseEntity<Employee> response = null;
		Employee employee = service.searchEmployeeById(employeeId);
		response = new ResponseEntity<Employee>(employee, HttpStatus.OK);
		return response;
	}
	
	//List of All Employees Based On Name
	//http://localhost:9090/project-api/admin/searchEmployeesByName/Name - GET
	@GetMapping(path = "searchEmployeesByName/{employeeName}")
	public ResponseEntity<List<Employee>> getAllEmployeesByName(@PathVariable("employeeName") String employeeName)
	{
		ResponseEntity<List<Employee>> response = null;
		List<Employee> employee = service.searchEmployeeByName(employeeName);
		response = new ResponseEntity<List<Employee>>(employee, HttpStatus.OK);
		return response;
	}	
    
	//Deactivate Panel Members
	//http://localhost:9090/project-api/admin/deactivatePanelMember/true/1 - DELETE
	@DeleteMapping(path = "deactivatePanelMember/{deleteStatus}/{panelId}")
	public ResponseEntity<String> deactivatePanelMember(@RequestBody @PathVariable("panelId") int panelId,
			@PathVariable("deleteStatus") boolean deleteStatus) throws NoSuchPanelIdException 
	{
	    ResponseEntity<String> response = null;
		boolean result = service.deletePanelMember(panelId, deleteStatus);
		if (result)
		{
			response = new ResponseEntity<String>("Candidate with id " + panelId + " is deleted.", HttpStatus.OK);
		}
		return response;
	}
	
	//List all Panel Members 
	//http://localhost:9090/project-api/admin/listAllPanelMembers - GET
	@GetMapping(path = "listAllPanelMembers")
	public ResponseEntity<List<PanelMember>> getAllExistingPanelMembers() 
	{
		ResponseEntity<List<PanelMember>> response = null;
		List<PanelMember> list = service.listAllExistingPanelMember();
		response = new ResponseEntity<List<PanelMember>>(list, HttpStatus.OK);
		return response;
	}
	
	//List of all Inactive Panel Members
	//http://localhost:9090/project-api/admin/inactivePanelMember - GET
	@GetMapping(path = "inactivePanelMember")
	public ResponseEntity<List<PanelMember>> getAllInActivePanelMember()
	{
		ResponseEntity<List<PanelMember>> response = null;
		List<PanelMember> list = service.InActivePanelMember();
		response = new ResponseEntity<List<PanelMember>>(list, HttpStatus.OK);
		return response;
	}

	//List of all Panel Members
	//http://localhost:9090/project-api/admin/activePanelMember - GET
	@GetMapping(path = "activePanelMember")
	public ResponseEntity<List<PanelMember>> getAllActivePanelMember() 
	{
		ResponseEntity<List<PanelMember>> response = null;
		List<PanelMember> list = service.activePanelMember();
		response = new ResponseEntity<List<PanelMember>>(list, HttpStatus.OK);
		return response;
	}
	
	//List of all Active Interview
	//http://localhost:9090/project-api/admin/activeInterviewSchedule - GET
	@GetMapping(path = "activeInterviewSchedule")
	public ResponseEntity<List<InterviewSchedule>> getAllActiveInterviewSchedule()
	{
		ResponseEntity<List<InterviewSchedule>> response = null;
		List<InterviewSchedule> list = service.activeInterview();
		response = new ResponseEntity<List<InterviewSchedule>>(list, HttpStatus.OK);
		return response;
	}

	//List of all Inactive Interview
	//http://localhost:9090/project-api/admin/inactiveInterviewSchedule - GET
	@GetMapping(path = "inactiveInterviewSchedule")
	public ResponseEntity<List<InterviewSchedule>> getAllInactiveInterviewSchedule() 
	{
		ResponseEntity<List<InterviewSchedule>> response = null;
		List<InterviewSchedule> list = service.inactiveInterview();
		response = new ResponseEntity<List<InterviewSchedule>>(list, HttpStatus.OK);
		return response;
	}
	
	//Add Employee to the Database
	//http://localhost:9090/project-api/admin/saveEmployee - POST
	@PostMapping(path="saveEmployee")
	public ResponseEntity<String> saveEmployee(@RequestBody Employee employee)
	{
		ResponseEntity<String> response = null;
		boolean result = service.addEmployee(employee);
		if (result)
		{
			response = new ResponseEntity<String>("Employee with Employee Id " + employee.getEmployeeId() + " is created.", HttpStatus.CREATED);
		}
		return response;
	}

	//Handling Exception 
	@ExceptionHandler(value = NoSuchCandidateException.class)
	public ResponseEntity<String> handleNoSuchCandidateException()
	{
		return new ResponseEntity<String>("Candidate with given ID does not exist.", HttpStatus.OK);
	}
    
	//Handling Exception 
	@ExceptionHandler(value = NoSuchInterviewScheduleException.class)
	public ResponseEntity<String> handleNoSuchInterviewScheduleException() 
	{
		return new ResponseEntity<String>("Interview with given ID does not exist.", HttpStatus.OK);
	}
    
	//Handling Exception 
	@ExceptionHandler(value = NoSuchEmployeeException.class)
	public ResponseEntity<String> handleNoSuchEmployeeException()
	{
		return new ResponseEntity<String>("Employee with given ID does not exist.", HttpStatus.OK);
	}
    
	//Handling Exception EmployeeNotFound Exception
	@ExceptionHandler(value = EmployeeNotFoundException.class)
	public ResponseEntity<String> handleEmployeeNotFoundException()
	{
		return new ResponseEntity<String>("Employee with given Name does not exists.", HttpStatus.OK);
	}
	
	//Handling Exception EmployeeByIdNotFound Exception
	@ExceptionHandler(value = EmployeeByIdNotFoundException.class)
	public ResponseEntity<String> handleEmployeeByIdNotFoundException()
	{
		return new ResponseEntity<String>("Employee with given ID does not exist.", HttpStatus.OK);
	}
	
	// Handling Exception
	@ExceptionHandler(value = NoSuchPanelTypeFound.class)
	public ResponseEntity<String> handleNoSuchPanelTypeFound() {
		return new ResponseEntity<String>("panel with the type is not found", HttpStatus.OK);
	}
	
	////Handling Exception PanelByIdNotFound Exception
	@ExceptionHandler(value = NoSuchPanelIdException.class)
	public ResponseEntity<String> handleNoSuchPanelIdException() {
		return new ResponseEntity<String>("panelId is not found", HttpStatus.OK);
	}

}