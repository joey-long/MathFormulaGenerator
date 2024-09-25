import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// 负责统筹所有运算式生成并写入文件的类
public class ExpressionSet {

    // 所有式子的列表
    List<Expression> allExpression = new ArrayList<>();

    // 创建文件
    FileWriter expressionsTxt = new FileWriter("Exercises.txt");
    FileWriter answersTxt = new FileWriter("Answers.txt");

    StringBuilder expressionsString = new StringBuilder();
    StringBuilder answersString = new StringBuilder();


    public ExpressionSet(int questionCount, int range) throws IOException {

        // 生成questionCount条式子
        for (int i = 0; i < questionCount; i++) {
            // 生成一条式子
            Expression expression = new Expression(range);

            // 如果式子结果不是负数且没有出现过
            ExistJudge existJudge = new ExistJudge();
            if (expression.result.denominator > 0 && !existJudge.existJudge(allExpression, expression)) {
                allExpression.add(expression);
                // 添加运算式到运算式字符串中
                expressionsWrite(expressionsString, allExpression.get(i), i);
                // 添加答案到答案字符串中
                answersWrite(answersString, allExpression.get(i), i);
            }
            // 如果不合法
            else {
                i--;
            }
        }
        expressionsTxt.write(expressionsString.toString());
        answersTxt.write(answersString.toString());
        expressionsTxt.close();
        answersTxt.close();
    }

    // 运算式写入运算式字符串
    private void expressionsWrite(StringBuilder expressionsString, Expression expression, int i) {
        expressionsString.append(i + 1).append(". ");
        //同个位置判断顺序：'(' 运算数 运算符 ')'
        // 左右括号判断（0左1右）
        int judgeBracket = 0;
        for (int j = 0; j < expression.numberCount; j++) {
            // 添加左括号
            if (expression.allBracket.size() == 2 && expression.allBracket.get(0) == j && judgeBracket == 0) {
                expressionsString.append("(");
                judgeBracket++;
            }
            // 添加运算数
            // 自然数
            if (expression.allNumberFix.get(j).denominator == 1 || expression.allNumberFix.get(j).numerator == 0) {
                expressionsString.append(expression.allNumberFix.get(j).numerator);
            }
            // 分数
            else {
                // 判断真假分数
                int judge = expression.allNumberFix.get(j).numerator / expression.allNumberFix.get(j).denominator;
                // judge<1,真分数
                if (judge < 1) {
                    expressionsString.append(expression.allNumberFix.get(j).numerator).append("/").append(expression.allNumberFix.get(j).denominator);
                }
                // judge>=1,假分数
                else {
                    // 提取假分数的整数部分judge后，需要修改分数部分的分子
                    int fractionNumerator = expression.allNumberFix.get(j).numerator % expression.allNumberFix.get(j).denominator;
                    Number temp = expression.simplify(new Number(fractionNumerator, expression.allNumberFix.get(j).denominator));
                    expressionsString.append(judge).append("’").append(temp.numerator).append("/").append(temp.denominator);
                }
            }
            // 添加右括号
            if (expression.allBracket.size() == 2 && expression.allBracket.get(1) == j + 1 && judgeBracket == 1) {
                judgeBracket++;
                expressionsString.append(")");
            }
            // 添加运算符
            if (j < expression.numberCount - 1) {
                expressionsString.append(" ").append(expression.allOperatorFix.get(j)).append(" ");
            }
        }
        expressionsString.append(" =" + "\n");
    }

    // 答案写入答案字符串
    private void answersWrite(StringBuilder answersString, Expression expression, int i) {
        answersString.append(i + 1).append(". ");
        // 自然数
        if (expression.result.denominator == 1) {
            answersString.append(expression.result.numerator).append("\n");

        }
        // 分数
        else {
            // 判断真假分数
            int judge = expression.result.numerator / expression.result.denominator;
            // judge<1,真分数
            if (judge < 1) {
                answersString.append(expression.result.numerator).append("/").append(expression.result.denominator).append("\n");
            }
            // judge>=1,假分数
            else {
                // 提取假分数的整数部分judge后，需要修改分数部分的分子
                int fractionNumerator = expression.result.numerator % expression.result.denominator;
                answersString.append(judge).append("’").append(fractionNumerator).append("/").append(expression.result.denominator).append("\n");
            }
        }
    }
}