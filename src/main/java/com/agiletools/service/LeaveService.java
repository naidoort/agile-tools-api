package com.agiletools.service;

import com.agiletools.dto.LeaveDto;
import com.agiletools.model.Leave;
import com.agiletools.model.TeamMember;
import com.agiletools.repository.LeaveRepository;
import com.agiletools.repository.TeamMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class LeaveService {

    @Autowired
    private LeaveRepository leaveRepository;

    @Autowired
    private TeamMemberRepository teamMemberRepository;

    public List<LeaveDto> getAllLeaves() {
        return leaveRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<LeaveDto> getLeavesByMember(Long teamMemberId) {
        return leaveRepository.findByTeamMemberId(teamMemberId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<LeaveDto> getLeavesByTeam(Long teamId) {
        return leaveRepository.findByTeamMemberTeamId(teamId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<LeaveDto> getTeamLeavesInPeriod(Long teamId, LocalDate startDate, LocalDate endDate) {
        return leaveRepository.findTeamLeavesInPeriod(teamId, startDate, endDate).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public LeaveDto getLeaveById(Long id) {
        Leave leave = leaveRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Leave not found with id: " + id));
        return convertToDto(leave);
    }

    public LeaveDto createLeave(LeaveDto leaveDto) {
        TeamMember teamMember = teamMemberRepository.findById(leaveDto.getTeamMemberId())
                .orElseThrow(() -> new RuntimeException("Team member not found with id: " + leaveDto.getTeamMemberId()));

        if (leaveDto.getStartDate().isAfter(leaveDto.getEndDate())) {
            throw new RuntimeException("Start date cannot be after end date");
        }

        List<Leave> overlappingLeaves = leaveRepository.findOverlappingLeaves(
                leaveDto.getTeamMemberId(),
                leaveDto.getStartDate(),
                leaveDto.getEndDate()
        );

        if (!overlappingLeaves.isEmpty()) {
            throw new RuntimeException("Leave overlaps with existing leave for this team member");
        }

        Leave leave = new Leave(
                leaveDto.getStartDate(),
                leaveDto.getEndDate(),
                leaveDto.getLeaveType(),
                leaveDto.getDescription()
        );

        leave.setTeamMember(teamMember);

        Leave savedLeave = leaveRepository.save(leave);
        return convertToDto(savedLeave);
    }

    public LeaveDto updateLeave(Long id, LeaveDto leaveDto) {
        Leave leave = leaveRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Leave not found with id: " + id));

        if (leaveDto.getStartDate().isAfter(leaveDto.getEndDate())) {
            throw new RuntimeException("Start date cannot be after end date");
        }

        List<Leave> overlappingLeaves = leaveRepository.findOverlappingLeaves(
                leave.getTeamMember().getId(),
                leaveDto.getStartDate(),
                leaveDto.getEndDate()
        );

        overlappingLeaves = overlappingLeaves.stream()
                .filter(l -> !l.getId().equals(id))
                .collect(Collectors.toList());

        if (!overlappingLeaves.isEmpty()) {
            throw new RuntimeException("Leave overlaps with existing leave for this team member");
        }

        leave.setStartDate(leaveDto.getStartDate());
        leave.setEndDate(leaveDto.getEndDate());
        leave.setLeaveType(leaveDto.getLeaveType());
        leave.setDescription(leaveDto.getDescription());

        Leave updatedLeave = leaveRepository.save(leave);
        return convertToDto(updatedLeave);
    }

    public void deleteLeave(Long id) {
        if (!leaveRepository.existsById(id)) {
            throw new RuntimeException("Leave not found with id: " + id);
        }
        leaveRepository.deleteById(id);
    }

    private LeaveDto convertToDto(Leave leave) {
        LeaveDto dto = new LeaveDto();
        dto.setId(leave.getId());
        dto.setStartDate(leave.getStartDate());
        dto.setEndDate(leave.getEndDate());
        dto.setLeaveType(leave.getLeaveType());
        dto.setDescription(leave.getDescription());
        dto.setCreatedAt(leave.getCreatedAt());
        dto.setUpdatedAt(leave.getUpdatedAt());

        if (leave.getTeamMember() != null) {
            dto.setTeamMemberId(leave.getTeamMember().getId());
            dto.setTeamMemberName(leave.getTeamMember().getFullName());
        }

        return dto;
    }
}