package com.agiletools.repository;

import com.agiletools.model.Leave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LeaveRepository extends JpaRepository<Leave, Long> {

    List<Leave> findByTeamMemberId(Long teamMemberId);

    List<Leave> findByTeamMemberTeamId(Long teamId);

    @Query("SELECT l FROM Leave l WHERE l.teamMember.id = :teamMemberId " +
           "AND ((l.startDate <= :endDate AND l.endDate >= :startDate))")
    List<Leave> findOverlappingLeaves(@Param("teamMemberId") Long teamMemberId,
                                     @Param("startDate") LocalDate startDate,
                                     @Param("endDate") LocalDate endDate);

    @Query("SELECT l FROM Leave l WHERE l.teamMember.team.id = :teamId " +
           "AND ((l.startDate <= :endDate AND l.endDate >= :startDate))")
    List<Leave> findTeamLeavesInPeriod(@Param("teamId") Long teamId,
                                      @Param("startDate") LocalDate startDate,
                                      @Param("endDate") LocalDate endDate);

    @Query("SELECT l FROM Leave l WHERE l.leaveType = 'PUBLIC_HOLIDAY' " +
           "AND l.teamMember.jurisdiction = :jurisdiction " +
           "AND ((l.startDate <= :endDate AND l.endDate >= :startDate))")
    List<Leave> findPublicHolidaysInJurisdiction(@Param("jurisdiction") String jurisdiction,
                                                @Param("startDate") LocalDate startDate,
                                                @Param("endDate") LocalDate endDate);
}