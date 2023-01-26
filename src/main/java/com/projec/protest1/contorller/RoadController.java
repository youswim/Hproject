package com.projec.protest1.contorller;

import com.projec.protest1.dto.RoadInfoDto;
import com.projec.protest1.dto.RoadDto;
import com.projec.protest1.utils.UrlMaker;
import com.projec.protest1.utils.XmlParser;
import com.projec.protest1.dto.RoadInfoSearchDto;
import com.projec.protest1.validation.RoadInfoValidator;
import com.projec.protest1.exceptionapi.ValidationResult;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@RestController
@RequiredArgsConstructor
public class RoadController {
    private final RoadInfoValidator roadInfoValidator;
    private final MessageSource messageSource;
    UrlMaker urlMaker = new UrlMaker();
    XmlParser xp = new XmlParser();

    @InitBinder
    public void init(WebDataBinder dataBinder) {
        dataBinder.addValidators(roadInfoValidator);
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationResult handleBindException(BindException bindException, Locale locale) {
//        System.out.println("RoadController.handleBindException");
        return ValidationResult.create(bindException, messageSource, locale);
    }

    // https://data.seoul.go.kr/dataList/OA-13314/A/1/datasetView.do
    @GetMapping("/api/roads")
    public List<RoadDto> getRoads() {
        String url = urlMaker.getSpotInfoUrl();
        return xp.formXmlToRoadDto(url);
    }

    // https://data.seoul.go.kr/dataList/OA-13316/A/1/datasetView.do
    @GetMapping("/api/road_info")
    public List<RoadInfoDto> getRoadInfo(@Validated @ModelAttribute RoadInfoSearchDto roadInfoSearchDto, BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }
        String url = urlMaker.getVolInfoUrl(roadInfoSearchDto.getRid().toUpperCase(), roadInfoSearchDto.getDate(), roadInfoSearchDto.getTime());
        System.out.println("url : " + url);
        return xp.fromXmlToRoadInfoDto(url);
    }
}
