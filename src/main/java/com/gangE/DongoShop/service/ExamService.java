package com.gangE.DongoShop.service;

import com.gangE.DongoShop.model.Exam;
import com.gangE.DongoShop.model.Word;
import com.gangE.DongoShop.repository.ExamRepository;
import com.gangE.DongoShop.repository.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ExamService {

    private final ExamRepository examRepository;


    @Autowired
    public ExamService(ExamRepository examRepository) {
        this.examRepository = examRepository;

    }
    // todo  문제를 저장할때 문제집인 product 에 저장 하기위해 exam_product에 등록
    // todo  문제를 삭제 하기위해 product 에있는 관리자 유저가 요청유저인지 확인한다음 식제
    // todo  문를 업데이트 하기위해 product 에있는 관리자 유저가 요청유저인지 확인한다음 업데이트
    public Exam saveExam(String title, String content) {

        if ( content == null || title == null) {
            throw new IllegalArgumentException("단어와 정의를 모두 제공해야합니다.");
        }
        Exam newExam = new Exam();
        newExam.setTitle(title);
        newExam.setContent(content);
        return examRepository.save(newExam);
    }


    public Exam getExamById(long id) {

        Optional<Exam> optionalExam = examRepository.findById(id);
        return optionalExam.orElse(null);
    }

}
