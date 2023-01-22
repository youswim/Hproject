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

        Map<String, Integer> roadIdMap = new HashMap<>();
        roadIdMap.put("A", 24);
        roadIdMap.put("B", 38);
        roadIdMap.put("C", 21);
        roadIdMap.put("D", 46);
        roadIdMap.put("F", 10);

        validateRid(rid, roadIdMap, errors);
        validateDate(date, errors);
        validateTime(date, time, errors);
    }

    private void validateRid(String rid, Map<String, Integer> roadIdMap, Errors errors) {
        if (!rid.matches("[A-Z]-[0-9]{2}")) { // 도로 id 형식이 맞지 않는 경우
            errors.rejectValue("rid", "formMismatch");
            return;
        }

        rid = rid.toUpperCase();

        String[] splittedRid = rid.split("-");
        if (roadIdMap.get(splittedRid[0]) == null) { // 만약, 없는 알파벳이라면
            errors.rejectValue("rid", "noAlphabet");
            return;
        }
        if (roadIdMap.get(splittedRid[0]) < Integer.parseInt(splittedRid[1])) { // 알파벳은 있는데 숫자를 넘긴다면
            errors.rejectValue("rid", "exceedNumber", new Object[]{splittedRid[0], roadIdMap.get(splittedRid[0])}, null);
        }
    }

    private void validateDate(String date, Errors errors) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            sdf.setLenient(false);
            sdf.parse(date);
        } catch(ParseException e) {
            errors.rejectValue("date", "wrongDate");
            return;
        }

        LocalDate today = LocalDate.now();
        LocalDate inputDate = LocalDate.parse(date);
        if (today.isBefore(inputDate)) {
            errors.rejectValue("date","exceedDate");
        }
    }

    private void validateTime(String date, Integer time, Errors errors) {
        if (time < 0 || 24 <= time) {
            errors.rejectValue("time", "exceedMaxTime");
            return;
        }
        LocalDate today = LocalDate.now();
        LocalDate inputDate = LocalDate.parse(date);
        if (today.compareTo(inputDate) == 0 && time < LocalTime.now().getHour()) {
            errors.rejectValue("date", "exceedNowTime");
        }
    }
}
