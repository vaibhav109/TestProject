package com.capgemini.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.capgemini.entities.Candidate;
import com.capgemini.entities.PanelMember;
import com.capgemini.entities.User;
import com.capgemini.exception.CandidateNotFoundException;
import com.capgemini.exception.NoSuchInterviewIdException;
import com.capgemini.exception.NoSuchPanelIdException;
import com.capgemini.exception.NoSuchPanelTypeFound;
import com.capgemini.repository.CandidateRepository;
import com.capgemini.repository.InterviewScheduleRepository;
import com.capgemini.repository.PanelMemberRepository;
import com.capgemini.repository.UserRepository;

@Service("techservice")
public class TechPanelServiceImpl implements TechPanelService {
	// Reference variable of CandidateRepository
	@Autowired
	private CandidateRepository repository;

	// Reference variable of InterviewScheduleRepository
	@Autowired
	private InterviewScheduleRepository interviewRepository;

	// Reference variable of PanelMemberRepository
	@Autowired
	private PanelMemberRepository panelRepository;

	@Autowired
	private UserRepository repositoryOfLogin;

	// Logger Object created
	private static final Logger LOGGER = LoggerFactory.getLogger(AdministratorServiceImpl.class);

	@Override
	public boolean loginUser(User user) {
		boolean result = false;
		User user2 = repositoryOfLogin.validateUser(user.getUsername(), user.getPassword());
		if (user2 != null) {
			result = true;

		}
		return result;

	}

	@Override
	public User registration(User user) {
		return repositoryOfLogin.save(user);
	}

	@Override
	public List<Candidate> viewInterviewMemebers() throws CandidateNotFoundException {
		LOGGER.trace("Entering viewInterviewMemebers method");
		List<Candidate> candidates = repository.findAll();
		if (candidates.isEmpty()) {
			LOGGER.error("Interview Member Not Found");
			throw new CandidateNotFoundException("No candidates found");
		}
		LOGGER.info("Interview Member Found Successfully");
		return candidates;
	}

	@Override
	@Transactional
	public int updateCandidateTechRating(int interviewId, String techRating) throws NoSuchInterviewIdException {
		LOGGER.trace("Entering updateCandidateTechRating method");
		if (interviewRepository.existsById(interviewId)) {
			LOGGER.info("Tech Rating Updated Successfully");
			return interviewRepository.updateTechRatingInInterviewSchedule(interviewId, techRating);
		}
		LOGGER.error("Scheduled Interview Not Found");
		throw new NoSuchInterviewIdException("No such interview id with  " + interviewId + "not found");
	}

	@Override
	public Candidate viewCandidateById(int candidateId) throws CandidateNotFoundException {
		LOGGER.trace("Entering viewCandidateById method");
		if (repository.existsById(candidateId)) {
			LOGGER.info("Candidate Found Successfully");
			return repository.findById(candidateId).get();
		}
		LOGGER.error("Candidate Not Found");
		throw new CandidateNotFoundException("candidate with id " + candidateId + "not found");
	}

	@Override
	public PanelMember viewPanelMemberById(int panelId) throws NoSuchPanelIdException {
		LOGGER.trace("Entering viewPanelMemberById method");
		if (panelRepository.existsById(panelId)) {
			LOGGER.info("Panel Member Found Successfully");
			return panelRepository.findById(panelId).get();
		}
		LOGGER.error("Panel Member Not Found");
		throw new NoSuchPanelIdException("panel with id " + panelId + " not found");
	}

	@Override
	public List<PanelMember> viewPanelMemberByType(String employeeType) throws NoSuchPanelTypeFound {
		LOGGER.trace("Entering viewPanelMemberByType method");
		LOGGER.info("Panel Member By Type Found Successfully");
		return panelRepository.readPanelMemberByTechType(employeeType);
	}

}