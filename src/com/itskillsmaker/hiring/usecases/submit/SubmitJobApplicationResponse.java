package com.itskillsmaker.hiring.usecases.submit;

import java.time.Instant;
import java.util.UUID;

public record SubmitJobApplicationResponse(UUID applicationId, Instant submittedAt, long pendingCount) {}
