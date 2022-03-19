package jpa.service;

import jpa.entitymodels.Student;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

public class StudentServiceTest {
    private static StudentService studentService;
    private static CourseService courseService;

    @BeforeAll
    public static void setUp() {
        studentService = new StudentService();
        courseService = new CourseService();
    }

    @Test
    public void getStudentByEmailTest() {
        // given
        Student expected = new Student();
        expected.setSEmail("hluckham0@google.ru");
        expected.setSName("Hazel Luckham");
        expected.setSPass("X1uZcoIh0dj");

        // when
        Student actual = studentService.getStudentByEmail("hluckham0@google.ru");

        // then
        Assertions.assertEquals(expected, actual);
    }

    @ParameterizedTest
    @CsvSource({"aiannitti7@is.gd, Alexandra Iannitti, TWP4hf5j", "cjaulme9@bing.com, Cahra Jaulme, FnVklVgC6r6", "cstartin3@flickr.com, Clem Startin, XYHzJ1S"})
    void testJUnit5CsvParameters(String email, String name, String pw) {
        // given
        Student expected = new Student();
        expected.setSEmail(email);
        expected.setSName(name);
        expected.setSPass(pw);

        // when
        Student actual = studentService.getStudentByEmail(email);

        // then
        Assertions.assertEquals(expected.getSName(), actual.getSName());
        Assertions.assertEquals(expected.getSEmail(), actual.getSEmail());
        Assertions.assertEquals(expected.getSPass(), actual.getSPass());

//        Assertions.assertEquals(expected, actual);
    }
}
