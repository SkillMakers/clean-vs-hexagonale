package com.itskillsmaker.hiring.usecases.submit;

public class JobOfferNotFound extends RuntimeException {
    public JobOfferNotFound() { super("Offer not found"); }
}
