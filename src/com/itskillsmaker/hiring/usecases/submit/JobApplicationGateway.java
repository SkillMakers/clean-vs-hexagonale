package com.itskillsmaker.hiring.usecases.submit;

import com.itskillsmaker.hiring.entities.JobApplication;
import java.util.List;
import java.util.UUID;

public interface JobApplicationGateway {
    List<JobApplication> findByCandidate(UUID candidateId);
    JobApplication save(JobApplication application);
}
