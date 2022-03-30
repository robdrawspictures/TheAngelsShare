package com.codeclan.example.WhiskyTracker.controllers;

import com.codeclan.example.WhiskyTracker.models.Distillery;
import com.codeclan.example.WhiskyTracker.models.Whisky;
import com.codeclan.example.WhiskyTracker.repositories.DistilleryRepository;
import com.codeclan.example.WhiskyTracker.repositories.WhiskyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class WhiskyController {

    @Autowired
    WhiskyRepository whiskyRepository;
    @Autowired
    DistilleryRepository distilleryRepository;


    @GetMapping(value="/whiskies/{id}")
    public ResponseEntity getWhisky(@PathVariable Long id){
        return new ResponseEntity(whiskyRepository.findById(id), HttpStatus.OK);
    }

    @GetMapping(value="/whiskies")
    public ResponseEntity getByNameOrRegionAndAge(
            @RequestParam(name="region", required = false) String region,
            @RequestParam(name="name", required = false) String name,
            @RequestParam(name="age", required = false) Integer age,
            @RequestParam(name="year", required = false) Integer year) {
        if (name != null) {
            return new ResponseEntity(whiskyRepository.findByDistilleryNameAndAge(name, age), HttpStatus.OK);
        } else if(region != null){
            return new ResponseEntity(whiskyRepository.findByDistilleryRegion(region), HttpStatus.OK);
        } else if (region != null && age != null) {
            return new ResponseEntity(whiskyRepository.findByDistilleryRegionAndAge(region, age), HttpStatus.OK);
        } else if(year != null){
            return new ResponseEntity(whiskyRepository.findByYear(year), HttpStatus.OK);
        } else if(age != null){
            return new ResponseEntity(whiskyRepository.findByAge(age), HttpStatus.OK);
        }
        return new ResponseEntity<>(whiskyRepository.findAll(), HttpStatus.OK);
    }
}
