package Laberon.coursework.Courseworksecondyear;

import Laberon.coursework.Courseworksecondyear.exception.AmountIncorrectQuestion;
import Laberon.coursework.Courseworksecondyear.packag.Question;
import Laberon.coursework.Courseworksecondyear.service.ExaminerServiceImpl;
import Laberon.coursework.Courseworksecondyear.service.JavaQuestionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ExaminerServiceImplTest {


    private static final int SIZE = 5;

    private List<Question> questionList;

    @Mock
    private JavaQuestionService javaQuestionService;

    @InjectMocks
    private ExaminerServiceImpl examinerService;

    @BeforeEach
    public void beforeEach() {
        questionList = Stream.iterate(1, i -> i + 1)
                .limit(SIZE)
                .map(i -> new Question("What" + i, "That" + i))
                .collect(Collectors.toList());

        lenient().when(javaQuestionService.getAll())
                .thenReturn(questionList);
    }

    @ParameterizedTest
    @MethodSource("incorrectAmount")
    public void getNegative(int incorrectAmount) {
        assertThatExceptionOfType(AmountIncorrectQuestion.class)
                .isThrownBy(() -> examinerService.getQuestion(incorrectAmount));
    }

    @Test
    public void getPositive() {
        when(javaQuestionService.randomQuestion())
                .thenReturn(
                        questionList.get(0),
                        questionList.get(2),
                        questionList.get(3),
                        questionList.get(2),
                        questionList.get(4),
                        questionList.get(1)
                );
        assertThat(examinerService.getQuestion(5))
                .containsExactly(
                        questionList.get(0),
                        questionList.get(2),
                        questionList.get(3),
                        questionList.get(4),
                        questionList.get(1)
                );
        when(javaQuestionService.randomQuestion())
                .thenReturn(
                        questionList.get(1),
                        questionList.get(0),
                        questionList.get(1),
                        questionList.get(3),
                        questionList.get(3),
                        questionList.get(0)
                );
        assertThat(examinerService.getQuestion(3))
                .containsExactly(
                        questionList.get(1),
                        questionList.get(0),
                        questionList.get(3)
                );
    }

    public static Stream<Arguments> incorrectAmount() {
        return Stream.of(
                Arguments.of(-1),
                Arguments.of(SIZE + 10),
                Arguments.of(SIZE + 1)
        );
    }
}
