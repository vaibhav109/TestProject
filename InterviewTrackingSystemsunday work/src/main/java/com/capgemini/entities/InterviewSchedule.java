package com.capgemini.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("interviewSchedule")
@Scope(scopeName = "prototype")
@Entity
@Table(name = "INTERVIEW_SCHEDULE") // Explicitly setting up name of the table
public class InterviewSchedule 
{
	// Primary Key for INTERVIEW_SCHEDULE is INTERVIEW_ID
	@Id
	@Column(name = "INTERVIEW_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int interviewId;

	@Column(name = "TECH_RATING", length = 10, nullable = false)
	private String techRating;

	@Column(name = "HR_RATING", length = 10, nullable = false)
	private String hrRating;

	@Column(name = "FINAL_STATUS", length = 50, nullable = false)
	private String finalStatus;

	@Column(name = "INTERVIEW_DATE", length = 50, nullable = false)
	private String interviewDate;

	@Column(name = "INTERVIEW_TIME", length = 10, nullable = false)
	private String interviewTime;

	@Column(name = "DELETE_STATUS", length = 10)
	private boolean deleteStatus = Boolean.FALSE;

	// OWNER Class
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "CANDIDATE_ID")
	@Autowired
	private Candidate candidate;

	// OWNER Class
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "PANEL_ID")
	@Autowired
	private PanelMember panelMember;

	public PanelMember getPanelMember() {
		return panelMember;
	}

	public void setPanelMember(PanelMember panelMember) {
		this.panelMember = panelMember;
	}

	public Candidate getCandidate() {
		return candidate;
	}

	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
	}

	public InterviewSchedule() {

	}

	public InterviewSchedule(int interviewId, String techRating, String hrRating, String finalStatus,
			String interviewDate, String interviewTime, boolean deleteStatus) {
		super();
		this.interviewId = interviewId;
		this.techRating = techRating;
		this.hrRating = hrRating;
		this.finalStatus = finalStatus;
		this.interviewDate = interviewDate;
		this.interviewTime = interviewTime;
		this.deleteStatus = deleteStatus;
	}

	public int getInterviewId() {
		return interviewId;
	}

	public void setInterviewId(int interviewId) {
		this.interviewId = interviewId;
	}

	public String getTechRating() {
		return techRating;
	}

	public void setTechRating(String techRating) {
		this.techRating = techRating;
	}

	public String getHrRating() {
		return hrRating;
	}

	public void setHrRating(String hrRating) {
		this.hrRating = hrRating;
	}

	public String getFinalStatus() {
		return finalStatus;
	}

	public void setFinalStatus(String finalStatus) {
		this.finalStatus = finalStatus;
	}

	public String getInterviewDate() {
		return interviewDate;
	}

	public void setInterviewDate(String interviewDate) {
		this.interviewDate = interviewDate;
	}

	public String getInterviewTime() {
		return interviewTime;
	}

	public void setInterviewTime(String interviewTime) {
		this.interviewTime = interviewTime;
	}

	public boolean getDeleteStatus() {
		return deleteStatus;
	}

	public void setDeleteStatus(boolean deleteStatus) {
		this.deleteStatus = deleteStatus;
	}

	@Override
	public String toString() {
		return "InterviewSchedule [interviewId=" + interviewId + ", techRating=" + techRating + ", hrRating=" + hrRating
				+ ", finalStatus=" + finalStatus + ", interviewDate=" + interviewDate + ", interviewTime="
				+ interviewTime + ", deleteStatus=" + deleteStatus + "]";
	}
}
	