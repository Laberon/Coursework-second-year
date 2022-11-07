package Laberon.coursework.Courseworksecondyear.service;

import Laberon.coursework.Courseworksecondyear.exception.AmountIncorrectQuestion;
import Laberon.coursework.Courseworksecondyear.packag.Question;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Service
public class ExaminerServiceImpl implements ExaminerService {

    private final QuestionService questionService;

    public ExaminerServiceImpl(QuestionService questionService) {
        this.questionService = questionService;
    }

    @Override
    public Collection<Question> getQuestion(int amount) {
        if (amount <= 0 || amount > questionService.getAll().size()) {
            throw new AmountIncorrectQuestion();
        }
        Set<Question> questions = new LinkedHashSet<>(amount);
        while (questions.size() < amount) {
            questions.add(questionService.randomQuestion());
        }
        return questions;
    }


}
