package com.example.wassimzoghlami.Controller;


import com.example.wassimzoghlami.Entity.Parking;
import com.example.wassimzoghlami.Entity.Personnel;
import com.example.wassimzoghlami.Entity.Zone;
import com.example.wassimzoghlami.Repository.ParkingRepository;
import com.example.wassimzoghlami.Repository.PersonnelRepository;
import com.example.wassimzoghlami.Repository.ZoneRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/Parking")
@AllArgsConstructor


public class RestController {
    @Autowired
    PersonnelRepository personnelRepository;
    @Autowired
    ParkingRepository parkingRepository;
    @Autowired
    ZoneRepository zoneRepository;

    @PostMapping("/ajouterPersonnel")
    public Personnel ajouterPersonnel(@RequestBody Personnel personnel) {
        return personnelRepository.save(personnel);
    }

    @PostMapping("/ajoutParkingetZones")
    public void ajoutParkingetZones(@RequestBody Parking parking) {
        List<Zone> zones = parking.getZones();
        zones.stream().forEach(zone -> zone.setParking(parking));
        parking.setZones(parking.getZones());
        parkingRepository.save(parking);

        zoneRepository.saveAll(parking.getZones());
    }

    @PostMapping("/affecterPZ/{idzone}/{idGarde}")
    public void affecterPersonnelZone(@PathVariable int idzone, @PathVariable int idGarde) {
        Zone zone = zoneRepository.findById(idzone).orElse(null);
        Personnel garde = personnelRepository.findById(idGarde).orElse(null);
        if (zone != null && garde != null) {
            zone.getPersonnels().add(garde);
            zoneRepository.save(zone);

        }
    }

    @PostMapping ("/getAllPersonnelByParking")
    public List<Personnel> getAllPersonnelByParking(@RequestBody Parking parking){
        Parking parking1 = parkingRepository.findById(parking.getId()).orElse(null);
        List<Zone> zones = parking1.getZones();
        List<Personnel> personnels=new ArrayList<Personnel>();
        // List<Personnel> personnels2=parking.getZones();

        zones.stream().forEach(zone -> zone.getPersonnels().forEach(personnel -> personnels.add(personnel)));
        return personnels;

    }


    @GetMapping("/nombreParAdresse/{adresse}")
    public int getNbrGardeJour(@PathVariable String adresse ){
        Parking parking = parkingRepository.findByAdresse(adresse);
        if(parking!=null){
            List<Zone> zones = parking.getZones();
            List<Personnel> personnels=new ArrayList<Personnel>();
            zones.stream().forEach(zone -> zone.getPersonnels().forEach(personnel -> personnels.add(personnel)));
            personnels.stream().filter(personnel -> personnel.getPoste().equals("GARDE_JOUR"));
            return personnels.size();
        }
        return 0;
    }


}