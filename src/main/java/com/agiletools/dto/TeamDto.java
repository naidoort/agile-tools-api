package com.agiletools.dto;

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
public class TeamDto {

    private Long id;

    @NotBlank(message = "Team name is required")
    private String name;

    private String description;
    private List<TeamMemberDto> members;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public TeamDto(String name, String description) {
        this.name = name;
        this.description = description;
    }
}