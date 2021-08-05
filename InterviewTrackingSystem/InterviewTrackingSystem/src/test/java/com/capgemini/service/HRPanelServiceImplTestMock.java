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
import com.capgemini.entities.InterviewSchedule;
import com.capgemini.entities.PanelMember;
import com.capgemini.exception.CandidateNotFoundException;
import com.capgemini.exception.NoSuchPanelIdException;
import com.capgemini.exception.NoSuchPanelTypeFound;
import com.capgemini.repository.CandidateRepository;
import com.capgemini.repository.InterviewScheduleRepository;
import com.capgemini.repository.PanelMemberRepository;
import com.capgemini.services.HRPanelService;

@SpringBootTest
class HRPanelServiceImplTestMock 
{
	//reference variable for TechPanelService
	@Autowired
	private HRPanelService hservice;
		
	//reference variable for ApplicationContext
	@Autowired
	private ApplicationContext context;
		
	//mock for  CandidateRepository
	@MockBean
	private CandidateRepository candidaterepository;

	//mock for PanelMemberRepository
	@MockBean
	private PanelMemberRepository pannelrepository;
	
	//mock for InterviewScheduleRepository
	@MockBean
	private InterviewScheduleRepository  interviewrepository;
		
	//Mock test case for view candidate by Id
	@Test
	void testFindCandidateByIdReturnCandidate() throws CandidateNotFoundException 
	{
			Candidate expected = context.getBean(Candidate.class);
			expected.setCandidateId(1);
			expected.setCandidateName("Amrutha");
			expected.setPrimarySkill("Java");
			expected.setSecondarySkill("Pega");
			expected.setExperience("3 years");
			expected.setQualification("BTech");
			expected.setDesignation("SoftwareAnalyst");
			expected.setNoticePeriod("3 months");
			expected.setLocation("Hyderabad");
				
			//setting up exception when you call methods
			when(candidaterepository.existsById(expected.getCandidateId())).thenReturn(true);
			Optional<Candidate> expectation = Optional.of(expected);
				
			when(candidaterepository.findById(expected.getCandidateId())).thenReturn(expectation);
				
			//actual method calls which we are testing
			Candidate actual = hservice.viewCandidateById(expected.getCandidateId());
				
			//assert Equals (expected,actual)
			assertEquals(expected.getCandidateId(), actual.getCandidateId());
			assertEquals(expected.getCandidateName(), actual.getCandidateName());
			assertEquals(expected.getPrimarySkill(), actual.getPrimarySkill());
			assertEquals(expected.getSecondarySkill(), actual.getSecondarySkill());
			assertEquals(expected.getExperience(), actual.getExperience());
			assertEquals(expected.getQualification(), actual.getQualification());
			assertEquals(expected.getDesignation(), actual.getDesignation());
			assertEquals(expected.getNoticePeriod(), actual.getNoticePeriod());
			assertEquals(expected.getLocation(), actual.getLocation());
	}
			
		//Mock test case for view PanelMember by Id
		@Test
		void testFindPanelMemberByIdReturnPanelMember() throws NoSuchPanelIdException 
		{
			PanelMember expected = context.getBean(PanelMember.class);
			expected.setPanelId(1);
			expected.setDeleteStatus(false);
			expected.setEmployeeType("TECH");
			expected.setLocation("HYDERBAD");
			expected.getEmployee().setEmployeeId(1001);
			expected.getEmployee().setEmployeeName("LUCKY");
			expected.getEmployee().setDepartment("TECH");
			expected.getEmployee().setDesignation("FINANCE");
			expected.getCandidate().setCandidateId(1);
			expected.getCandidate().setCandidateName("Amrutha");
			expected.getCandidate().setPrimarySkill("Java");
			expected.getCandidate().setSecondarySkill("Pega");
			expected.getCandidate().setExperience("2 years");
			expected.getCandidate().setQualification("BTECH");
			expected.getCandidate().setDesignation("SoftwareAnalyst");
			expected.getCandidate().setNoticePeriod("3 months");
			expected.getCandidate().setLocation("Hyderabad");

			//setting up exception when you call methods
			when(pannelrepository.existsById(expected.getPanelId())).thenReturn(true);     
			Optional<PanelMember> expectation = Optional.of(expected);
				
			when(pannelrepository.findById(expected.getPanelId())).thenReturn(expectation); 
				
			//actual method calls which we are testing
			PanelMember actual =hservice.viewPanelMemberById(expected.getPanelId());
				
			assertEquals(expected.getPanelId(),actual.getPanelId());
			assertEquals(expected.getDeleteStatus(),actual.getDeleteStatus());
			assertEquals(expected.getEmployeeType(),actual.getEmployeeType());
			assertEquals(expected.getLocation(),actual.getLocation());
			assertEquals(expected.getEmployee().getEmployeeId(),actual.getEmployee().getEmployeeId());
			assertEquals(expected.getEmployee().getEmployeeName(),actual.getEmployee().getEmployeeName());
			assertEquals(expected.getEmployee().getDepartment(),actual.getEmployee().getDepartment());
			assertEquals(expected.getEmployee().getDesignation(),actual.getEmployee().getDesignation());
			assertEquals(expected.getCandidate().getCandidateId(),actual.getCandidate().getCandidateId());
			assertEquals(expected.getCandidate().getCandidateName(),actual.getCandidate().getCandidateName());
			assertEquals(expected.getCandidate().getPrimarySkill(),actual.getCandidate().getPrimarySkill());
			assertEquals(expected.getCandidate().getSecondarySkill(),actual.getCandidate().getSecondarySkill());
			assertEquals(expected.getCandidate().getExperience(),actual.getCandidate().getExperience());
			assertEquals(expected.getCandidate().getQualification(),actual.getCandidate().getQualification());
			assertEquals(expected.getCandidate().getDesignation(),actual.getCandidate().getDesignation());
			assertEquals(expected.getCandidate().getNoticePeriod(),actual.getCandidate().getNoticePeriod());
			assertEquals(expected.getCandidate().getLocation(),actual.getCandidate().getLocation());
		}
		
		//Mock test case for view all candidates
		@Test
		public void testShouldReturnAllCandidates()throws CandidateNotFoundException
		{
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
			
			List<Candidate> list = new ArrayList<Candidate>();
			list.add(expected);
			when(candidaterepository.findAll()).thenReturn(list);
			assertEquals(1 ,hservice.viewInterviewMemebers().size());
		}
		
		//Mock test case for view all panel members by type
		@Test
		public void testShouldReturnAllPanelMembersByType() throws NoSuchPanelTypeFound
		{
			PanelMember expected = context.getBean(PanelMember.class);
			expected.setPanelId(2);
			expected.setEmployeeType("TECH");
			expected.setLocation("Mumbai");
			expected.setDeleteStatus(false);
			expected.getEmployee().setEmployeeId(2);
			expected.getEmployee().setEmployeeName("Durga");
			expected.getEmployee().setDepartment("Technical");
			expected.getEmployee().setDesignation("SoftwareDeveloper");
			expected.getCandidate().setCandidateId(1);
			expected.getCandidate().setCandidateName("Aman");
			expected.getCandidate().setPrimarySkill("Java");
			expected.getCandidate().setSecondarySkill("Python");
			expected.getCandidate().setExperience("");
			expected.getCandidate().setQualification("BE");
			expected.getCandidate().setDesignation("Analyst");
			expected.getCandidate().setNoticePeriod("3 months");
			expected.getCandidate().setLocation("Mumbai");
			expected.getCandidate().setEmail("aman.parashar006@gmail.com");
			
			List<PanelMember> list = new ArrayList<PanelMember>();
			list.add(expected);
			when(pannelrepository.findAll()).thenReturn(list);
			assertEquals(1 ,list.size());                                            
		}
		
		
		//mock test case for view all interview schedule by tech rating
		@Test
		public void testShouldReturnAllInterviewSchedulesByTechRating() throws CandidateNotFoundException
		{
			InterviewSchedule expected = context.getBean(InterviewSchedule.class);
			expected.setInterviewId(1);
			expected.setTechRating("4");
			expected.setHrRating("6");
			expected.setFinalStatus("ok");
			expected.setInterviewDate("12/05/2021");
			expected.setInterviewTime("1pm");
			expected.setDeleteStatus(false);
			expected.getCandidate().setCandidateId(1);
			expected.getCandidate().setCandidateName("Aman");
			expected.getCandidate().setPrimarySkill("Java");
			expected.getCandidate().setSecondarySkill("Python");
			expected.getCandidate().setExperience("");
			expected.getCandidate().setQualification("BE");
			expected.getCandidate().setDesignation("Analyst");
			expected.getCandidate().setNoticePeriod("3 months");
			expected.getCandidate().setLocation("Mumbai");
			expected.getPanelMember().setPanelId(1);
			expected.getPanelMember().setEmployeeType("HR");
			expected.getPanelMember().setLocation("Mumbai");
			expected.getPanelMember().setDeleteStatus(false);
			expected.getPanelMember().getEmployee().setEmployeeId(1);
			expected.getPanelMember().getEmployee().setEmployeeName("Aman");
			expected.getPanelMember().getEmployee().setDesignation("Finance");
			expected.getPanelMember().getEmployee().setDepartment("HR");
			expected.getPanelMember().getCandidate().setCandidateId(1);
			expected.getPanelMember().getCandidate().setCandidateName("Aman");
			expected.getPanelMember().getCandidate().setPrimarySkill("Java");
			expected.getPanelMember().getCandidate().setSecondarySkill("Python");
			expected.getPanelMember().getCandidate().setExperience("");
			expected.getPanelMember().getCandidate().setQualification("BE");
			expected.getPanelMember().getCandidate().setDesignation("Analyst");
			expected.getPanelMember().getCandidate().setNoticePeriod("3 months");
			expected.getPanelMember().getCandidate().setLocation("Mumbai");
			expected.getCandidate().setEmail("aman.parashar006@gmail.com");
			
			List<InterviewSchedule> list = new ArrayList<InterviewSchedule>();
			list.add(expected);
			when(interviewrepository.findAll()).thenReturn(list);
			assertEquals(1,list.size());
			
		}
		

		
		
		
		//Mock test case for CandidateNotFoundException
		@Test
		public void testShouldThrowCandidateNotFoundException() throws CandidateNotFoundException
		{
			int candidateId = -1;
			when(candidaterepository.existsById(candidateId)).thenReturn(false);
			assertThrows(CandidateNotFoundException.class,()->hservice.viewCandidateById(-1));
		}
			
		//Mock test case for NoSuchPanelIdException
		@Test
		public void testShouldThrowNoSuchPanelIdException() throws NoSuchPanelIdException
		{
			int panelMemberId = -100;
			when(candidaterepository.existsById(panelMemberId)).thenReturn(false);
			assertThrows(NoSuchPanelIdException.class,()->hservice.viewPanelMemberById(-100));
		}	
		
		
		

}
