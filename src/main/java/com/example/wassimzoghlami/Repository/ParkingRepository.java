package com.example.wassimzoghlami.Repository;

import com.example.wassimzoghlami.Entity.Parking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingRepository extends JpaRepository<Parking,Integer> {
    Parking findByAdresse(String adresse);
}
