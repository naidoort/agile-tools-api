package com.agiletools.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

public class TeamMemberDto {

    private Long id;

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @Email(message = "Valid email is required")
    private String email;

    private String jurisdiction;
    private Double capacityPercentage;
    private Long teamId;
    private String teamName;
    private List<LeaveDto> leaves;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public TeamMemberDto() {}

    public TeamMemberDto(String firstName, String lastName, String email, String jurisdiction) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.jurisdiction = jurisdiction;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getJurisdiction() {
        return jurisdiction;
    }

    public void setJurisdiction(String jurisdiction) {
        this.jurisdiction = jurisdiction;
    }

    public Double getCapacityPercentage() {
        return capacityPercentage;
    }

    public void setCapacityPercentage(Double capacityPercentage) {
        this.capacityPercentage = capacityPercentage;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public List<LeaveDto> getLeaves() {
        return leaves;
    }

    public void setLeaves(List<LeaveDto> leaves) {
        this.leaves = leaves;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }
}