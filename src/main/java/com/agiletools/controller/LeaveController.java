package com.agiletools.controller;

import com.agiletools.dto.LeaveDto;
import com.agiletools.service.LeaveService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/leaves")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
public class LeaveController {

    @Autowired
    private LeaveService leaveService;

    @GetMapping
    public ResponseEntity<List<LeaveDto>> getAllLeaves() {
        List<LeaveDto> leaves = leaveService.getAllLeaves();
        return ResponseEntity.ok(leaves);
    }

    @GetMapping("/member/{teamMemberId}")
    public ResponseEntity<List<LeaveDto>> getLeavesByMember(@PathVariable Long teamMemberId) {
        List<LeaveDto> leaves = leaveService.getLeavesByMember(teamMemberId);
        return ResponseEntity.ok(leaves);
    }

    @GetMapping("/team/{teamId}")
    public ResponseEntity<List<LeaveDto>> getLeavesByTeam(@PathVariable Long teamId) {
        List<LeaveDto> leaves = leaveService.getLeavesByTeam(teamId);
        return ResponseEntity.ok(leaves);
    }

    @GetMapping("/team/{teamId}/period")
    public ResponseEntity<List<LeaveDto>> getTeamLeavesInPeriod(
            @PathVariable Long teamId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<LeaveDto> leaves = leaveService.getTeamLeavesInPeriod(teamId, startDate, endDate);
        return ResponseEntity.ok(leaves);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LeaveDto> getLeaveById(@PathVariable Long id) {
        try {
            LeaveDto leave = leaveService.getLeaveById(id);
            return ResponseEntity.ok(leave);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<LeaveDto> createLeave(@Valid @RequestBody LeaveDto leaveDto) {
        try {
            LeaveDto createdLeave = leaveService.createLeave(leaveDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdLeave);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<LeaveDto> updateLeave(@PathVariable Long id, @Valid @RequestBody LeaveDto leaveDto) {
        try {
            LeaveDto updatedLeave = leaveService.updateLeave(id, leaveDto);
            return ResponseEntity.ok(updatedLeave);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLeave(@PathVariable Long id) {
        try {
            leaveService.deleteLeave(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}