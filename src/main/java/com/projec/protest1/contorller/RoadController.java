package com.projec.protest1.contorller;

import com.projec.protest1.dto.RoadInfoDto;
import com.projec.protest1.dto.RoadDto;
import com.projec.protest1.utils.UrlMaker;
import com.projec.protest1.utils.XmlParser;
import com.projec.protest1.validation.RoadInfoSearchDto;
import com.projec.protest1.validation.RoadInfoValidator;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class RoadController {

    private final RoadInfoValidator roadInfoValidator;
    UrlMaker urlMaker = new UrlMaker();
    XmlParser xp = new XmlParser();

    @InitBinder
    public void init(WebDataBinder dataBinder) {
        dataBinder.addValidators(roadInfoValidator);
    }

    // https://data.seoul.go.kr/dataList/OA-13314/A/1/datasetView.do
    @GetMapping("/api/roads")
    public List<RoadDto> getRoads() {
        String url = urlMaker.getSpotInfoUrl();
        return xp.formXmlToRoadDto(url);
    }

    // https://data.seoul.go.kr/dataList/OA-13316/A/1/datasetView.do
    @GetMapping("/api/road_info")
    public List<RoadInfoDto> getRoadInfo(@Validated @ModelAttribute RoadInfoSearchDto roadInfoSearchDto, BindingResult bindingResult) {
//        System.out.println(bindingResult);
        String url = urlMaker.getVolInfoUrl(roadInfoSearchDto.getRid().toUpperCase(), roadInfoSearchDto.getDate(), roadInfoSearchDto.getTime());
        System.out.println("url : " + url);
        return xp.fromXmlToRoadInfoDto(url);
    }
}
