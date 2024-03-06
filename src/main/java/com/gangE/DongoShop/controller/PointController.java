package com.gangE.DongoShop.controller;

import com.gangE.DongoShop.dto.PointDto;
import com.gangE.DongoShop.model.Point;
import com.gangE.DongoShop.service.PointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/point")
public class PointController {

    @Autowired
    private PointService pointService;

    @PostMapping("/add")
    public int createSchedule(@RequestBody PointDto money) {
      return   pointService.addMyWallet(money);
    }

    @PutMapping("/subtract")
    public int subtractMyWallet(@RequestBody PointDto money) {
        return   pointService.subtractMyWallet(money);
    }

    @GetMapping("/select_my")
    public Point getMySchedule() {
        return pointService.getPointsByCustomerId();
    }
}
