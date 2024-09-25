import java.util.List;

// 负责计算的类
public class EvaluateExpression {

    // 计算的主方法
    public Number expressionResult(List<Number> allNumber, List<Character> allOperator, List<Integer> allBracket, int begin, int end) {
        // 没有括号时
        if (allBracket.isEmpty()) {
            return countExpression(allNumber, allOperator, begin, end);
        }
        // 有括号时，先算括号
        else {
            // 先计算括号里的内容
            countExpression(allNumber, allOperator, allBracket.get(0), allBracket.get(1) - 1);
            // 再运算其他部分
            return countExpression(allNumber, allOperator, 0, allOperator.size());
        }
    }

    // 根据提供的范围进行计算
    private Number countExpression(List<Number> allNumber, List<Character> allOperator, int begin, int end) {
        // 第一次遍历，处理乘除
        for (int i = begin; i < allNumber.size() && i < end; i++) {
            if (allOperator.get(i) == '×' || allOperator.get(i) == '÷') {
                Number result = count(allNumber.get(i), allNumber.get(i + 1), allOperator.get(i));
                if (result.numerator < 0 || result.denominator <= 0) {
                    return new Number(-1, -1);
                }
                allNumber.set(i, result);
                allNumber.remove(i + 1);
                allOperator.remove(i);
                // 调整索引来适应移除操作
                end--;
                i--;
            }
        }

        // 第二次遍历，处理加减
        for (int i = 0; i < allNumber.size() && i < end; i++) {
            Number result = count(allNumber.get(i), allNumber.get(i + 1), allOperator.get(i));
            if (result.numerator < 0 || result.denominator <= 0) {
                return new Number(-1, -1);
            }
            allNumber.set(i, result);
            allNumber.remove(i + 1);
            allOperator.remove(i);
            // 调整索引来适应移除操作
            end--;
            i--;
        }
        return allNumber.get(0);  // 返回最终的计算结果
    }

    //处理两个运算数之间的计算
    public static Number count(Number n1, Number n2, char operator) {
        switch (operator) {
            case '÷':
                if (n2.numerator == 0 || n2.denominator == 0) {
                    return new Number(-1, -1);  // 返回非法值
                }
                n1.numerator *= n2.denominator;
                n1.denominator *= n2.numerator;
                break;
            case '×':
                n1.numerator *= n2.numerator;
                n1.denominator *= n2.denominator;
                break;
            case '+':
            case '-':
                n1 = commonDenominator(n1, n2);
                n1.numerator += (operator == '+') ? n2.numerator : -n2.numerator;
                if (n1.numerator < 0 || n1.denominator < 0) {
                    // 返回非法值
                    return new Number(-1, -1);
                }
                break;
        }
        Expression expression = new Expression();
        // 对结果进行化简
        return expression.simplify(n1);
    }

    // 给分数通分
    private static Number commonDenominator(Number n1, Number n2) {
        n1.numerator *= n2.denominator;
        n2.numerator *= n1.denominator;
        n1.denominator *= n2.denominator;
        return n1;
    }
}

