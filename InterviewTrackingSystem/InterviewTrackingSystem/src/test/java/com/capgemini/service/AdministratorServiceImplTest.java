package com.capgemini.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import com.capgemini.entities.Candidate;
import com.capgemini.entities.Employee;
import com.capgemini.entities.InterviewSchedule;
import com.capgemini.entities.PanelMember;
import com.capgemini.exception.CandidateNotFoundException;
import com.capgemini.exception.EmployeeByIdNotFoundException;
import com.capgemini.exception.NoSuchCandidateException;
import com.capgemini.exception.NoSuchInterviewScheduleException;
import com.capgemini.exception.NoSuchPanelIdException;
import com.capgemini.services.AdministratorService;

@SpringBootTest
class AdministratorServiceImplTest {
	@Autowired
	private AdministratorService administratorService;

	@Autowired
	private ApplicationContext context;

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

		Candidate actual = administratorService.viewCandidateById(expected.getCandidateId());

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

	@Test
	void testFindPanelMember() throws NoSuchPanelIdException {
		PanelMember expected = context.getBean(PanelMember.class);

		expected.setPanelId(1);
		;
		expected.setEmployeeType("Tech");
		expected.setLocation("VIR");
		expected.setDeleteStatus(false);
		expected.getEmployee().setEmployeeId(1);
		expected.getCandidate().setCandidateId(1);

		administratorService.addPanelMember(expected);

		PanelMember actual = administratorService.viewPanelMemberById(expected.getPanelId());

		assertEquals(expected.getPanelId(), actual.getPanelId());
		assertEquals(expected.getEmployeeType(), actual.getEmployeeType());
		assertEquals(expected.getLocation(), actual.getLocation());
		assertEquals(expected.getDeleteStatus(), actual.getDeleteStatus());
		assertEquals(expected.getEmployee().getEmployeeId(), actual.getEmployee().getEmployeeId());
		assertEquals(expected.getCandidate().getCandidateId(), actual.getCandidate().getCandidateId());
	}

	@Test
	void testCancelPanelMember() throws NoSuchPanelIdException {
		PanelMember panelMember = new PanelMember(1, "Tech", "VIR", false);
		boolean result = administratorService.deletePanelMember(1, true);
		panelMember = administratorService.viewPanelMemberById(1);
		assertEquals(panelMember.getDeleteStatus(), true);
	}

	@Test
	void testFindScheduleInterviewShouldReturnInterviewSchedule() throws NoSuchInterviewScheduleException {

		InterviewSchedule expected = context.getBean(InterviewSchedule.class);

		expected.setInterviewId(1);
		expected.setFinalStatus("pending");
		expected.setTechRating("rate");
		expected.setInterviewDate("21/06/2021");
		expected.setInterviewTime("3pm");
		expected.setHrRating("rate");
		expected.setDeleteStatus(false);
		expected.getCandidate().setCandidateId(2);
		expected.getPanelMember().setPanelId(1);

		administratorService.scheduleInterview(expected);

		InterviewSchedule actual = administratorService.viewInterviewById(expected.getInterviewId());

		assertEquals(expected.getInterviewId(), actual.getInterviewId());
		assertEquals(expected.getFinalStatus(), actual.getFinalStatus());
		assertEquals(expected.getTechRating(), actual.getTechRating());
		assertEquals(expected.getInterviewDate(), actual.getInterviewDate());
		assertEquals(expected.getInterviewTime(), actual.getInterviewTime());
		assertEquals(expected.getHrRating(), actual.getHrRating());
		assertEquals(expected.getDeleteStatus(), actual.getDeleteStatus());
		assertEquals(expected.getCandidate().getCandidateId(), actual.getCandidate().getCandidateId());
		assertEquals(expected.getPanelMember().getPanelId(), actual.getPanelMember().getPanelId());

	}

	@Test
	void testModifyTechRatingShouldReturnInterviewId() {
		InterviewSchedule interviewSchedule = new InterviewSchedule(1, "rate", "rate", "pending", "21/06/2021", "3pm",
				false);

		int result = administratorService.updateInterviewScheduleOfTechRatig(1, "5");
		interviewSchedule = administratorService.viewInterviewById(1);
		assertEquals(interviewSchedule.getTechRating(), "5");

	}

	@Test
	void testModifyHrRatingShouldReturnInterviewId() {
		InterviewSchedule interviewSchedule = new InterviewSchedule(1, "rate", "rate", "pending", "21/06/2021", "3pm",
				false);
		int result = administratorService.updateInterviewScheduleOfHrRating(1, "6");
		interviewSchedule = administratorService.viewInterviewById(1);
		assertEquals("6", interviewSchedule.getHrRating());
	}

	@Test
	void testModifyFinalStatusShouldReturnInterviewId() {
		InterviewSchedule interviewSchedule = new InterviewSchedule(1, "rate", "rate", "pending", "21/06/2021", "3pm",
				false);
		int result = administratorService.updateInterviewScheduleOfFinalStatus(1, "Selected");
		interviewSchedule = administratorService.viewInterviewById(1);
		assertEquals("Selected", interviewSchedule.getFinalStatus());
	}

	@Test
	void testCancelInterviewShouldReturnInterviewId() {
		InterviewSchedule interviewSchedule = new InterviewSchedule(1, "5", "6", "ok", "12/10/2020", "1pm", false);
		boolean result = administratorService.cancelInterview(1, true);
		interviewSchedule = administratorService.viewInterviewById(1);
		assertEquals(interviewSchedule.getDeleteStatus(), true);
	}

	//
	@Test
	void testShouldThrowNoSuchCandidateException() {
		NoSuchCandidateException exception = assertThrows(NoSuchCandidateException.class, () -> {
			administratorService.viewCandidateById(340);
		});
	}

	//
	@Test
	void testShouldThrowNoSuchIntervieweScheduleException() {
		NoSuchInterviewScheduleException exception = assertThrows(NoSuchInterviewScheduleException.class, () -> {
			administratorService.viewInterviewById(234);
		});
	}

	// test case for Search Employee By ID and Add Employee method

	@Test
	void testsearchEmployeeByIdShouldReturnEmployee() throws EmployeeByIdNotFoundException {
		Employee expected = context.getBean(Employee.class);
		expected.setEmployeeId(1);
		expected.setEmployeeName("Makarand");
		expected.setDepartment("Tech");
		expected.setDesignation("SoftwareDeveloper");

		administratorService.addEmployee(expected);

		Employee actual = administratorService.searchEmployeeById(expected.getEmployeeId());

		assertEquals(expected.getEmployeeId(), actual.getEmployeeId());
		assertEquals(expected.getEmployeeName(), actual.getEmployeeName());
		assertEquals(expected.getDepartment(), actual.getDepartment());
		assertEquals(expected.getDesignation(), actual.getDesignation());
	}

	@Test
	public void testShouldThrowEmployeeByIdNotFoundException() {
		Employee expected = context.getBean(Employee.class);
		EmployeeByIdNotFoundException exception = assertThrows(EmployeeByIdNotFoundException.class, () -> {
			administratorService.searchEmployeeById(expected.getEmployeeId());
		});
	}

}
