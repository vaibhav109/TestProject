package com.capgemini.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.entities.Candidate;
import com.capgemini.entities.InterviewSchedule;
import com.capgemini.entities.PanelMember;
import com.capgemini.entities.User;
import com.capgemini.exception.CandidateNotFoundException;
import com.capgemini.exception.NoSuchInterviewIdException;
import com.capgemini.exception.NoSuchPanelIdException;
import com.capgemini.exception.NoSuchPanelTypeFound;
import com.capgemini.exception.PasswordIncorrectException;
import com.capgemini.services.HRPanelService;

@RestController
@RequestMapping(path = "hr")
public class HRPanelController {
	// Reference variable of HRPanelService
	@Autowired
	private HRPanelService service;

	//http://localhost:9090/project-api/hr/login
	@PostMapping(path = "login")
	public ResponseEntity<String> checkLogin(@RequestBody User user) {
		ResponseEntity<String> response = null;

		boolean result = service.loginUser(user);
		if (result) {
			response = new ResponseEntity<String>("Username and Password is correct", HttpStatus.OK);
		} else {
			response = new ResponseEntity<String>("Username and Password is incorrect",
					HttpStatus.INTERNAL_SERVER_ERROR);

		}
		return response;
	}

	//http://localhost:9090/project-api/hr/resgistration

	@PostMapping(path = "resgistration")
	public User registration(@RequestBody User user) {
		return service.registration(user);
	}

	// 1. view all candidates
	// http://localhost:9090/project-api/hr/viewCandidates ---->Get
	@GetMapping(path = "/viewCandidates")
	public ResponseEntity<List<Candidate>> getAllCandidates() throws CandidateNotFoundException {
		ResponseEntity<List<Candidate>> response;
		List<Candidate> list = service.viewInterviewMemebers();
		response = new ResponseEntity<List<Candidate>>(list, HttpStatus.OK);
		return response;
	}

	// 2.view panel member by id
	// http://localhost:9090/project-api/hr/searchByPanelId/1 ---->Get
	@GetMapping(path = "searchByPanelId/{panelId}")
	public ResponseEntity<PanelMember> getPanelMemberById(@PathVariable("panelId") int panelId)
			throws NoSuchPanelIdException {
		ResponseEntity<PanelMember> response = null;
		PanelMember panelMember = service.viewPanelMemberById(panelId);
		response = new ResponseEntity<PanelMember>(panelMember, HttpStatus.OK);
		return response;
	}

	// 3.find panel by type
	// http://localhost:9090/project-api/hr/searchPanelMemberByType/HR --->GET
	@GetMapping(path = "searchPanelMemberByType/{employeeType}")
	public ResponseEntity<List<PanelMember>> getPanelMemberByType(@PathVariable("employeeType") String employeeType)
			throws NoSuchPanelTypeFound {
		ResponseEntity<List<PanelMember>> response = null;
		List<PanelMember> list = service.viewPanelMemberByType(employeeType);
		response = new ResponseEntity<List<PanelMember>>(list, HttpStatus.OK);
		return response;
	}

	// 4. view candidate by id
	// http://localhost:9090/project-api/hr/searchById/1 ---->Get
	@GetMapping(path = "searchById/{candidateId}")
	public ResponseEntity<Candidate> getCandidateById(@PathVariable("candidateId") int candidateId)
			throws CandidateNotFoundException {
		ResponseEntity<Candidate> response = null;
		Candidate candidate = service.viewCandidateById(candidateId);
		response = new ResponseEntity<Candidate>(candidate, HttpStatus.OK);
		return response;
	}

	// 5. updating HR rating
	// http://localhost:9090/project-api/hr/updateById/1/selected ---->Put
	@PutMapping(path = "updateById/{interviewId}/{hrRating}")
	public ResponseEntity<String> updateCandidateById(@PathVariable(value = "interviewId") int interviewId,
			@PathVariable(value = "hrRating") String hrRating) throws NoSuchInterviewIdException {
		ResponseEntity<String> response = null;
		int result = service.updateCandidateHRRating(interviewId, hrRating);
		if (result > 0) {
			response = new ResponseEntity<String>("hrRating with Id" + interviewId + "is successfully updated",
					HttpStatus.OK);
		}
		return response;
	}

	// 6.find candidate by techrating
	// http://localhost:9090/project-api/hr/searchCandidateByTechRating/selected
	// --->Get
	@GetMapping(path = "searchCandidateByTechRating/{techRating}")
	public ResponseEntity<List<InterviewSchedule>> getCandidateByTechRating(
			@PathVariable("techRating") String techRating) throws CandidateNotFoundException {
		ResponseEntity<List<InterviewSchedule>> response = null;
		List<InterviewSchedule> list = service.viewCandidateByTechRating(techRating);
		response = new ResponseEntity<List<InterviewSchedule>>(list, HttpStatus.OK);
		return response;
	}

	// Handling Exception
	@ExceptionHandler(value = NoSuchInterviewIdException.class)
	public ResponseEntity<String> handleNoSuchInterviewIdException() {
		return new ResponseEntity<String>("No interview id found", HttpStatus.OK);
	}

	// Handling Exception
	@ExceptionHandler(value = CandidateNotFoundException.class)
	public ResponseEntity<String> handleCandidateNotFoundException() {
		return new ResponseEntity<String>("candidate with the id is not found", HttpStatus.OK);
	}

	// Handling Exception
	@ExceptionHandler(value = NoSuchPanelIdException.class)
	public ResponseEntity<String> handleNoSuchPanelIdException() {
		return new ResponseEntity<String>("panel with the id is not found", HttpStatus.OK);
	}

	// Handling Exception
	@ExceptionHandler(value = NoSuchPanelTypeFound.class)
	public ResponseEntity<String> handleNoSuchPanelTypeFound() {
		return new ResponseEntity<String>("panel with the type is not found", HttpStatus.OK);
	}

}
