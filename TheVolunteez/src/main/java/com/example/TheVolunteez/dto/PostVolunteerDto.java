package com.example.TheVolunteez.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class PostVolunteerDto {
    private String writerId;
    private String title;
    private String description;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date deadline;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;
    private String place;
    private int volunteerHour;
    private int maxPeople;
    private String contact;
    private long period;

    public PostVolunteerDto(String writerId, String title, String description, Date deadline, Date startDate, Date endDate, String place, int volunteerHour, int maxPeople, String contact) {
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
    }
}
