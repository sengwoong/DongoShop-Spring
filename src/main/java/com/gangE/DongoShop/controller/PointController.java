package com.gangE.DongoShop.controller;

import com.gangE.DongoShop.dto.PointDto;
import com.gangE.DongoShop.model.Point;
import com.gangE.DongoShop.service.PointService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = " Point Controller", description = "포인트")
@RestController
@RequestMapping("/point")
public class PointController {

    @Autowired
    private PointService pointService;

    @Operation(summary = "add", description = "포인트를 추가합니다.")
    @PostMapping("/add")
    public int createSchedule(@RequestBody PointDto money) {
      return   pointService.addMyWallet(money);
    }
    @Operation(summary = "add", description = "포인트를 감소 시킵니다.")
    @PutMapping("/subtract")
    public int subtractMyWallet(@RequestBody PointDto money) {
        return   pointService.subtractMyWallet(money);
    }
    @Operation(summary = "add", description = "나의 포인트를 가져옵니다.")
    @GetMapping("/select_my")
    public Point getMySchedule() {
        return pointService.getPointsByCustomerId();
    }
}
