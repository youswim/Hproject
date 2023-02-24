package com.projec.protest1.contorller;

import com.projec.protest1.domain.RoadSpotInfo;
import com.projec.protest1.dto.RoadInfoDto;
import com.projec.protest1.dto.RoadInfoSearchDto;
import com.projec.protest1.exception.ApiErrorCodeException;
import com.projec.protest1.exceptionapi.ValidationResult;
import com.projec.protest1.service.RoadService;
import com.projec.protest1.validation.RoadInfoValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@Slf4j
@RestController
@RequiredArgsConstructor
public class RoadController {

    private final RoadService roadService;

    private final RoadInfoValidator roadInfoValidator;
    private final MessageSource messageSource;

    @InitBinder
    public void init(WebDataBinder dataBinder) {
        dataBinder.addValidators(roadInfoValidator);
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationResult handleBindException(BindException bindException, Locale locale) {
        return ValidationResult.create(bindException, messageSource, locale);
    }

    @ExceptionHandler(ApiErrorCodeException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleApiErrorCodeException(ApiErrorCodeException apiErrorCodeException, Locale locale) {
        String exceptionMessage = apiErrorCodeException.getMessage();
        log.warn(exceptionMessage);
        String[] splittedExceptionMessage = exceptionMessage.split("\\|");
        return splittedExceptionMessage[2];
    }

    // https://data.seoul.go.kr/dataList/OA-13314/A/1/datasetView.do
    @GetMapping("/api/roads")
    public List<RoadSpotInfo> findRoadSpotInfos() {
        return roadService.findRoadSpotInfos();
    }

    // https://data.seoul.go.kr/dataList/OA-13316/A/1/datasetView.do
    @GetMapping("/api/road-info")
    public List<RoadInfoDto> getRoadInfo(@Validated @ModelAttribute RoadInfoSearchDto risDto, BindingResult bindingResult) throws BindException, ApiErrorCodeException {
        
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }
        return roadService.getRoadInfo(risDto);
    }
}
