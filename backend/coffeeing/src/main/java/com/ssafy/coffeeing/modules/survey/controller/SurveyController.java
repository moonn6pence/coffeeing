package com.ssafy.coffeeing.modules.survey.controller;

import com.ssafy.coffeeing.modules.survey.dto.PreferenceRequest;
import com.ssafy.coffeeing.modules.survey.dto.SurveyResponse;
import com.ssafy.coffeeing.modules.survey.service.SurveyService;
import com.ssafy.coffeeing.modules.util.base.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/survey")
@RequiredArgsConstructor
public class SurveyController {

    private final SurveyService surveyService;


    @GetMapping
    public BaseResponse<SurveyResponse> getSurveyResult(){

        return BaseResponse.<SurveyResponse>builder()
                .data(surveyService.getSurveyResult())
                .build();

    }

    @PostMapping("/save-preference")
    public BaseResponse<Void> savePreference(@RequestBody PreferenceRequest preferenceRequest){

        return BaseResponse.<Void>builder().build();
    }

}
