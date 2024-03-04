package com.gangE.DongoShop.controller;

import com.gangE.DongoShop.dto.QDto.ProductIdAndExamDto;
import com.gangE.DongoShop.dto.ExamDto;
import com.gangE.DongoShop.model.Exam;
import com.gangE.DongoShop.model.ExamProduct;
import com.gangE.DongoShop.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/exam")
public class ExamController {

    @Autowired
    private ExamService ExamService;

    @PostMapping("/create/{productId}")
    public Exam addExam( @PathVariable long productId , @RequestBody ExamDto Exam) {
        return ExamService.saveExam(Exam.getTitle(), Exam.getContent(),productId);
    }



    // {id} 는 모두 프로덕트아이디
    @GetMapping("select_all/{productId}")
    public List<ProductIdAndExamDto> SelectAllExamById(@PathVariable Long productId) {
        return ExamService.selectAllExam(productId);
    }


// todo  단어를 삭제 하기위해 product 에있는 관리자 유저가 요청유저인지 확인한다음 식제
    @DeleteMapping("delect/product/{productId}/Exam/{ExamId}")
    public ExamProduct DelectExamById(@PathVariable Long productId, @PathVariable Long ExamId) {
        return ExamService.DelectMyExam(productId,ExamId);
    }
    // todo  단어를 업데이트 하기위해 product 에있는 관리자 유저가 요청유저인지 확인한다음 업데이트

    @PostMapping("update/product/{productId}/Exam/{ExamId}")
    public Optional<Exam> UpdateExamById(@PathVariable Long productId, @PathVariable Long ExamId, @RequestBody ExamDto Exam) {
        return ExamService.UpdateMyExam(productId,ExamId,Exam);
    }



}
