package com.itskillsmaker.hiring.usecases.submit;

public class TooManyPendingApplications extends RuntimeException {
    public TooManyPendingApplications(int max) { super("Already " + max + " pending applications"); }
}
