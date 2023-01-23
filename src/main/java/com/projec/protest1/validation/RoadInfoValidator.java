package com.projec.protest1.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class RoadInfoValidator implements Validator {

    Map<String, Integer> roadIdMap;

    public RoadInfoValidator() {
        roadIdMap = new HashMap<>();
        roadIdMap.put("A", 24);
        roadIdMap.put("B", 38);
        roadIdMap.put("C", 21);
        roadIdMap.put("D", 46);
        roadIdMap.put("F", 10);
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return RoadInfoSearchDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RoadInfoSearchDto roadInfoSearchDto = (RoadInfoSearchDto) target;
        String rid = roadInfoSearchDto.getRid();
        String date = roadInfoSearchDto.getDate();
        Integer time = roadInfoSearchDto.getTime();

        validateRid(rid, errors);
        validateDate(date, errors);
        validateTime(date, time, errors);
    }

    private void validateRid(String rid, Errors errors) {
        if (rid == null || rid.isBlank()) {
            errors.rejectValue("rid", "isNull");
            return;
        }
        if (!rid.matches("[A-Z]-[0-9]{2}")) { // 도로 id 형식이 맞지 않는 경우
            errors.rejectValue("rid", "formMismatch");
            return;
        }

        rid = rid.toUpperCase();

        String[] splittedRid = rid.split("-");
        Integer findValue = roadIdMap.get(splittedRid[0]);
        if (findValue == null) { // 만약, 없는 알파벳이라면
            errors.rejectValue("rid", "noMatchAlphabet");
            return;
        }
        if (findValue < Integer.parseInt(splittedRid[1])) { // 알파벳은 있는데 숫자를 넘긴다면
            errors.rejectValue("rid", "exceedNumber", new Object[]{splittedRid[0], findValue}, null);
        }
    }

    private void validateDate(String date, Errors errors) {
        if (date == null || date.isBlank()) {
            errors.rejectValue("date", "isNull");
            return;
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            sdf.setLenient(false);
            sdf.parse(date);
        } catch(ParseException e) {
            errors.rejectValue("date", "wrongDateForm");
            return;
        }
        LocalDate today = LocalDate.now();
        LocalDate inputDate = LocalDate.parse(changeToLocalDate(date));
        if (today.isBefore(inputDate)) {
            errors.rejectValue("date","exceedDate");
        }
    }

    private void validateTime(String date, Integer time, Errors errors) {
        if (time == null) {
            errors.rejectValue("time", "isNull");
            return;
        }
        if (time < 0 || 24 <= time) {
            errors.rejectValue("time", "exceedMaxTime");
            return;
        }
        if(!errors.hasFieldErrors("date")) {
            LocalDate today = LocalDate.now();
            LocalDate inputDate = LocalDate.parse(changeToLocalDate(date));
            if (today.compareTo(inputDate) == 0 && LocalTime.now().getHour() <= time) {
                errors.rejectValue("date", "exceedNowTime");
            }
        }
    }

    private String changeToLocalDate(String date) {
        date = date.substring(0, 4) + "-" + date.substring(4, 6) + "-" + date.substring(6, 8);
        return date;
    }
}
