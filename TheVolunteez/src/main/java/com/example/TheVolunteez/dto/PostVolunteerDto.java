package com.example.TheVolunteez.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@NoArgsConstructor
public class PostVolunteerDto {
    private String writerId;

    @NotBlank(message = "제목을 입력해주세요.")
    private String title;

    @NotBlank(message = "내용을 입력해주세요.")
    private String description;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @FutureOrPresent
    private Date deadline;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @FutureOrPresent
    private Date startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @FutureOrPresent
    private Date endDate;

    @NotBlank(message = "장소는 필수 값입니다.")
    private String place;

    @NotNull(message = "봉사 시간을 입력해주세요.")
    @Min(value = 1,message = "봉사 시간은 최소 1시간 이상이어야합니다.")
    private int volunteerHour;

    @NotNull(message = "최대 인원 수를 설정해주세요.")
    private int maxPeople;

    @NotBlank(message = "연락 정보는 필수 값입니다.")
    private String contact;

    @Min(value = 0, message = "기간을 다시 설정해주세요.")
    private long period;
    private int currentPeople;

    public PostVolunteerDto(String writerId, String title, String description, Date deadline, Date startDate, Date endDate, String place, int volunteerHour, int maxPeople, int currentPeople, String contact) {
        this.writerId = writerId;
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.startDate = startDate;
        this.endDate = endDate;
        this.place = place;
        this.volunteerHour = volunteerHour;
        this.maxPeople = maxPeople;
        this.contact = contact;
        this.period = (endDate.getTime() - startDate.getTime()) / (1000*60*60*24);
        this.currentPeople = currentPeople;
    }
}
