package com.capgemini.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.capgemini.entities.PanelMember;

public interface PanelMemberRepository extends JpaRepository<PanelMember, Integer>
{
	@Query(value ="select P from PanelMember P where P.employeeType = :employeeType")
	public List<PanelMember> readPanelMemberByTechType(@Param("employeeType")String employeeType);

	@Query(value ="select P from PanelMember P where P.employeeType = :employeeType")
	public List<PanelMember> readPanelMemberByHRType(@Param("employeeType")String employeeType);

	@Transactional
	@Modifying
	@Query(value = "Update PanelMember i set i.deleteStatus = :deleteStatus where i.panelId = :panelId")
	public void softDeletePanelMember(@Param("panelId") int panelId, @Param("deleteStatus") boolean deleteStatus);

	@Query(value = "Select i from PanelMember i where i.deleteStatus = false")
	public List<PanelMember> findAllActivePanelMember();
	
	@Query(value = "Select i from PanelMember i where i.deleteStatus = true")
	public List<PanelMember> findAllInactivePanelMember();
}
