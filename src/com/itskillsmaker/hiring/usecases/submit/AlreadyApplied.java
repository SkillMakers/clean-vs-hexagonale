package com.itskillsmaker.hiring.usecases.submit;

public class AlreadyApplied extends RuntimeException {
    public AlreadyApplied() { super("Already applied to this offer"); }
}
