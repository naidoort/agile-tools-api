package com.agiletools.repository;

import com.agiletools.model.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {

    Optional<TeamMember> findByEmail(String email);

    boolean existsByEmail(String email);

    List<TeamMember> findByTeamId(Long teamId);

    List<TeamMember> findByJurisdiction(String jurisdiction);

    @Query("SELECT tm FROM TeamMember tm LEFT JOIN FETCH tm.leaves WHERE tm.id = :id")
    Optional<TeamMember> findByIdWithLeaves(Long id);

    @Query("SELECT tm FROM TeamMember tm LEFT JOIN FETCH tm.leaves WHERE tm.team.id = :teamId")
    List<TeamMember> findByTeamIdWithLeaves(Long teamId);
}