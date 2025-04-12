package com.muj.se.codeerrordetection.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.muj.se.codeerrordetection.client.ErrorAnalyzerClient;
import com.muj.se.codeerrordetection.dto.Code;
import com.muj.se.codeerrordetection.dto.Error;
import com.muj.se.codeerrordetection.dto.ErrorItem;
import com.muj.se.codeerrordetection.service.CodeProcessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class CodeProcessingServiceImpl implements CodeProcessingService {

    private final ErrorAnalyzerClient errorAnalyzerClient;
    private final ObjectMapper objectMapper;

    @Autowired
    public CodeProcessingServiceImpl(ErrorAnalyzerClient errorAnalyzerClient,
                                     @Qualifier("objectMapper") ObjectMapper objectMapper) {
        this.errorAnalyzerClient = errorAnalyzerClient;
        this.objectMapper = objectMapper;
    }

    @Override
    public Error sendForErrorAnalysis(Code code) {
        String clientResponse;
        try {
            String clientPayload = objectMapper.writeValueAsString(code);
            System.out.println("Client Payload: " + clientPayload);
            clientResponse = errorAnalyzerClient.processCodeForErrors(clientPayload);
        } catch (JsonProcessingException e) {
            System.out.println("Error in serializing the payload: " + e.getMessage());
            throw new RuntimeException("Error in serializing the payload");
        } catch (IOException | InterruptedException e) {
            System.out.println("Error in sending code to ErrorAnalyzerClient: " + e.getMessage());
            throw new RuntimeException("Error in invoking ErrorAnalyzerClient");
        }
        System.out.println("Response from client: " + clientResponse);

        Error analyzedErrors;
        try {
            JsonNode rootNode = objectMapper.readTree(clientResponse);

            // Fetch syntax errors as string
            String syntaxErrorsString = rootNode.get("syntax_errors").toString();
            // Fetch logical errors as string
            String logicalErrorsString = rootNode.get("logical_errors").toString();

            // Obtain the list of syntax errors
            List<ErrorItem> syntaxErrorList = objectMapper.readValue(syntaxErrorsString, new TypeReference<>() {});
            boolean hasSyntaxErrors = (syntaxErrorList != null && !syntaxErrorList.isEmpty());

            // Obtain the list of logical errors
            List<ErrorItem> logicalErrorList = objectMapper.readValue(logicalErrorsString, new TypeReference<>() {});
            boolean hasLogicalErrors = (logicalErrorList != null && !logicalErrorList.isEmpty());

            analyzedErrors = new Error(hasSyntaxErrors, syntaxErrorList, hasLogicalErrors, logicalErrorList);
            System.out.println("Payload to be sent to UI: " +
                    objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(analyzedErrors));
        } catch (JsonProcessingException e) {
            System.out.println("Error in parsing response payload from client: " + e.getMessage());
            throw new RuntimeException("Error in parsing payload from ErrorAnalyzerClient");
        }
        return analyzedErrors;
    }
}
