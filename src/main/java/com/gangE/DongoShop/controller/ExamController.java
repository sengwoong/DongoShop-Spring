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
