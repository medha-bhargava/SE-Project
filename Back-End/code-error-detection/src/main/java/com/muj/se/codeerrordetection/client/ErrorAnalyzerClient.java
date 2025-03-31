package com.muj.se.codeerrordetection.client;

import java.io.IOException;

public interface ErrorAnalyzerClient {

    String processCodeForErrors(String payload) throws IOException, InterruptedException;
}
