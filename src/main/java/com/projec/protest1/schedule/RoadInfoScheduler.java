package com.projec.protest1.schedule;

import com.projec.protest1.domain.RoadAll;
import com.projec.protest1.domain.RoadSpotInfo;
import com.projec.protest1.dto.RoadInfoDto;
import com.projec.protest1.exception.ApiErrorCodeException;
import com.projec.protest1.repository.RoadRepository;
import com.projec.protest1.service.RoadService;
import com.projec.protest1.utils.ExternalApiRequester;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RoadInfoScheduler {
    private final RoadRepository roadRepository;
    private final RoadService roadService;
    private final ExternalApiRequester apiRequester = new ExternalApiRequester();

    @Scheduled(cron = "0 10 * * * *") // 외부 api에 늦게 데이터가 업로드 되는 가능성을 예상하여 0초 10분으로 설정
    public void saveLatestRoadInfo() throws ApiErrorCodeException { // 한시간 전 데이터를 DB에 저장하는 스케줄러
        List<RoadSpotInfo> roadSpotInfos = roadService.findRoadSpotInfos();

        LocalDateTime nowDateTime = LocalDateTime.now();

        String todayDate = nowDateTime
                .format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        int time = Integer.parseInt(nowDateTime.minusHours(1)
                .format(DateTimeFormatter.ofPattern("HH")));

        for (RoadSpotInfo roadSpotInfo : roadSpotInfos) { // roadList에 있는 모든 도로에 대해 실행
            List<RoadInfoDto> roadInfoDtos = apiRequester.requestRoadVolInfo(roadSpotInfo.getRoadId(), todayDate, time);
            // 외부 api에서 값 조회하기
            for (RoadInfoDto roadInfoDto : roadInfoDtos) {
                RoadAll roadAll = new RoadAll(
                        roadSpotInfo.getRoadId(),
                        todayDate,
                        time,
                        roadInfoDto.getIoType(),
                        roadInfoDto.getLaneNum(),
                        roadInfoDto.getVol());
                roadRepository.save(roadAll); // 저장
            }
        }
    }

    @Scheduled(cron = "0 0 0 * * *") // 0초 0분 0시에 실행
    public void deleteOldRoadInfo() { // 하루 지나면, 5년 + 1일 전날의 데이터 삭제
        String yesterdayDate = LocalDateTime.now().minusYears(5).minusDays(1).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        roadRepository.deleteByDate(yesterdayDate);
    }
}