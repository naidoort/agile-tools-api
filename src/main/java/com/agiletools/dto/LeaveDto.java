package com.agiletools.dto;

import com.agiletools.model.Leave;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeaveDto {

    private Long id;

    @NotNull(message = "Start date is required")
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    private LocalDate endDate;

    private Leave.LeaveType leaveType;
    private String description;
    private Long teamMemberId;
    private String teamMemberName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public LeaveDto(LocalDate startDate, LocalDate endDate, Leave.LeaveType leaveType, String description) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.leaveType = leaveType;
        this.description = description;
    }
}