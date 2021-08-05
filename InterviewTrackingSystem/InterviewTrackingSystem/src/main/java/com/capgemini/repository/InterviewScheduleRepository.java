package com.capgemini.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.capgemini.entities.InterviewSchedule;

public interface InterviewScheduleRepository extends JpaRepository<InterviewSchedule, Integer> 
{
	@Transactional
	@Modifying
	@Query(value = "Update InterviewSchedule i set i.techRating = :techRating where i.interviewId = :interviewId")
	int updateTechRatingInInterviewSchedule(@Param("interviewId") int interviewId,
			@Param("techRating") String techRating);

	@Transactional
	@Modifying
	@Query(value = "Update InterviewSchedule i set i.hrRating = :hrRating where i.interviewId= :interviewId")
	int updateHRRatingInInterviewSchedule(@Param("interviewId") int interviewId, @Param("hrRating") String hrRating);

	@Transactional
	@Modifying
	@Query(value = "Update InterviewSchedule i set i.finalStatus = :finalStatus where i.interviewId = :interviewId")
	int updateFinalStatusInInterviewSchedule(@Param("interviewId") int interviewId,
			@Param("finalStatus") String finalStatus);

	@Transactional
	@Modifying
	@Query(value = "Update InterviewSchedule i set i.deleteStatus = :deleteStatus where i.interviewId = :interviewId")
	public void softDeleteOfInterviewSchedule(@Param("interviewId") int interviewId,
			@Param("deleteStatus") boolean deleteStatus);

	@Query(value = "Select i from InterviewSchedule i where i.deleteStatus = false")
	public List<InterviewSchedule> findAllActiveSchedule();

	@Query(value = "Select i from InterviewSchedule i where i.deleteStatus = true")
	public List<InterviewSchedule> findAllInactiveSchedule();
	
	@Query(value="select i from InterviewSchedule i where i.techRating =:techRating")
	public List<InterviewSchedule> findAllCandidateByTechRating(@Param("techRating") String techRating);

}
