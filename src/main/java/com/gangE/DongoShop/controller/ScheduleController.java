package com.gangE.DongoShop.controller;

import com.gangE.DongoShop.model.Schedule;
import com.gangE.DongoShop.service.UserScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {

    @Autowired
    private UserScheduleService userScheduleService;

    @PostMapping("/create")
    public Schedule createSchedule(@RequestBody Schedule schedule) {
        return userScheduleService.createSchedule(schedule);
    }

    @PutMapping("/update/{id}")
    public Schedule updateSchedule(@PathVariable int id, @RequestBody Schedule schedule) {
        return userScheduleService.updateSchedule(id, schedule);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteSchedule(@PathVariable int id) {
        userScheduleService.deleteSchedule(id);
    }

    @GetMapping("/my_select_all")
    public List<Schedule> getMySchedule() {
        return userScheduleService.getAllMyScheduleByName();
    }
}
