package com.ssafy.coffeeing.modules.survey.controller;

import com.ssafy.coffeeing.modules.survey.dto.PreferenceRequest;
import com.ssafy.coffeeing.modules.survey.dto.SurveyResponse;
import com.ssafy.coffeeing.modules.survey.service.SurveyService;
import com.ssafy.coffeeing.modules.util.base.BaseResponse;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/survey")
@RequiredArgsConstructor
public class SurveyController {

    private final SurveyService surveyService;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "isCapsule", dataType = "Boolean", value = "캡슐 여부", paramType = "query"),
            @ApiImplicitParam(name = "machineType", dataType = "Integer", value = "커피 머신 타입", paramType = "query"),
            @ApiImplicitParam(name = "roast", dataType = "Double", value = "로스팅", paramType = "query"),
            @ApiImplicitParam(name = "acidity", dataType = "Double", value = "산미", paramType = "query"),
            @ApiImplicitParam(name = "body", dataType = "Double", value = "바디감", paramType = "query"),
            @ApiImplicitParam(name = "flavorNote", dataType = "String", value = "아로마 맛 노트", paramType = "query")
    })
    @PostMapping("/recommend")
    public BaseResponse<SurveyResponse> recommendBySurvey(@Valid PreferenceRequest preferenceRequest) {
        
        return BaseResponse.<SurveyResponse>builder()
                .data(surveyService.recommendBySurvey(preferenceRequest))
                .build();
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "isCapsule", dataType = "Boolean", value = "캡슐 여부", paramType = "query"),
            @ApiImplicitParam(name = "machineType", dataType = "Integer", value = "커피 머신 타입", paramType = "query"),
            @ApiImplicitParam(name = "roast", dataType = "Double", value = "로스팅", paramType = "query"),
            @ApiImplicitParam(name = "acidity", dataType = "Double", value = "산미", paramType = "query"),
            @ApiImplicitParam(name = "body", dataType = "Double", value = "바디감", paramType = "query"),
            @ApiImplicitParam(name = "flavorNote", dataType = "String", value = "아로마 맛 노트", paramType = "query")
    })
    @PostMapping("/save")
    public BaseResponse<Void> savePreference(@Valid PreferenceRequest preferenceRequest){
        surveyService.savePreference(preferenceRequest);
        return BaseResponse.<Void>builder().build();
    }

}
