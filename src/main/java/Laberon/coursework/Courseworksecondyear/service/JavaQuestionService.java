package Laberon.coursework.Courseworksecondyear.service;

import Laberon.coursework.Courseworksecondyear.exception.QuestionAlreadyException;
import Laberon.coursework.Courseworksecondyear.exception.QuestionNotFoundException;
import Laberon.coursework.Courseworksecondyear.packag.Question;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class JavaQuestionService implements QuestionService {

    private final Set<Question> questions;
    private final Random random;

    public JavaQuestionService() {
        this.questions = new HashSet<>();
        this.random = new Random();
    }

    @Override
    public Question add(String question, String answer) {
        return add(new Question(question,answer));
    }

    @Override
    public Question add(Question question) {
        if (!questions.add(question)) {
            throw new QuestionAlreadyException();
        }
        return question;
    }

    @Override
    public Question remove(Question question) {
        if (!questions.remove(question)) {
            throw new QuestionNotFoundException();
        }
        return question;
    }

    @Override
    public Collection<Question> getAll() {
        return new HashSet<>(questions);
    }

    @Override
    public Question randomQuestion() {
        return new ArrayList<>(questions).get(random.nextInt(questions.size()));
    }
}
