package com.itskillsmaker.hiring.entities;

import java.util.UUID;

public record JobOffer(UUID id, String title, boolean open) {

    // Enterprise rule: true whatever the application that uses it.
    public void ensureAcceptsApplications() {
        if (!open) {
            throw new JobOfferClosed(id);
        }
    }
}
