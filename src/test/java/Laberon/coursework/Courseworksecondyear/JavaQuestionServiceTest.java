package Laberon.coursework.Courseworksecondyear;

import Laberon.coursework.Courseworksecondyear.exception.QuestionAlreadyException;
import Laberon.coursework.Courseworksecondyear.exception.QuestionNotFoundException;
import Laberon.coursework.Courseworksecondyear.packag.Question;
import Laberon.coursework.Courseworksecondyear.service.JavaQuestionService;
import Laberon.coursework.Courseworksecondyear.service.QuestionService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class JavaQuestionServiceTest {

    private final QuestionService questionService = new JavaQuestionService();

    @AfterEach
    public void afterEach() {
        questionService.getAll().forEach(questionService::remove);
    }

    @Test
    public void addTest() {
        assertThat(questionService.getAll().isEmpty());
        Question questionFirst = new Question("Question one", "Answer one");
        Question questionSecond = new Question("Question two", "Answer two");
        questionService.add(questionFirst);

        assertThat(questionService.getAll())
                .hasSize(1)
                .contains(questionFirst);

        assertThatExceptionOfType(QuestionAlreadyException.class)
                .isThrownBy(() -> questionService.add(questionFirst.getQuestion(), questionFirst.getAnswer()));
        questionService.add(questionSecond.getQuestion(), questionSecond.getAnswer());

        assertThat(questionService.getAll())
                .hasSize(2)
                .contains(questionFirst, questionSecond);
    }

    @Test
    public void removeTest() {
        assertThat(questionService.getAll().isEmpty());

        assertThatExceptionOfType(QuestionNotFoundException.class)
                .isThrownBy(() -> questionService.remove(new Question("Question one", "Answer one")));

        Question question = add("Question one", "Answer one");

        assertThat(questionService.getAll())
                .hasSize(1)
                .contains(question);

        assertThatExceptionOfType(QuestionNotFoundException.class)
                .isThrownBy(() -> questionService.remove(new Question("Question two", "Answer two")));


        questionService.remove(question);
        assertThat(questionService.getAll().isEmpty());
    }

    @Test
    public void getRandomQuestionTest() {
        assertThat(questionService.getAll().isEmpty());
        for (int i = 1; i <= 3; i++) {
            add("What" + i, "That" + i);
        }
        assertThat(questionService.getAll())
                .hasSize(3)
                .contains(questionService.randomQuestion());
    }

    public Question add(String question, String answer) {
        return questionService.add(new Question(question, answer));
    }
}
