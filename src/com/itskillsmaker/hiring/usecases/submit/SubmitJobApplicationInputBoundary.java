package com.itskillsmaker.hiring.usecases.submit;

public interface SubmitJobApplicationInputBoundary {
    void submit(SubmitJobApplicationRequest request, SubmitJobApplicationOutputBoundary presenter);
}
