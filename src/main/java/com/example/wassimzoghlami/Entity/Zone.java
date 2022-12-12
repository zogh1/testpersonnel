package com.example.wassimzoghlami.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Zone {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id ;
    private  String ref;
    private  float dimension;
    @OneToMany
    private List<Personnel> personnels ;
    @OneToOne
    private Personnel personnel;
    @ManyToOne
    private Parking parking;
}
