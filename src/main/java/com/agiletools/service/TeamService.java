package com.agiletools.service;

import com.agiletools.dto.TeamDto;
import com.agiletools.dto.TeamMemberDto;
import com.agiletools.model.Team;
import com.agiletools.model.TeamMember;
import com.agiletools.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    public List<TeamDto> getAllTeams() {
        return teamRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public TeamDto getTeamById(Long id) {
        Team team = teamRepository.findByIdWithMembers(id)
                .orElseThrow(() -> new RuntimeException("Team not found with id: " + id));
        return convertToDtoWithMembers(team);
    }

    public TeamDto createTeam(TeamDto teamDto) {
        if (teamRepository.existsByName(teamDto.getName())) {
            throw new RuntimeException("Team with name '" + teamDto.getName() + "' already exists");
        }

        Team team = new Team(teamDto.getName(), teamDto.getDescription());
        Team savedTeam = teamRepository.save(team);
        return convertToDto(savedTeam);
    }

    public TeamDto updateTeam(Long id, TeamDto teamDto) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Team not found with id: " + id));

        if (!team.getName().equals(teamDto.getName()) && teamRepository.existsByName(teamDto.getName())) {
            throw new RuntimeException("Team with name '" + teamDto.getName() + "' already exists");
        }

        team.setName(teamDto.getName());
        team.setDescription(teamDto.getDescription());

        Team updatedTeam = teamRepository.save(team);
        return convertToDto(updatedTeam);
    }

    public void deleteTeam(Long id) {
        if (!teamRepository.existsById(id)) {
            throw new RuntimeException("Team not found with id: " + id);
        }
        teamRepository.deleteById(id);
    }

    private TeamDto convertToDto(Team team) {
        TeamDto dto = new TeamDto();
        dto.setId(team.getId());
        dto.setName(team.getName());
        dto.setDescription(team.getDescription());
        dto.setCreatedAt(team.getCreatedAt());
        dto.setUpdatedAt(team.getUpdatedAt());
        return dto;
    }

    private TeamDto convertToDtoWithMembers(Team team) {
        TeamDto dto = convertToDto(team);
        if (team.getMembers() != null) {
            dto.setMembers(team.getMembers().stream()
                    .map(this::convertMemberToDto)
                    .collect(Collectors.toList()));
        }
        return dto;
    }

    private TeamMemberDto convertMemberToDto(TeamMember member) {
        TeamMemberDto dto = new TeamMemberDto();
        dto.setId(member.getId());
        dto.setFirstName(member.getFirstName());
        dto.setLastName(member.getLastName());
        dto.setEmail(member.getEmail());
        dto.setJurisdiction(member.getJurisdiction());
        dto.setCapacityPercentage(member.getCapacityPercentage());
        dto.setCreatedAt(member.getCreatedAt());
        dto.setUpdatedAt(member.getUpdatedAt());
        return dto;
    }
}