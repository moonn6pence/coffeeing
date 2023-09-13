package com.ssafy.coffeeing.modules.curation.service;

import com.ssafy.coffeeing.modules.curation.dto.CurationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CurationService {


    @Transactional(readOnly = true)
    public CurationResponse getOpenCuration() {
        return null;
    }

    @Transactional(readOnly = true)
    public CurationResponse getCustomCuration() {
        return null;
    }
}
