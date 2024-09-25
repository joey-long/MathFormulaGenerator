import java.util.List;

//负责判断运算式是否已经存在的类
public class ExistJudge {
    public boolean existJudge(List<Expression> allExpression, Expression expression) {

        if (allExpression.isEmpty()) return false;

        for (Expression existingExpression : allExpression) {
            if (!compareNumberCount(expression, existingExpression)) continue;
            if (!compareResult(expression, existingExpression)) continue;
            if (!compareAllOperatorFix(expression, existingExpression)) continue;
            if (!compareAllNumberFix(expression, existingExpression)) continue;
            // 如果所有条件都相同，说明表达式已存在
            return true;
        }
        return false;
    }
    private boolean compareResult(Expression e1, Expression e2) {
        return e1.result.numerator == e2.result.numerator && e1.result.denominator == e2.result.denominator;
    }

    private boolean compareNumberCount(Expression e1, Expression e2) {
        return e1.numberCount == e2.numberCount;
    }

    private boolean compareAllOperatorFix(Expression e1, Expression e2) {
        return e1.allOperatorFix.equals(e2.allOperatorFix);
    }

    private boolean compareAllNumberFix(Expression e1, Expression e2) {
        for (Number num1 : e1.allNumberFix) {
            boolean foundMatch = false;
            for (Number num2 : e2.allNumberFix) {
                if (num1.equals(num2)) {
                    foundMatch = true;
                    break;
                }
            }
            // 如果未找到相同的运算数
            if (!foundMatch) return false;
        }
        return true;
    }
}
