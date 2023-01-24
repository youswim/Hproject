package com.projec.protest1.validation;

import org.junit.jupiter.api.BeforeEach;
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

    @Test
    public void testValidateRid_validRid() {
        roadInfoSearchDto.setRid("A-12");
        validator.validate(roadInfoSearchDto, errors);
        assertFalse(errors.hasFieldErrors("rid"));
    }

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
                Arguments.of(null, "isNull"),
                Arguments.of("   ", "isNull"),
                Arguments.of("A12", "formMismatch"),
                Arguments.of("Z-12", "noMatchAlphabet"),
                Arguments.of("A-50", "exceedNumber")
        );
    }

    @Test
    public void testValidateDate_validDate() {
        roadInfoSearchDto.setDate("20221231");
        validator.validate(roadInfoSearchDto, errors);
        assertNull(errors.getFieldError("date"));
    }


    @ParameterizedTest
    @MethodSource("provideDateCases")
    public void variousValidateDateTest(String date, String errorCode) {
        roadInfoSearchDto.setDate(date);
        validator.validate(roadInfoSearchDto, errors);
        assertEquals(errorCode, errors.getFieldError("date").getCode());
    }

    private static Stream<Arguments> provideDateCases() {
        return Stream.of(
                Arguments.of(null, "isNull"),
                Arguments.of("   ", "isNull"),
                Arguments.of("2022/12/31", "wrongDateForm"),
                Arguments.of(getTomorrowDate(), "exceedDate")
        );
    }

    @Test
    public void testValidateTime_validTime() {
        roadInfoSearchDto.setDate("20220203");
        roadInfoSearchDto.setTime(9);
        validator.validate(roadInfoSearchDto, errors);
        assertFalse(errors.hasFieldErrors("time"));
    }

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
                Arguments.of("20220201", null, "isNull"),
                Arguments.of("20220201", -1, "exceedTimeRange"),
                Arguments.of("20230201", 24, "exceedTimeRange"),
                Arguments.of(getTodayDate(), getNowTime(), "exceedNowTime")
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