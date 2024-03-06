package com.gangE.DongoShop.controller;

import com.gangE.DongoShop.dto.QDto.ProductIdAndExamDto;
import com.gangE.DongoShop.dto.ExamDto;
import com.gangE.DongoShop.model.Exam;
import com.gangE.DongoShop.model.ExamProduct;
import com.gangE.DongoShop.service.ExamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "Exam Controller", description = "문제")
@RestController
@RequestMapping("/exam")
public class ExamController {

    @Autowired
    private ExamService ExamService;
    @Operation(summary = "create", description = "프로덕트 아이디를 파람으로 받고 문제를 생성합니다.")
    @PostMapping("/create/{productId}")
    public Exam addExam( @PathVariable long productId , @RequestBody ExamDto Exam) {
        return ExamService.saveExam(Exam.getTitle(), Exam.getContent(),productId);
    }


    @Operation(summary = "select_all", description = "프로덕트 아이디를 파람으로 받고 문제를 리스트로 모두 가져갑니다.")
    @GetMapping("select_all/{productId}")
    public List<ProductIdAndExamDto> SelectAllExamById(@PathVariable Long productId) {
        return ExamService.selectAllExam(productId);
    }


    @Operation(summary = "delect", description = "프로덕트 아이디 와 문제의 아이디를 파람으로 받고 자신의 프로덕트이면 문제를 제거 합니다.")
    @DeleteMapping("delect/product/{productId}/Exam/{ExamId}")
    public ExamProduct DelectExamById(@PathVariable Long productId, @PathVariable Long ExamId) {
        return ExamService.DelectMyExam(productId,ExamId);
    }

    @Operation(summary = "update", description = "프로덕트 아이디와 문제 아이디를 파람으로 받고 문제를 자신의 프로덕트이면 업데이트를 합니다.")
    @PostMapping("update/product/{productId}/Exam/{ExamId}")
    public Optional<Exam> UpdateExamById(@PathVariable Long productId, @PathVariable Long ExamId, @RequestBody ExamDto Exam) {
        return ExamService.UpdateMyExam(productId,ExamId,Exam);
    }



}
