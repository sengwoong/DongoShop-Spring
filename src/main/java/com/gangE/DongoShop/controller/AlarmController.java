package com.gangE.DongoShop.controller;

import com.gangE.DongoShop.model.Alarm;
import com.gangE.DongoShop.service.AlarmService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "Alarms Controller", description = "유저에게 알람")
@RestController
@RequestMapping("/alarms")
public class AlarmController {

    private final AlarmService alarmService;

    @Autowired
    public AlarmController(AlarmService alarmService) {
        this.alarmService = alarmService;
    }


    @Operation(summary = "alarms paging", description = "모든 알람을 페이징하여 가져옵니다.")
    @GetMapping("/select")
    public ResponseEntity<Page<Alarm>> getAllAlarms(Pageable pageable) {
        Page<Alarm> alarms = alarmService.findAllByOrderByUserIdCreatedAtDesc(pageable);
        return new ResponseEntity<>(alarms, HttpStatus.OK);
    }

    @Operation(summary = "create alarms", description = "유저 아이디를받고 알람을 생성합니다.")
    @PostMapping("create/{userId}")
    public ResponseEntity<Alarm> createAlarm(@PathVariable("userId") int userId, @RequestBody Alarm alarm) {
        Optional<Alarm> createdAlarm = alarmService.createAlarm(userId, alarm);
        return createdAlarm.map(value -> new ResponseEntity<>(value, HttpStatus.CREATED))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    // 알람업데이트

    @Operation(summary = "delect alarms", description = "알람 아이디를 받고 자신의 알람을 지웁니다.")
    @DeleteMapping("delect/{alarmId}")
    public ResponseEntity<Void> deleteAlarm(@PathVariable("alarmId") int alarmId) {
        alarmService.deleteAlarm(alarmId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
