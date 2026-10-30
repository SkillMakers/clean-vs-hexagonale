package com.itskillsmaker.hiring.usecases.submit;

import com.itskillsmaker.hiring.entities.JobOffer;
import java.util.Optional;
import java.util.UUID;

public interface JobOfferGateway {
    Optional<JobOffer> findById(UUID id);
}
