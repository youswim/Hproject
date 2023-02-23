package com.projec.protest1.utils;

import com.projec.protest1.domain.RoadAll;
import com.projec.protest1.domain.RoadSpotInfo;
import com.projec.protest1.dto.RoadInfoDto;
import com.projec.protest1.dto.SignupRequestDto;
import com.projec.protest1.repository.RoadRepository;
import com.projec.protest1.repository.RoadSpotInfoRepository;
import com.projec.protest1.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ApplicationSetup {

    private final UserService userService;
    private final RoadRepository roadRepository;
    private final RoadSpotInfoRepository roadSpotInfoRepository;

    private final ExternalApiRequester apiRequester = new ExternalApiRequester();

    public void signUpMember() {
        SignupRequestDto signupRequestDto = new SignupRequestDto();
        signupRequestDto.setUsername("111");
        signupRequestDto.setPassword("111");
        signupRequestDto.setEmail("111@naver.com");
        signupRequestDto.setAdmin(true);
        signupRequestDto.setAdminToken("AAABnv/xRVklrnYxKZ0aHgTBcXukeZygoC");

        userService.registerUser(signupRequestDto);
    }

    public void saveInfos() throws InterruptedException {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        List<RoadSpotInfo> roadSpotInfos = null;
        try {
            roadSpotInfos = apiRequester.requestRoadSpotInfos();
        } catch (IllegalStateException e) {
            log.warn(e.getMessage());
        }
        if (roadSpotInfos == null) {
            return;
        }

        for (RoadSpotInfo roadSpotInfoDto : roadSpotInfos) {
            roadSpotInfoRepository.save(new RoadSpotInfo(roadSpotInfoDto.getRoadId(), roadSpotInfoDto.getRoadName()));
        }

        // 오늘 데이터 얻기
        // [00시 ~ 한시간 전] 범위의 데이터 저장
        for (RoadSpotInfo roadSpotInfoDto : roadSpotInfos) {
            String roadId = roadSpotInfoDto.getRoadId();

            String hh = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH"));
            String nowDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            for (int time = 0; time < Integer.parseInt(hh); time++) {
                List<RoadInfoDto> roadInfoDtos = null;
                try {
                    roadInfoDtos = apiRequester.requestRoadVolInfo(roadId, nowDate, time);
                } catch (IllegalStateException e) {
                    log.warn(e.getMessage());
                }
                if (roadInfoDtos == null) {
                    continue;
                }
                for (RoadInfoDto roadInfoDto : roadInfoDtos) {
                    roadRepository.save(new RoadAll(roadId, nowDate, time, roadInfoDto.getIo_type(), roadInfoDto.getLane_num(), roadInfoDto.getVol()));
                }
            }
        }

        // 과거 데이터 얻기
        // [5년전 오늘 ~ 어제] 범위의 데이터 저장
        for (RoadSpotInfo roadSpotInfoDto : roadSpotInfos) {
            String roadId = roadSpotInfoDto.getRoadId();

            // 전날부터 오년전 오늘까지 데이터 저장하기
            LocalDate baseDate = LocalDate.now().minusYears(5).minusDays(1);
            LocalDate idxDate = LocalDate.now().minusDays(1);

            while (baseDate.isBefore(idxDate)) {
                for (int time = 0; time < 24; time++) {
                    String date = idxDate.format(dateTimeFormatter);
                    List<RoadInfoDto> roadInfoDtos = null;
                    try {
                        roadInfoDtos = apiRequester.requestRoadVolInfo(roadId, date, time);
                    } catch (IllegalStateException e) {
                        log.warn(e.getMessage());
                    }
                    if (roadInfoDtos == null) {
                        continue;
                    }
                    for (RoadInfoDto roadInfoDto : roadInfoDtos) {
                        roadRepository.save(new RoadAll(roadId, date, time, roadInfoDto.getIo_type(), roadInfoDto.getLane_num(), roadInfoDto.getVol()));
                    }
                }
                idxDate = idxDate.minusDays(1);
            }
            Thread.sleep(1000);
        }
    }
}
