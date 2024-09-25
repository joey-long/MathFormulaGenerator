import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// 负责生成运算式的类
public class Expression {
    private final Random random = new Random();
    
    int numberCount = random.nextInt(3) + 2;    // 运算式的数量(2,3,4)
    List<Number> allNumber = new ArrayList<>();       // 运算式列表
    List<Character> allOperator = new ArrayList<>();  //  运算符列表
    List<Integer> allBracket = new ArrayList<>();     // 括号位置列表，位于相同索引运算数的左边

    // 用来复制上面两个列表，作为固定列表，在判断运算式是否已经存在时使用
    List<Number> allNumberFix = new ArrayList<>();
    List<Character> allOperatorFix = new ArrayList<>();

    // 用来存储运算结果
    Number result = new Number(); 

    public Expression(int range) {
        // 生成数字、符号、括号列表
        generateNumber(range);
        generateOperator();
        generateBracket();
        EvaluateExpression evaluateExpression = new EvaluateExpression();
        result = evaluateExpression.expressionResult(allNumber, allOperator, allBracket, 0, allOperator.size());
    }
    public Expression() {
    }

    // 随机生成自然数或真分数
    private void generateNumber(int range) {
        for (int i = 0; i < numberCount; i++) {
            Number number = new Number();
            boolean isFraction = random.nextBoolean();
            // 生成分数(分母不为0)
            if (isFraction) {
                number.numerator = random.nextInt(range * (range - 1) -1) + 1;

                //
                int productNumberCount = 0;
                do{
                    number.denominator = random.nextInt(range - 1) + 1;
                    if(productNumberCount++ == 10000000) System.out.println("参数r太小啦");
                }while(number.numerator % number.denominator == 0 || number.numerator / number.denominator >= range);
                // 调用simplify方法化简分数
                number = simplify(number);
            }
            // 生成自然数
            else {
                number.numerator = random.nextInt(range - 1) + 1;
                number.denominator = 1;
            }
            allNumber.add(number);
        }
        // 深拷贝 allNumber 到 allNumberFix
        for (Number num : allNumber) {
            // 这里为每个 num 创建一个新的 Number 对象，确保 allNumberFix 拷贝的是新的对象
            allNumberFix.add(new Number(num.numerator, num.denominator));
        }

    }

    // 约分
    public Number simplify(Number number) {
        int numerator = number.numerator;
        int denominator = number.denominator;
        int gcd = gcd(numerator, denominator);
        number.numerator = numerator / gcd;
        number.denominator = denominator / gcd;
        return number;
    }
    // 求最大公因数
    private int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    // 随机生成运算符
    private void generateOperator(){
        Character[] operator = new Character[]{'+', '-', '×', '÷'}; //运算符号
        // 运算符数量是运算式数量减一
        for (int i = 0; i < numberCount - 1; i++) {
            allOperator.add(operator[random.nextInt(operator.length)]);
        }
        allOperatorFix = new ArrayList<>(allOperator);
    }

    // 随机生成括号
    private void generateBracket(){
        // 括号个数0或1
        if(numberCount > 2 && random.nextBoolean()) {
            int index = random.nextInt(numberCount - 1);
            allBracket.add(index);
            allBracket.add(index + 2);
        }
    }
}