package com.example.TheVolunteez.validator;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.example.TheVolunteez.dto.PostVolunteerDto;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class BoardValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return PostVolunteerDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PostVolunteerDto post = (PostVolunteerDto) target;

        if(!StringUtils.hasText(post.getTitle())){
            errors.rejectValue("title", "제목을 입력해주세요.");
        }
        if(!StringUtils.hasText(post.getDescription())){
            errors.rejectValue("description", "내용을 입력해주세요.");
        }
    }

    public boolean formatCheck(String date){
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.setLenient(false);
            sdf.parse(date);
            return true;
        } catch (ParseException e) {
            System.out.println("날짜 형식에 맞지 않습니다. (yyyy-MM-dd)");
            e.printStackTrace();
            return false;
        }
    }
}