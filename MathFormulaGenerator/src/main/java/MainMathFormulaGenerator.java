import java.io.IOException;

public class MainMathFormulaGenerator {

    public static void main(String[] args) throws IOException {
        // 读取参数，根据参数进行不同操作
        CommandLineParser parser = new CommandLineParser(args);

        if (args.length < 4) {
            System.out.println("缺少参数");
        }
        // -n -r 参数组合
        else if (parser.hasNParam() && parser.hasRParam()) {
            // 读取 -n 和 -r 所对应的参数
            int questionCount = parser.getQuestionCount();
            int range = parser.getRange();
            // 生成四则运算式
            new ExpressionSet(questionCount, range);
            System.out.println("生成 " + questionCount + " 个题目，数值范围为 0~" + range);
        }

        // -e -a 参数组合
        else if (parser.hasEParam() && parser.hasAParam()) {
            String exercisesFile = parser.getExerciseFile();
            String answersFile = parser.getAnswerFile();
            new JudgeExpressions(exercisesFile, answersFile);
            System.out.println("题目文件 " + exercisesFile + " 答案文件 " + answersFile);
        }
        // 无法同时读取到两个参数
        else {
            System.out.println("参数错误");
        }
    }
}
