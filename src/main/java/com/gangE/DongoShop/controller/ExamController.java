package com.gangE.DongoShop.controller;

import com.gangE.DongoShop.dto.ExamDto;
import com.gangE.DongoShop.dto.WordDto;
import com.gangE.DongoShop.model.Exam;
import com.gangE.DongoShop.model.Word;
import com.gangE.DongoShop.service.ExamService;
import com.gangE.DongoShop.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/exam")
public class ExamController {


    // todo  문제를 저장할때 문제집인 product 에 저장 하기위해 exam_product에 등록
    // todo  문제를 삭제 하기위해 product 에있는 관리자 유저가 요청유저인지 확인한다음 식제
    // todo  문를 업데이트 하기위해 product 에있는 관리자 유저가 요청유저인지 확인한다음 업데이트
    @Autowired
    private ExamService examService;

    @PostMapping("/create")
    public Exam addWord(@RequestBody ExamDto exam) {
        return examService.saveExam(exam.getTitle(), exam.getContent() );
    }

    @GetMapping("select/{id}")
    public Exam getWordById(@PathVariable long id) {
        return examService.getExamById(id);
    }



}
