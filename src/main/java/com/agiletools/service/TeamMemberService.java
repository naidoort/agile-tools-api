package com.agiletools.service;

import com.agiletools.dto.TeamMemberDto;
import com.agiletools.model.Team;
import com.agiletools.model.TeamMember;
import com.agiletools.repository.TeamMemberRepository;
import com.agiletools.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TeamMemberService {

    @Autowired
    private TeamMemberRepository teamMemberRepository;

    @Autowired
    private TeamRepository teamRepository;

    public List<TeamMemberDto> getAllMembers() {
        return teamMemberRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<TeamMemberDto> getMembersByTeam(Long teamId) {
        return teamMemberRepository.findByTeamId(teamId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public TeamMemberDto getMemberById(Long id) {
        TeamMember member = teamMemberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Team member not found with id: " + id));
        return convertToDto(member);
    }

    public TeamMemberDto createMember(TeamMemberDto memberDto) {
        if (teamMemberRepository.existsByEmail(memberDto.getEmail())) {
            throw new RuntimeException("Team member with email '" + memberDto.getEmail() + "' already exists");
        }

        Team team = null;
        if (memberDto.getTeamId() != null) {
            team = teamRepository.findById(memberDto.getTeamId())
                    .orElseThrow(() -> new RuntimeException("Team not found with id: " + memberDto.getTeamId()));
        }

        TeamMember member = new TeamMember(
                memberDto.getFirstName(),
                memberDto.getLastName(),
                memberDto.getEmail(),
                memberDto.getJurisdiction()
        );

        if (memberDto.getCapacityPercentage() != null) {
            member.setCapacityPercentage(memberDto.getCapacityPercentage());
        }

        member.setTeam(team);

        TeamMember savedMember = teamMemberRepository.save(member);
        return convertToDto(savedMember);
    }

    public TeamMemberDto updateMember(Long id, TeamMemberDto memberDto) {
        TeamMember member = teamMemberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Team member not found with id: " + id));

        if (!member.getEmail().equals(memberDto.getEmail()) && teamMemberRepository.existsByEmail(memberDto.getEmail())) {
            throw new RuntimeException("Team member with email '" + memberDto.getEmail() + "' already exists");
        }

        member.setFirstName(memberDto.getFirstName());
        member.setLastName(memberDto.getLastName());
        member.setEmail(memberDto.getEmail());
        member.setJurisdiction(memberDto.getJurisdiction());

        if (memberDto.getCapacityPercentage() != null) {
            member.setCapacityPercentage(memberDto.getCapacityPercentage());
        }

        if (memberDto.getTeamId() != null) {
            Team team = teamRepository.findById(memberDto.getTeamId())
                    .orElseThrow(() -> new RuntimeException("Team not found with id: " + memberDto.getTeamId()));
            member.setTeam(team);
        }

        TeamMember updatedMember = teamMemberRepository.save(member);
        return convertToDto(updatedMember);
    }

    public void deleteMember(Long id) {
        if (!teamMemberRepository.existsById(id)) {
            throw new RuntimeException("Team member not found with id: " + id);
        }
        teamMemberRepository.deleteById(id);
    }

    private TeamMemberDto convertToDto(TeamMember member) {
        TeamMemberDto dto = new TeamMemberDto();
        dto.setId(member.getId());
        dto.setFirstName(member.getFirstName());
        dto.setLastName(member.getLastName());
        dto.setEmail(member.getEmail());
        dto.setJurisdiction(member.getJurisdiction());
        dto.setCapacityPercentage(member.getCapacityPercentage());
        dto.setCreatedAt(member.getCreatedAt());
        dto.setUpdatedAt(member.getUpdatedAt());

        if (member.getTeam() != null) {
            dto.setTeamId(member.getTeam().getId());
            dto.setTeamName(member.getTeam().getName());
        }

        return dto;
    }
}