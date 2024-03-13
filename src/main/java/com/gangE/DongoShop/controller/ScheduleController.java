package com.gangE.DongoShop.controller;

import com.gangE.DongoShop.model.Schedule;
import com.gangE.DongoShop.service.UserScheduleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name = "Schedules Controller", description = "스케줄")
@RestController
@RequestMapping("/schedules")
public class ScheduleController {

    @Autowired
    private UserScheduleService userScheduleService;

    // 페이지네이션 추가
    @Operation(summary = "my_select_all paging", description = "나의 스케줄을 모두 가져옵니다.")
    @GetMapping("/my_select_all")
    public List<Schedule> getMySchedule() {
        return userScheduleService.getAllMyScheduleByName();
    }
    @Operation(summary = "create", description = "나의 스케줄을 만듭니다.")
    @PostMapping("/create")
    public Schedule createSchedule(@RequestBody Schedule schedule) {
        return userScheduleService.createSchedule(schedule);
    }
    @Operation(summary = "update", description = "스케줄 아이디를 파람으로 받으며 나의 스케줄을 업데이트 합니다.")
    @PutMapping("/update/{schedule_id}")
    public Schedule updateSchedule(@PathVariable int schedule_id, @RequestBody Schedule schedule) {
        return userScheduleService.updateSchedule(schedule_id, schedule);
    }
    @Operation(summary = "delete", description = "스케줄 아이디를 파람으로 받으며 나의 스케줄을 삭제 합니다.")
    @DeleteMapping("/delete/{schedule_id}")
    public void deleteSchedule(@PathVariable int schedule_id) {
        userScheduleService.deleteSchedule(schedule_id);
    }

}
