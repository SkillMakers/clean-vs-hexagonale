package com.itskillsmaker.hiring.entities;

import java.time.Instant;
import java.util.UUID;

public record JobApplication(
        UUID id,
        UUID candidateId,
        UUID offerId,
        Instant submittedAt,
        ApplicationStatus status) {

    public static JobApplication submit(UUID candidateId, UUID offerId, Instant at) {
        return new JobApplication(UUID.randomUUID(), candidateId, offerId, at, ApplicationStatus.PENDING);
    }

    public boolean isPending() {
        return status == ApplicationStatus.PENDING;
    }
}
