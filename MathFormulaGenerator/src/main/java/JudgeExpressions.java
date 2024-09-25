import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// 负责判题的类
public class JudgeExpressions {
    int correctNum = 0; // 记录正确答案的数量
    int wrongNum = 0;  // 记录错误答案的数量
    List<Integer> correctQuestion = new ArrayList<>(); // 正确题号列表
    List<Integer> wrongQuestion = new ArrayList<>();  // 错误题号列表

    // 判题主方法
    public JudgeExpressions(String exerciseFile, String answerFile) throws IOException {

        // 使用 BufferedReader 读取这两个文件，以便逐行处理。
        BufferedReader exerciseFileRead = new BufferedReader(new FileReader(exerciseFile));
        BufferedReader answerFileRead = new BufferedReader(new FileReader(answerFile));

        // 题号
        int questonNumber = 1;

        while (true) {
            String expressionsString = exerciseFileRead.readLine();
            String answersString = answerFileRead.readLine();
            if (expressionsString == null || answersString == null)
                break;
            int i = 0;
            int j = 0;

            // 跳过题号
            for (i = 0; i < expressionsString.length(); i++) {
                if (expressionsString.charAt(i) == '.')
                    break;
            }
            for (j = 0; j < answersString.length(); j++) {
                if (answersString.charAt(j) == '.')
                    break;
            }
            i++;

            // 标记要读取的范围
            while (j < answersString.length() && (answersString.charAt(j) > '9' || answersString.charAt(j) < '0')) {
                j++;
            }
            int begin = j;
            while (j < answersString.length() && (answersString.charAt(j) <= '9' && answersString.charAt(j) >= '0' ||
                    answersString.charAt(j) == '’' || answersString.charAt(j) == '/')) {
                j++;
            }

            // 计算正确答案
            Number rightAnswer = separateExpression(expressionsString.substring(i));
            // 得到文件中的答案
            Number pathAnswer = getNumber(answersString.substring(begin, j));

            // 如果答案相同
            if (rightJudge(rightAnswer, pathAnswer)) {
                correctNum++;
                correctQuestion.add(questonNumber);
            }
            // 如果答案不相同
            else {
                wrongNum++;
                wrongQuestion.add(questonNumber);
            }
            questonNumber++;
        }
        // 关闭资源
        exerciseFileRead.close();
        answerFileRead.close();
        // 写入文件
        writeResult(correctNum, wrongNum);
    }

    // 将判题结果写入文件
    private void writeResult(int correctNum, int wrongNum) throws IOException {
        FileWriter writeGrade = new FileWriter("Grade.txt");

        // 先用字符串存储写入文件的内容
        StringBuilder writeGradeString = new StringBuilder();

        // 写入正确的题号
        writeGradeString.append("Correct：").append(correctNum).append(" (");
        for (int i = 0; i < correctQuestion.size(); i++) {
            writeGradeString.append(correctQuestion.get(i));
            if (i != correctQuestion.size() - 1)//最末尾没逗号
                writeGradeString.append("，");
        }
        // 写入错误的题号
        writeGradeString.append(")").append("\n").append("Wrong：").append(wrongNum).append(" (");
        for (int i = 0; i < wrongQuestion.size(); i++) {
            writeGradeString.append(wrongQuestion.get(i));
            ;
            if (i != wrongQuestion.size() - 1)
                writeGradeString.append("，");
        }
        writeGradeString.append(")");
        writeGrade.write(writeGradeString.toString());
        writeGrade.close();
    }

    // 比较答案是否相同
    private boolean rightJudge(Number a, Number b) {
        return a.numerator == b.numerator && a.denominator == b.denominator;
    }

    // 将运算式拆分成运算式、运算符、括号，然后调用EvaluateExpression类进行计算
    private Number separateExpression(String expression) {

        // 创建ExpressionSeparate对象
        Expression expressionSeparate = new Expression();

        // 检查是否包含括号
        if (expression.contains("(")) {
            // 如果有括号，找到括号的索引
            int pos = 0;
            for (int i = 0; i < expression.length(); i++) {
                char currentChar = expression.charAt(i);
                if (currentChar == '(') {
                    break;
                }
                if (currentChar == '+' || currentChar == '-' || currentChar == '×' || currentChar == '÷') {
                    pos++;
                }
            }
            expressionSeparate.allBracket.add(pos);
            expressionSeparate.allBracket.add(pos + 2);
        }

        // 遍历剩余表达式
        for (int i = 0; i < expression.length(); i++) {
            char currentChar = expression.charAt(i);
            // 跳过空格
            if (currentChar == ' ') continue;

            if (currentChar == '+' || currentChar == '-' || currentChar == '×' || currentChar == '÷') {
                // 将运算符存入列表
                expressionSeparate.allOperator.add(currentChar);
            } else if (Character.isDigit(currentChar)) {
                // 运算数的起始索引
                int j = i;
                while (j < expression.length() && (Character.isDigit(expression.charAt(j)) ||
                        expression.charAt(j) == '/' || expression.charAt(j) == '’')) {
                    j++;
                }
                // 获取当前运算数的字符串
                String num = expression.substring(i, j);
                // 将字符串转化为Number对象并添加到列表中
                expressionSeparate.allNumber.add(getNumber(num));
                // 更新索引
                i = j - 1;
            }
        }
        // 调用EvaluateExpression类进行计算，并返回结果
        EvaluateExpression evaluateExpression = new EvaluateExpression();
        return evaluateExpression.expressionResult(expressionSeparate.allNumber, expressionSeparate.allOperator, expressionSeparate.allBracket, 0, expressionSeparate.allOperator.size());
    }

    // 将字符串里的运算数转化为Number类
    public static Number getNumber(String num) {
        int index = 0;      //索引
        int integer = 0;    //整数
        int numerator = 0;  //分子
        int denominator = 1;//分母
        while (index < num.length() && num.charAt(index) >= '0' && num.charAt(index) <= '9') {
            index++;
        }
        // 将String里的数字转化为int
        integer = Integer.parseInt(num.substring(0, index));
        // 如果是带分数
        if (index < num.length() && num.charAt(index) == '’') {
            index++;
            int l = index;
            while (index < num.length() && num.charAt(index) >= '0' && num.charAt(index) <= '9') {
                index++;
            }
            numerator = Integer.parseInt(num.substring(l, index));
        }
        // 如果是真分数
        else {
            numerator = integer;
            integer = 0;
        }
        index++;
        if (index < num.length()) denominator = Integer.parseInt(num.substring(index));
        return new Number(integer * denominator + numerator, denominator);
    }
}
