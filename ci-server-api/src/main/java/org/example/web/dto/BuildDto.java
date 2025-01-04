package org.example.web.dto;

import org.example.domain.BuildStatus;

public record BuildDto(Long id, String startTime, String endTime, BuildStatus buildStatus,
                       Long projectId, String branch) {
}
