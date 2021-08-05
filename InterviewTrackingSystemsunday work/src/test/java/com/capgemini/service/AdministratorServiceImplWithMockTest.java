package com.capgemini.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;

import com.capgemini.entities.Candidate;
import com.capgemini.entities.Employee;
import com.capgemini.entities.InterviewSchedule;
import com.capgemini.entities.PanelMember;
import com.capgemini.exception.EmployeeByIdNotFoundException;
import com.capgemini.exception.EmployeeNotFoundException;
import com.capgemini.exception.NoSuchCandidateException;
import com.capgemini.exception.NoSuchInterviewScheduleException;
import com.capgemini.exception.NoSuchPanelIdException;
import com.capgemini.repository.CandidateRepository;
import com.capgemini.repository.EmployeeRepository;
import com.capgemini.repository.InterviewScheduleRepository;
import com.capgemini.repository.PanelMemberRepository;
import com.capgemini.services.AdministratorService;

@SpringBootTest
class AdministratorServiceImplWithMockTest {
	@Autowired
	private AdministratorService administratorService;

	@Autowired
	private ApplicationContext context;

	@MockBean
	private CandidateRepository repositoryOfCandidate;

	@MockBean
	private InterviewScheduleRepository repositoryOfInterviewSchedule;

	@MockBean
	private EmployeeRepository repositoryOfEmployee;

	@MockBean
	private PanelMemberRepository repositoryOfPanelMember;

	@Test
	void testFindCandidateByIdShouldReturnCandidate() throws NoSuchCandidateException {

		Candidate expected = context.getBean(Candidate.class);

		expected.setCandidateId(111);
		expected.setCandidateName("Test");
		expected.setPrimarySkill("Test");
		expected.setSecondarySkill("Test");
		expected.setExperience("Test");
		expected.setQualification("Test");
		expected.setDesignation("Test");
		expected.setNoticePeriod("Test");
		expected.setLocation("Test");
		expected.setEmail("Test");

		// setting up expectation when you call methods testing
		when(repositoryOfCandidate.existsById(expected.getCandidateId())).thenReturn(true);
		Optional<Candidate> expectation = Optional.of(expected);
		when(repositoryOfCandidate.findById(expected.getCandidateId())).thenReturn(expectation);

		// actual method call which we are
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
		expected.setEmployeeType("Tech");
		expected.setLocation("VIR");
		expected.setDeleteStatus(false);
		expected.getEmployee().setEmployeeId(1);
		expected.getCandidate().setCandidateId(1);

		// setting up expectation when you call methods testing
		when(repositoryOfPanelMember.existsById(expected.getPanelId())).thenReturn(true);
		Optional<PanelMember> expectation = Optional.of(expected);
		when(repositoryOfPanelMember.findById(expected.getPanelId())).thenReturn(expectation);

		// actual method call which we are

		PanelMember actual = administratorService.viewPanelMemberById(expected.getPanelId());

		assertEquals(expected.getPanelId(), actual.getPanelId());
		assertEquals(expected.getEmployeeType(), actual.getEmployeeType());
		assertEquals(expected.getLocation(), actual.getLocation());
		assertEquals(expected.getDeleteStatus(), actual.getDeleteStatus());
		assertEquals(expected.getEmployee().getEmployeeId(), actual.getEmployee().getEmployeeId());
		assertEquals(expected.getCandidate().getCandidateId(), actual.getCandidate().getCandidateId());
	}

	@Test
	void testFindScheduleInterviewShouldReturnInterviewSchedule() throws NoSuchInterviewScheduleException {

		// 1,'ok','3','12/10/2021','11am','4',1,1

		InterviewSchedule expected = context.getBean(InterviewSchedule.class);

		expected.setInterviewId(1);
		expected.setFinalStatus("Test");
		expected.setTechRating("Test");
		expected.setInterviewDate("Test");
		expected.setInterviewTime("Test");
		expected.setHrRating("Test");
		expected.getCandidate().setCandidateId(4);
		expected.getPanelMember().setPanelId(5);

		// setting up expectation when you call methods testing
		when(repositoryOfInterviewSchedule.existsById(expected.getInterviewId())).thenReturn(true);
		Optional<InterviewSchedule> expectation = Optional.of(expected);
		when(repositoryOfInterviewSchedule.findById(expected.getInterviewId())).thenReturn(expectation);

		// actual method call which we are
		InterviewSchedule actual = administratorService.viewInterviewById(expected.getInterviewId());

		assertEquals(expected.getInterviewId(), actual.getInterviewId());
		assertEquals(expected.getFinalStatus(), actual.getFinalStatus());
		assertEquals(expected.getTechRating(), actual.getTechRating());
		assertEquals(expected.getInterviewDate(), actual.getInterviewDate());
		assertEquals(expected.getInterviewTime(), actual.getInterviewTime());
		assertEquals(expected.getHrRating(), actual.getHrRating());
		assertEquals(expected.getCandidate().getCandidateId(), actual.getCandidate().getCandidateId());
		assertEquals(expected.getPanelMember().getPanelId(), actual.getPanelMember().getPanelId());
	}

	@Test
	void testFindCandidateShouldThrowsNoSuchCandidateException() throws NoSuchCandidateException {
		int candidateId = 345;
		when(repositoryOfCandidate.existsById(candidateId)).thenReturn(false);
		assertThrows(NoSuchCandidateException.class, () -> administratorService.viewCandidateById(candidateId));
	}

	// test case for Search Employee By ID
	// 1, 'Test', 'Test', 'Test'
	@Test
	void testsearchEmployeeByIdShouldReturnEmployee() throws EmployeeByIdNotFoundException {
		Employee expected = context.getBean(Employee.class);
		expected.setEmployeeId(1);
		expected.setEmployeeName("Test");
		expected.setDepartment("Test");
		expected.setDesignation("Test");

		// setting up expectation when you call methods testing
		when(repositoryOfEmployee.existsById(expected.getEmployeeId())).thenReturn(true);
		Optional<Employee> expectation = Optional.of(expected);
		when(repositoryOfEmployee.findById(expected.getEmployeeId())).thenReturn(expectation);

		// actual method call which we are
		Employee actual = administratorService.searchEmployeeById(expected.getEmployeeId());

		assertEquals(expected.getEmployeeId(), actual.getEmployeeId());
		assertEquals(expected.getEmployeeName(), actual.getEmployeeName());
		assertEquals(expected.getDepartment(), actual.getDepartment());
		assertEquals(expected.getDesignation(), actual.getDesignation());

	}

	// test case for search Employee By Name
	@Test
	public void testsearchEmployeeByNameShouldReturnAllEmployee() {
		Employee expected = context.getBean(Employee.class);
		expected.setEmployeeId(1);
		expected.setEmployeeName("Test");
		expected.setDepartment("Test");
		expected.setDesignation("Test");

		List<Employee> list = new ArrayList<Employee>();
		list.add(expected);

		when(repositoryOfEmployee.findAll()).thenReturn(list);

		assertEquals(1, list.size());

	}

	@Test
	public void testlistAllPanelMemberShouldReturnAllPanelMember() {
		PanelMember expected = context.getBean(PanelMember.class);

		expected.setPanelId(1);
		expected.setEmployeeType("Tech");
		expected.setLocation("VIR");
		expected.setDeleteStatus(false);
		expected.getEmployee().setEmployeeId(1);
		expected.getCandidate().setCandidateId(1);

		List<PanelMember> list = new ArrayList<PanelMember>();
		list.add(expected);

		when(repositoryOfPanelMember.findAll()).thenReturn(list);

		assertEquals(1, list.size());

	}

	// test case for EmployeeByIdNotFound Exception
	@Test
	void testShouldThrowEmployeeByIdNotFoundException() {
		int EmployeeId = -1;
		when(repositoryOfEmployee.existsById(EmployeeId)).thenReturn(false);
		assertThrows(EmployeeByIdNotFoundException.class, () -> administratorService.searchEmployeeById(-1));
	}

	@Test
	void testNoSuchPanelIdException() {
		int PanelId = -1;
		when(repositoryOfPanelMember.existsById(PanelId)).thenReturn(false);
		assertThrows(NoSuchPanelIdException.class, () -> administratorService.viewPanelMemberById(-1));
	}

	// test case for EmployeeNotFound Exception
	@Test
	void testShouldThrowEmployeeNotFoundException() {
		String EmployeeName = "null";
		when(repositoryOfEmployee.findAll()).thenReturn(null);
		assertThrows(EmployeeNotFoundException.class, () -> administratorService.searchEmployeeByName(EmployeeName));
	}

}
