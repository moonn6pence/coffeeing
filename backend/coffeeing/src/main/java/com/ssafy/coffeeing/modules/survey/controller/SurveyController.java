package com.ssafy.coffeeing.modules.survey.controller;

import com.ssafy.coffeeing.modules.survey.dto.PreferenceRequest;
import com.ssafy.coffeeing.modules.survey.dto.SurveyResponse;
import com.ssafy.coffeeing.modules.survey.service.SurveyService;
import com.ssafy.coffeeing.modules.util.base.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/survey")
@RequiredArgsConstructor
public class SurveyController {

    private final SurveyService surveyService;


    @PostMapping("/recommend")
    public BaseResponse<SurveyResponse> recommendBySurvey(@Valid PreferenceRequest preferenceRequest){

        return BaseResponse.<SurveyResponse>builder()
                .data(surveyService.recommendBySurvey(preferenceRequest))
                .build();
    }

    @PostMapping("/save")
    public BaseResponse<Void> savePreference(@Valid PreferenceRequest preferenceRequest){
        surveyService.savePreference(preferenceRequest);
        return BaseResponse.<Void>builder().build();
    }

}
