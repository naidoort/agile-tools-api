package com.agiletools.controller;

import com.agiletools.dto.TeamMemberDto;
import com.agiletools.service.TeamMemberService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/team-members")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
public class TeamMemberController {

    @Autowired
    private TeamMemberService teamMemberService;

    @GetMapping
    public ResponseEntity<List<TeamMemberDto>> getAllMembers() {
        List<TeamMemberDto> members = teamMemberService.getAllMembers();
        return ResponseEntity.ok(members);
    }

    @GetMapping("/team/{teamId}")
    public ResponseEntity<List<TeamMemberDto>> getMembersByTeam(@PathVariable Long teamId) {
        List<TeamMemberDto> members = teamMemberService.getMembersByTeam(teamId);
        return ResponseEntity.ok(members);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeamMemberDto> getMemberById(@PathVariable Long id) {
        try {
            TeamMemberDto member = teamMemberService.getMemberById(id);
            return ResponseEntity.ok(member);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<TeamMemberDto> createMember(@Valid @RequestBody TeamMemberDto memberDto) {
        try {
            TeamMemberDto createdMember = teamMemberService.createMember(memberDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdMember);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeamMemberDto> updateMember(@PathVariable Long id, @Valid @RequestBody TeamMemberDto memberDto) {
        try {
            TeamMemberDto updatedMember = teamMemberService.updateMember(id, memberDto);
            return ResponseEntity.ok(updatedMember);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long id) {
        try {
            teamMemberService.deleteMember(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}