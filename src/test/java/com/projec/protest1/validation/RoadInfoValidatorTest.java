package com.projec.protest1.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    @Test
    public void testValidateRid_nullRid() {
        roadInfoSearchDto.setRid(null);
        validator.validate(roadInfoSearchDto, errors);
        assertTrue(errors.hasFieldErrors("rid"));
        assertEquals("isNull", errors.getFieldError("rid").getCode());
    }

    @Test
    public void testValidateRid_blankRid() {
        roadInfoSearchDto.setRid("   ");
        validator.validate(roadInfoSearchDto, errors);
        assertTrue(errors.hasFieldErrors("rid"));
        assertEquals("isNull", errors.getFieldError("rid").getCode());
    }

    @Test
    public void testValidateRid_invalidRidFormat() {
        roadInfoSearchDto.setRid("A12");
        validator.validate(roadInfoSearchDto, errors);
        assertTrue(errors.hasFieldErrors("rid"));
        assertEquals("formMismatch", errors.getFieldError("rid").getCode());
    }

    @Test
    public void testValidateRid_invalidRidAlphabet() {
        roadInfoSearchDto.setRid("Z-12");
        validator.validate(roadInfoSearchDto, errors);
        assertTrue(errors.hasFieldErrors("rid"));
        assertEquals("noMatchAlphabet", errors.getFieldError("rid").getCode());
    }

    @Test
    public void testValidateRid_invalidRidNumber() {
        roadInfoSearchDto.setRid("A-50");
        validator.validate(roadInfoSearchDto, errors);
        assertTrue(errors.hasFieldErrors("rid"));
        assertEquals("exceedNumber", errors.getFieldError("rid").getCode());
    }

    @Test
    public void testValidateDate_validDate() {
        roadInfoSearchDto.setDate("20221231");
        validator.validate(roadInfoSearchDto, errors);
        assertNull(errors.getFieldError("date"));
    }

    @Test
    public void testValidateDate_nullDate() {
        roadInfoSearchDto.setDate(null);
        validator.validate(roadInfoSearchDto, errors);
        assertEquals("isNull", errors.getFieldError("date").getCode());
    }

    @Test
    public void testValidateDate_blankDate() {
        roadInfoSearchDto.setDate("   ");
        validator.validate(roadInfoSearchDto, errors);
        assertEquals("isNull", errors.getFieldError("date").getCode());

    }

    @Test
    public void testValidateDate_invalidDateFormat() {
        roadInfoSearchDto.setDate("2022/12/31");
        validator.validate(roadInfoSearchDto, errors);
        assertTrue(errors.hasFieldErrors("date"));
        assertEquals("wrongDateForm", errors.getFieldError("date").getCode());
    }

    @Test
    public void testValidateDate_exceedDate() { // 이거 수정해야 함!
        roadInfoSearchDto.setDate(getTomorrowDate());
        validator.validate(roadInfoSearchDto, errors);
        assertTrue(errors.hasFieldErrors("date"));
        assertEquals("exceedDate", errors.getFieldError("date").getCode());
    }

    @Test
    public void testValidateTime_validTime() {
        roadInfoSearchDto.setDate("20220203");
        roadInfoSearchDto.setTime(9);
        validator.validate(roadInfoSearchDto, errors);
        assertFalse(errors.hasFieldErrors("time"));
    }

    @Test
    public void testValidateDate_nullTime() {
        roadInfoSearchDto.setDate("20220201");
        roadInfoSearchDto.setTime(null);
        validator.validate(roadInfoSearchDto, errors);
        assertTrue(errors.hasFieldErrors("time"));
        assertEquals("isNull", errors.getFieldError("time").getCode());
    }

    @Test
    public void testValidateDate_underTime() {
        roadInfoSearchDto.setDate("20220201");
        roadInfoSearchDto.setTime(-1);
        validator.validate(roadInfoSearchDto, errors);
        assertTrue(errors.hasFieldErrors("time"));
        assertEquals("exceedTimeRange", errors.getFieldError("time").getCode());
    }
    @Test
    public void testValidateDate_upperTime() {
        roadInfoSearchDto.setDate("20230201");
        roadInfoSearchDto.setTime(24);
        validator.validate(roadInfoSearchDto, errors);
        assertTrue(errors.hasFieldErrors("time"));
        assertEquals("exceedTimeRange", errors.getFieldError("time").getCode());
    }
    @Test
    public void testValidateDate_exceedNowTime() {
        roadInfoSearchDto.setDate(getTodayDate());
        roadInfoSearchDto.setTime(Integer.parseInt(getNowTime()));
        validator.validate(roadInfoSearchDto, errors);
        assertTrue(errors.hasFieldErrors("time"));
        assertEquals("exceedNowTime", errors.getFieldError("time").getCode());
    }

    private String getTomorrowDate() {
        return LocalDate.now()
                .plusDays(1)
                .format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }

    private String getTodayDate() {
        return LocalDate.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }

    private String getNowTime() {
        return LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("HH"));
    }

}