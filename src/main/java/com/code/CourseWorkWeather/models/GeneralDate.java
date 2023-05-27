package com.code.CourseWorkWeather.models;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "general_date")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class GeneralDate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "date")
    private String date;

    @Column(name = "next_date")
    private String nextDate;

    @Column(name = "previous_date")
    private String previousDate;

    public GeneralDate(String date, String nextDate, String previousDate) {
        this.date = date;
        this.nextDate = nextDate;
        this.previousDate = previousDate;
    }
}
