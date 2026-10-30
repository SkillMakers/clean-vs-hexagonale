package com.itskillsmaker.hiring.usecases.submit;

import java.util.UUID;

public record SubmitJobApplicationRequest(UUID candidateId, UUID offerId) {}
