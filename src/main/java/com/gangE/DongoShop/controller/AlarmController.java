package com.gangE.DongoShop.controller;

import com.gangE.DongoShop.model.Alarm;
import com.gangE.DongoShop.service.AlarmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/alarms")
public class AlarmController {

    private final AlarmService alarmService;

    @Autowired
    public AlarmController(AlarmService alarmService) {
        this.alarmService = alarmService;
    }



    @GetMapping("/select")
    public ResponseEntity<Page<Alarm>> getAllAlarms(Pageable pageable) {
        Page<Alarm> alarms = alarmService.findAllByOrderByUserIdCreatedAtDesc(pageable);
        return new ResponseEntity<>(alarms, HttpStatus.OK);
    }

    @PostMapping("create/{userId}")
    public ResponseEntity<Alarm> createAlarm(@PathVariable("userId") int userId, @RequestBody Alarm alarm) {
        Optional<Alarm> createdAlarm = alarmService.createAlarm(userId, alarm);
        return createdAlarm.map(value -> new ResponseEntity<>(value, HttpStatus.CREATED))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("update/{id}")
    public ResponseEntity<Alarm> updateAlarm(@PathVariable("id") int id, @RequestBody Alarm alarm) {
        Optional<Alarm> updatedAlarm = alarmService.updateAlarm(id, alarm);
        return updatedAlarm.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("delect/{id}")
    public ResponseEntity<Void> deleteAlarm(@PathVariable("id") int id) {
        alarmService.deleteAlarm(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
