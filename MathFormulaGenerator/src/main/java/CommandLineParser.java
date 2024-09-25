import java.util.HashMap;
import java.util.Map;

// 负责读取命令行参数的类
public class CommandLineParser {
    private Map<String, String> argumentMap = new HashMap<>();

    public CommandLineParser(String[] args) {
        parseArgs(args);
    }

    // 解析命令行参数
    private void parseArgs(String[] args) {
        for (int i = 0; i < args.length - 1; i++) {
            if (args[i].startsWith("-")) {
                argumentMap.put(args[i], args[i + 1]);
            }
        }
    }

    // 获取生成题目的数量
    public int getQuestionCount() {
        if (argumentMap.containsKey("-n")) {
            if (Integer.parseInt(argumentMap.get("-n")) < 0) {
                throw new IllegalArgumentException("参数 -n 必须为非负整数");
            }
            return Integer.parseInt(argumentMap.get("-n"));
        } else {
            throw new IllegalArgumentException("参数 -n 未指定");
        }
    }

    // 获取数值范围
    public int getRange() {
        if (argumentMap.containsKey("-r")) {
            if (Integer.parseInt(argumentMap.get("-r")) < 0) {
                throw new IllegalArgumentException("参数 -r 必须为非负整数");
            }
            return Integer.parseInt(argumentMap.get("-r"));
        } else {
            throw new IllegalArgumentException("参数 -r 未指定");
        }
    }

    // 获取练习题文件路径
    public String getExerciseFile() {
        if (argumentMap.containsKey("-e")) {
            return argumentMap.get("-e");
        } else {
            throw new IllegalArgumentException("参数 -e 未指定");
        }
    }

    // 获取答案文件路径
    public String getAnswerFile() {
        if (argumentMap.containsKey("-a")) {
            return argumentMap.get("-a");
        } else {
            throw new IllegalArgumentException("参数 -a 未指定");
        }
    }

    // 检查是否提供了 -n 参数
    public boolean hasNParam() {
        return argumentMap.containsKey("-n");
    }

    // 检查是否提供了 -r 参数
    public boolean hasRParam() {
        return argumentMap.containsKey("-r");
    }

    // 检查是否提供了 -e 参数
    public boolean hasEParam() {
        return argumentMap.containsKey("-e");
    }

    // 检查是否提供了 -a 参数
    public boolean hasAParam() {
        return argumentMap.containsKey("-a");
    }
}
