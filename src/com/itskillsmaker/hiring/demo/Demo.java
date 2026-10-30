package com.itskillsmaker.hiring.demo;

import com.itskillsmaker.hiring.entities.*;
import com.itskillsmaker.hiring.usecases.submit.*;
import java.time.*;
import java.util.*;

// Checks the code shown on the cards: plain Java, no Spring, no database.
public class Demo {

    static class InMemoryJobApplications implements JobApplicationGateway {
        private final List<JobApplication> store = new ArrayList<>();
        public List<JobApplication> findByCandidate(UUID candidateId) {
            return store.stream().filter(a -> a.candidateId().equals(candidateId)).toList();
        }
        public JobApplication save(JobApplication application) { store.add(application); return application; }
    }

    static class CapturingPresenter implements SubmitJobApplicationOutputBoundary {
        SubmitJobApplicationResponse response;
        public void present(SubmitJobApplicationResponse response) { this.response = response; }
    }

    public static void main(String[] args) {
        Map<UUID, JobOffer> offers = new HashMap<>();
        UUID candidate = UUID.randomUUID();
        UUID open = UUID.randomUUID();
        UUID closed = UUID.randomUUID();
        offers.put(open, new JobOffer(open, "Backend Java", true));
        offers.put(closed, new JobOffer(closed, "Frontend", false));

        InMemoryJobApplications applications = new InMemoryJobApplications();
        Clock clock = Clock.fixed(Instant.parse("2026-10-27T10:00:00Z"), ZoneOffset.UTC);
        SubmitJobApplicationInputBoundary submit =
                new SubmitJobApplicationInteractor(applications, id -> Optional.ofNullable(offers.get(id)), clock);

        CapturingPresenter presenter = new CapturingPresenter();
        submit.submit(new SubmitJobApplicationRequest(candidate, open), presenter);
        System.out.println("1. presented: submittedAt=" + presenter.response.submittedAt()
                + " pendingCount=" + presenter.response.pendingCount());

        expect("2. same offer twice", AlreadyApplied.class,
                () -> submit.submit(new SubmitJobApplicationRequest(candidate, open), new CapturingPresenter()));
        expect("3. closed offer (thrown by the JobOffer entity)", JobOfferClosed.class,
                () -> submit.submit(new SubmitJobApplicationRequest(candidate, closed), new CapturingPresenter()));

        for (int i = 0; i < 9; i++) {
            UUID offer = UUID.randomUUID();
            offers.put(offer, new JobOffer(offer, "Offer " + i, true));
            submit.submit(new SubmitJobApplicationRequest(candidate, offer), new CapturingPresenter());
        }
        UUID eleventh = UUID.randomUUID();
        offers.put(eleventh, new JobOffer(eleventh, "Offer 11", true));
        expect("4. eleventh pending application", TooManyPendingApplications.class,
                () -> submit.submit(new SubmitJobApplicationRequest(candidate, eleventh), new CapturingPresenter()));
    }

    static void expect(String label, Class<? extends RuntimeException> expected, Runnable action) {
        try {
            action.run();
            System.out.println(label + ": FAILED, no exception");
        } catch (RuntimeException e) {
            System.out.println(label + ": " + (expected.isInstance(e) ? "OK " : "WRONG ") + e.getClass().getSimpleName());
        }
    }
}
