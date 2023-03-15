package com.projec.protest1.validation;

import com.projec.protest1.dto.RoadInfoSearchDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class RoadInfoValidatorTest {

    private RoadInfoValidator validator;
    private RoadInfoSearchDto roadInfoSearchDto;
    private Errors errors;

    @BeforeEach
    public void setup() {
        validator = new RoadInfoValidator();
        roadInfoSearchDto = new RoadInfoSearchDto();
        errors = new BeanPropertyBindingResult(roadInfoSearchDto, "roadInfoSearchDto");
    }

    @DisplayName("정상 도로 ID 입력 -> 검증 후, 필드에러 없음")
    @Test
    public void testValidateRid_validRid() {
        roadInfoSearchDto.setRid("A-12");
        validator.validate(roadInfoSearchDto, errors);
        assertFalse(errors.hasFieldErrors("rid"));
    }

    @DisplayName("비정상 도로 ID 입력 -> 검증 후, 필드에러 있음")
    @ParameterizedTest
    @MethodSource("provideRidCases")
    public void variousValidateRidTest(String rid, String errorCode) {
        roadInfoSearchDto.setRid(rid);
        validator.validate(roadInfoSearchDto, errors);
        assertTrue(errors.hasFieldErrors("rid"));
        assertEquals(errorCode, errors.getFieldError("rid").getCode());
    }

    private static Stream<Arguments> provideRidCases() {
        return Stream.of(
                Arguments.of(null, "isNull"), // 도로 ID가 null 경우
                Arguments.of("   ", "isNull"), // 도로 ID가 공백일 경우
                Arguments.of("A12", "formMismatch"), // 도로 ID 형식이 맞지 않을 경우
                Arguments.of("Z-12", "noMatchAlphabet"), // 존재하지 않는 도로 알파벳인 경우
                Arguments.of("A-50", "exceedNumber") // 존재하지 않는 도로 번호인 경우
        );
    }

    @DisplayName("정상 날짜 입력 -> 검증 후, 필드에러 없음")
    @Test
    public void testValidateDate_validDate() {
        roadInfoSearchDto.setDate("20221231");
        validator.validate(roadInfoSearchDto, errors);
        assertNull(errors.getFieldError("date"));
    }


    @DisplayName("비정상 날짜 입력 -> 검증 후, 필드에러 있음")
    @ParameterizedTest
    @MethodSource("provideDateCases")
    public void variousValidateDateTest(String date, String errorCode) {
        roadInfoSearchDto.setDate(date);
        validator.validate(roadInfoSearchDto, errors);
        assertEquals(errorCode, errors.getFieldError("date").getCode());
    }

    private static Stream<Arguments> provideDateCases() {
        return Stream.of(
                Arguments.of(null, "isNull"), // 날짜가 null인 경우
                Arguments.of("   ", "isNull"), // 날짜가 공백인 경우
                Arguments.of("2022/12/31", "wrongDateForm"), // 날짜 형식이 잘못된 경우
                Arguments.of(getTomorrowDate(), "exceedDate") // 미래 날짜를 입력한 경우
        );
    }

    @DisplayName("정상 시간 입력 -> 검증 후, 필드에러 없음")
    @Test
    public void testValidateTime_validTime() {
        roadInfoSearchDto.setDate("20220203");
        roadInfoSearchDto.setTime(9);
        validator.validate(roadInfoSearchDto, errors);
        assertFalse(errors.hasFieldErrors("time"));
    }

    @DisplayName("비정상 시간 입력 -> 검증 후, 필드에러 있음")
    @ParameterizedTest
    @MethodSource("provideTimeCases")
    public void variousValidateTimeTest(String date, Integer time, String errorCode) {
        roadInfoSearchDto.setDate(date);
        roadInfoSearchDto.setTime(time);
        validator.validate(roadInfoSearchDto, errors);
        assertTrue(errors.hasFieldErrors("time"));
        assertEquals(errorCode, errors.getFieldError("time").getCode());
    }

    private static Stream<Arguments> provideTimeCases() {
        return Stream.of(
                Arguments.of("20220201", null, "isNull"), // 시간이 null인 경우
                Arguments.of("20220201", -1, "exceedTimeRange"), // 시간이 0 ~ 23을 벗어나는 경우
                Arguments.of("20230201", 24, "exceedTimeRange"), // 시간이 0 ~ 23을 벗어나는 경우
                Arguments.of(getTodayDate(), getNowTime(), "exceedNowTime") // 현재 시간인 경우거나, 넘어가는 경우
        );
    }

    private static String getTomorrowDate() {
        return LocalDate.now()
                .plusDays(1)
                .format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }

    private static String getTodayDate() {
        return LocalDate.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }

    private static Integer getNowTime() {
        return Integer.parseInt(LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("HH")));
    }

}