package com.capgemini.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("candidate")
@Scope(scopeName="prototype")
@Entity
@Table(name="CANDIDATE") //Explicitly specifying the name of Table
public class Candidate 
{       
	    //Primary Key for CANDIDATE is CANDIDATE_ID
	    @Id
	    @Column(name="CANDIDATE_ID")
	    @GeneratedValue(strategy=GenerationType.IDENTITY )
		private Integer candidateId;
	    
	    @Column(name="CANDIDATE_NAME", length=50)
	    @NotEmpty(message="Please Enter Candidate Name")
	    @Pattern(regexp="^[a-zA-Z]+$",message="Please Enter Valid Name.")
		private String candidateName;
	    
	    @Column(name="PRIMARY_SKILL", length=30, nullable=false)
		private String primarySkill;
	    
	    @Column(name="SECONDARY_SKILL", length=30)
		private String secondarySkill;
	    
	    @Column(name="EXPERIENCE", length=20)
		private String experience;
	    
	    @Column(name="QUALIFICATION", length=50, nullable=false)
	    @Pattern(regexp="^[a-zA-Z]+$",message="Please Enter Valid Qualification.")
		private String qualification;
	    
	    @Column(name="DESIGNATION", length=30)
	    @Pattern(regexp="^[a-zA-Z]+$",message="Please Enter Valid Designation.")
		private String designation;
	    
	    @Column(name="NOTICE_PERIOD", length=30)
		private String noticePeriod;
	    
	    @Column(name="LOCATION", length=50)
	    @Pattern(regexp="^[a-zA-Z]+$",message="Please Enter Valid Location.")
		private String location;
	    
	    @Column(name="EMAIL", length=30)
	    private String email;
	    
	    public Candidate()
	    {
	    	
	    }

		public Candidate(Integer candidateId,
				@NotEmpty(message = "Please Enter Candidate Name") @Pattern(regexp = "^[a-zA-Z]+$", message = "Please Enter Valid Name.") String candidateName,
				String primarySkill, String secondarySkill, String experience,
				@Pattern(regexp = "^[a-zA-Z]+$", message = "Please Enter Valid Qualification.") String qualification,
				@Pattern(regexp = "^[a-zA-Z]+$", message = "Please Enter Valid Designation.") String designation,
				String noticePeriod,
				@Pattern(regexp = "^[a-zA-Z]+$", message = "Please Enter Valid Location.") String location,
				String email) {
			super();
			this.candidateId = candidateId;
			this.candidateName = candidateName;
			this.primarySkill = primarySkill;
			this.secondarySkill = secondarySkill;
			this.experience = experience;
			this.qualification = qualification;
			this.designation = designation;
			this.noticePeriod = noticePeriod;
			this.location = location;
			this.email = email;
		}

		public Integer getCandidateId() {
			return candidateId;
		}

		public void setCandidateId(Integer candidateId) {
			this.candidateId = candidateId;
		}

		public String getCandidateName() {
			return candidateName;
		}

		public void setCandidateName(String candidateName) {
			this.candidateName = candidateName;
		}

		public String getPrimarySkill() {
			return primarySkill;
		}

		public void setPrimarySkill(String primarySkill) {
			this.primarySkill = primarySkill;
		}

		public String getSecondarySkill() {
			return secondarySkill;
		}

		public void setSecondarySkill(String secondarySkill) {
			this.secondarySkill = secondarySkill;
		}

		public String getExperience() {
			return experience;
		}

		public void setExperience(String experience) {
			this.experience = experience;
		}

		public String getQualification() {
			return qualification;
		}

		public void setQualification(String qualification) {
			this.qualification = qualification;
		}

		public String getDesignation() {
			return designation;
		}

		public void setDesignation(String designation) {
			this.designation = designation;
		}

		public String getNoticePeriod() {
			return noticePeriod;
		}

		public void setNoticePeriod(String noticePeriod) {
			this.noticePeriod = noticePeriod;
		}

		public String getLocation() {
			return location;
		}

		public void setLocation(String location) {
			this.location = location;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		@Override
		public String toString() {
			return "Candidate [candidateId=" + candidateId + ", candidateName=" + candidateName + ", primarySkill="
					+ primarySkill + ", secondarySkill=" + secondarySkill + ", experience=" + experience
					+ ", qualification=" + qualification + ", designation=" + designation + ", noticePeriod="
					+ noticePeriod + ", location=" + location + ", email=" + email + "]";
		}

			   
		
}
