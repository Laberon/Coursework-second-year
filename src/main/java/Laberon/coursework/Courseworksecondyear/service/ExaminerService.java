package Laberon.coursework.Courseworksecondyear.service;

import Laberon.coursework.Courseworksecondyear.packag.Question;

import java.util.Collection;

public interface ExaminerService {

    Collection<Question> getQuestion(int amount);
}
