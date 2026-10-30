package com.itskillsmaker.hiring.usecases.submit;

import com.itskillsmaker.hiring.entities.JobApplication;
import com.itskillsmaker.hiring.entities.JobOffer;
import java.time.Clock;
import java.util.List;

public class SubmitJobApplicationInteractor implements SubmitJobApplicationInputBoundary {

    private static final int MAX_PENDING = 10;

    private final JobApplicationGateway applications;
    private final JobOfferGateway offers;
    private final Clock clock;

    public SubmitJobApplicationInteractor(JobApplicationGateway applications, JobOfferGateway offers, Clock clock) {
        this.applications = applications;
        this.offers = offers;
        this.clock = clock;
    }

    @Override
    public void submit(SubmitJobApplicationRequest request, SubmitJobApplicationOutputBoundary presenter) {
        JobOffer offer = offers.findById(request.offerId())
                .orElseThrow(JobOfferNotFound::new);

        offer.ensureAcceptsApplications();                       // enterprise rule (entity)

        List<JobApplication> existing = applications.findByCandidate(request.candidateId());

        if (existing.stream().anyMatch(a -> a.offerId().equals(request.offerId()))) {
            throw new AlreadyApplied();                          // application rule
        }

        long pending = existing.stream().filter(JobApplication::isPending).count();
        if (pending >= MAX_PENDING) {
            throw new TooManyPendingApplications(MAX_PENDING);   // application rule
        }

        JobApplication saved = applications.save(
                JobApplication.submit(request.candidateId(), request.offerId(), clock.instant()));

        presenter.present(new SubmitJobApplicationResponse(saved.id(), saved.submittedAt(), pending + 1));
    }
}
