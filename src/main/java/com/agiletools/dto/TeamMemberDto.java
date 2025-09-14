package com.agiletools.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeamMemberDto {

    private Long id;

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @Email(message = "Email must be valid if provided")
    private String email;

    private String jurisdiction;
    private Double capacityPercentage;
    private Long teamId;
    private String teamName;
    private List<LeaveDto> leaves;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public TeamMemberDto(String firstName, String lastName, String email, String jurisdiction) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.jurisdiction = jurisdiction;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }
}