import org.junit.jupiter.api.Test;

import java.io.IOException;

public class MathFormulaGeneratorTest {
    @Test
    void test1() throws IOException {
        MainMathFormulaGenerator.main(new String[]{});
    }

    @Test
    void test2() throws IOException {
        MainMathFormulaGenerator.main(new String[]{"-n", "10"});
    }

    @Test
    void test3() throws IOException {
        MainMathFormulaGenerator.main(new String[]{"-n", "10", "r"});
    }

    @Test
    void test4() throws IOException {
        MainMathFormulaGenerator.main(new String[]{"-n", "10", "-r","10"});
    }

    @Test
    void test5() throws IOException {
        MainMathFormulaGenerator.main(new String[]{"-n", "-20", "-r","2"});
    }

    @Test
    void test6() throws IOException {
        MainMathFormulaGenerator.main(new String[]{"-n", "2", "-r","2"});
    }

    @Test
    void test7() throws IOException {
        MainMathFormulaGenerator.main(new String[]{"-e", "Exercises.txt", "-a","MyAnswers.txt"});
    }

    @Test
    void test8() throws IOException {
        MainMathFormulaGenerator.main(new String[]{"-e", "Exercises.txt", "-a","Answers.txt"});
    }

    @Test
    void test9() throws IOException {
        MainMathFormulaGenerator.main(new String[]{"-n", "10", "-r","10"});
    }

    @Test
    void test10() throws IOException {
        MainMathFormulaGenerator.main(new String[]{"-n", "10000", "-r","10000"});
    }
}
