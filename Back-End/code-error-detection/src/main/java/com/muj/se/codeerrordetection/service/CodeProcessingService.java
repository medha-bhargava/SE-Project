package com.muj.se.codeerrordetection.service;

import com.muj.se.codeerrordetection.dto.Code;
import com.muj.se.codeerrordetection.dto.Error;

public interface CodeProcessingService {

    Error sendForErrorAnalysis(Code code);
}
