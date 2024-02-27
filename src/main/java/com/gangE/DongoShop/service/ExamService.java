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
