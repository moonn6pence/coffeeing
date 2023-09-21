package com.ssafy.coffeeing.modules.curation.service;

import com.ssafy.coffeeing.modules.curation.dto.CurationResponse;
import com.ssafy.coffeeing.modules.recommend.service.RecommendService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CurationService {

    private RecommendService recommendService;

    @Transactional(readOnly = true)
    public CurationResponse getOpenCuration() {
        return null;
    }

    @Transactional(readOnly = true)
    public CurationResponse getCustomCuration() {
        return null;
    }
}
