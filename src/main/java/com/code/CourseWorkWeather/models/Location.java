package com.code.CourseWorkWeather.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;


    @Column(name = "name")
    private String name;

    @Column(name = "country")
    private String country;

    public Location(String name, String country) {
        this.name = name;
        this.country = country;
    }

    @Override
    public String toString() {
        return name;
    }
}
