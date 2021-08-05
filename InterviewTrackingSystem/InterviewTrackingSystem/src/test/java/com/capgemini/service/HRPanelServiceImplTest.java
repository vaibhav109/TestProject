package com.capgemini.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import com.capgemini.entities.Candidate;
import com.capgemini.entities.InterviewSchedule;
import com.capgemini.entities.PanelMember;
import com.capgemini.exception.CandidateNotFoundException;
import com.capgemini.exception.NoSuchPanelIdException;
import com.capgemini.exception.NoSuchPanelTypeFound;
import com.capgemini.services.AdministratorService;
import com.capgemini.services.HRPanelService;

@SpringBootTest
class HRPanelServiceImplTest {
	// reference variable for AdministratorService
	@Autowired
	private AdministratorService administratorService;

	// reference variable for ApplicationContext
	@Autowired
	private ApplicationContext context;

	// reference variable for HRPanelService
	@Autowired
	private HRPanelService hservice;

	// test case for view candidate by Id
	@Test
	void testFindCandidateByIdReturnCandidate() throws CandidateNotFoundException {
		// 1,"Aman","Java","Python","null","BE","Analyst","3 months","Mumbai"
		Candidate expected = context.getBean(Candidate.class);
		expected.setCandidateId(1);
		expected.setCandidateName("Aman");
		expected.setPrimarySkill("Java");
		expected.setSecondarySkill("Python");
		expected.setExperience("null");
		expected.setQualification("BE");
		expected.setDesignation("Analyst");
		expected.setNoticePeriod("3 months");
		expected.setLocation("Mumbai");
		expected.setEmail("aman.parashar006@gmail.com");

		administratorService.addCandidate(expected);

		Candidate actual = hservice.viewCandidateById(expected.getCandidateId());
		assertEquals(expected.getCandidateId(), actual.getCandidateId());
		assertEquals(expected.getCandidateName(), actual.getCandidateName());
		assertEquals(expected.getPrimarySkill(), actual.getPrimarySkill());
		assertEquals(expected.getSecondarySkill(), actual.getSecondarySkill());
		assertEquals(expected.getExperience(), actual.getExperience());
		assertEquals(expected.getQualification(), actual.getQualification());
		assertEquals(expected.getDesignation(), actual.getDesignation());
		assertEquals(expected.getNoticePeriod(), actual.getNoticePeriod());
		assertEquals(expected.getLocation(), actual.getLocation());
		assertEquals(expected.getEmail(), actual.getEmail());
	}

	// test case for view PanelMember by Id
	@Test
	void testFindPanelMemberByIdReturnPanelMember() throws NoSuchPanelIdException {

		// 1,"HR","Mumbai",1000,Lakshmi,"HR","FINANCE",1,"Aman","Java","Python","null","BE","Analyst","3
		// months","Mumbai"
		PanelMember expected = context.getBean(PanelMember.class);
		expected.setPanelId(2);
		expected.setDeleteStatus(false);
		expected.setEmployeeType("HR");
		expected.setLocation("Mumbai");
		expected.getEmployee().setEmployeeId(1);
		expected.getEmployee().setEmployeeName("Durga");
		expected.getEmployee().setDepartment("HR");
		expected.getEmployee().setDesignation("FINANCE");
		expected.getCandidate().setCandidateId(1);
		expected.getCandidate().setCandidateName("Aman");
		expected.getCandidate().setPrimarySkill("Java");
		expected.getCandidate().setSecondarySkill("Python");
		expected.getCandidate().setExperience("null");
		expected.getCandidate().setQualification("BE");
		expected.getCandidate().setDesignation("Analyst");
		expected.getCandidate().setNoticePeriod("3 months");
		expected.getCandidate().setLocation("Mumbai");
		expected.getCandidate().setEmail("aman.parashar006@gmail.com");

		administratorService.addPanelMember(expected);

		PanelMember actual = hservice.viewPanelMemberById(expected.getPanelId());

		assertEquals(expected.getPanelId(), actual.getPanelId());
		assertEquals(expected.getDeleteStatus(), actual.getDeleteStatus());
		assertEquals(expected.getEmployeeType(), actual.getEmployeeType());
		assertEquals(expected.getLocation(), actual.getLocation());
		assertEquals(expected.getEmployee().getEmployeeId(), actual.getEmployee().getEmployeeId());
		assertEquals(expected.getEmployee().getEmployeeName(), actual.getEmployee().getEmployeeName());
		assertEquals(expected.getEmployee().getDepartment(), actual.getEmployee().getDepartment());
		assertEquals(expected.getEmployee().getDesignation(), actual.getEmployee().getDesignation());
		assertEquals(expected.getCandidate().getCandidateId(), actual.getCandidate().getCandidateId());
		assertEquals(expected.getCandidate().getCandidateName(), actual.getCandidate().getCandidateName());
		assertEquals(expected.getCandidate().getPrimarySkill(), actual.getCandidate().getPrimarySkill());
		assertEquals(expected.getCandidate().getSecondarySkill(), actual.getCandidate().getSecondarySkill());
		assertEquals(expected.getCandidate().getExperience(), actual.getCandidate().getExperience());
		assertEquals(expected.getCandidate().getQualification(), actual.getCandidate().getQualification());
		assertEquals(expected.getCandidate().getDesignation(), actual.getCandidate().getDesignation());
		assertEquals(expected.getCandidate().getNoticePeriod(), actual.getCandidate().getNoticePeriod());
		assertEquals(expected.getCandidate().getLocation(), actual.getCandidate().getLocation());
		assertEquals(expected.getCandidate().getEmail(), actual.getCandidate().getEmail());
	}

	// test case for view all candidates
	@Test
	public void testShouldReturnAllCandidates() throws CandidateNotFoundException {
		Candidate expected = context.getBean(Candidate.class);
		List<Candidate> list = new ArrayList<Candidate>();
		list.add(expected);
		list.add(expected);
		int count = list.size();
		List<Candidate> actual = hservice.viewInterviewMemebers();
		assertEquals(count, actual.size());
	}

	// test case for view all Panel Members by type
	@Test
	public void testShouldReturnAllPanelMemberByType() throws NoSuchPanelTypeFound {
		PanelMember expected = context.getBean(PanelMember.class);
		expected.setEmployeeType("HR");
		List<PanelMember> list = new ArrayList<PanelMember>();
		list.add(expected);
		int count = list.size();
		List<PanelMember> actual = hservice.viewPanelMemberByType(expected.getEmployeeType());
		assertEquals(count, actual.size());
	}

	// test case for updating HRrating by interviewId
	@Test
	void testModifyHRRatingShouldReturnInterviewId() {
		InterviewSchedule interviewSchedule = new InterviewSchedule();
		
		int result = administratorService.updateInterviewScheduleOfHrRating(4, "selected");
		interviewSchedule = administratorService.viewInterviewById(4);
		assertEquals(interviewSchedule.getHrRating(), "selected");
	}

	// test case for CandidateNotFoundException
	@Test
	public void testShouldThrowCandidateNotFoundException() {
		@SuppressWarnings("unused")
		CandidateNotFoundException exception = assertThrows(CandidateNotFoundException.class, () -> {
			hservice.viewCandidateById(111);
		});
	}

	// test case for NoSuchPanelIdException
	@Test
	public void testShouldThrowNoSuchPanelIdException() {
		@SuppressWarnings("unused")
		NoSuchPanelIdException exception = assertThrows(NoSuchPanelIdException.class, () -> {
			hservice.viewPanelMemberById(111);
		});
	}

}
