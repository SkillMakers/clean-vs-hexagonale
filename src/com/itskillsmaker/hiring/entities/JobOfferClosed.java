package com.itskillsmaker.hiring.entities;

import java.util.UUID;

public class JobOfferClosed extends RuntimeException {
    public JobOfferClosed(UUID offerId) {
        super("Offer " + offerId + " is closed");
    }
}
