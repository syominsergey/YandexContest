import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class No1_Casting {

    private static class Input {
        int mode;
        int totalCount;
        ArrayList<Integer> countByType;
    }

    private static Input readInput() throws IOException{
        Input input = new Input();
        BufferedReader reader = new BufferedReader(new FileReader(new File(working_dir, "casting.in")));
        String line;
        try {
            line = reader.readLine();
            input.mode = Integer.parseInt(line);
            line = reader.readLine();
        } finally {
            reader.close();
        }
        String[] numbers = line.split(" ");
        input.totalCount = Integer.parseInt(numbers[0]);
        input.countByType = new ArrayList<>(numbers.length-1);
        for (int i = 0; i < numbers.length-1; i++) {
            input.countByType.add(Integer.parseInt(numbers[i+1]));
        }
        return input;
    }
    
    private static File working_dir;

    public static void main(String[] args) throws IOException {
        if("Dalvik".equals(System.getProperty("java.vm.name"))){
            working_dir = new File("/storage/emulated/0/AppProjects/YandexContest/Roi_2013_1/working-dir");
        } else {
            working_dir = new File(".");
        }
        Input input = readInput();
        int answer;
        switch (input.mode){
            case 1: {
                //поиск минимального числа
                //int max = Collections.max(input.countByType);
                //int sum = 0;
                //for (int i = 0; i < input.countByType.size(); i++) {
                //    sum += input.countByType.get(i);
                //}
                //answer = Math.max(0, max + sum - 3*input.totalCount);
                answer = Math.max(
                        0,
                        Math.max(0, input.countByType.get(0) + input.countByType.get(1) - input.totalCount) +
                        Math.max(0, input.countByType.get(0) + input.countByType.get(2) - input.totalCount) -
                        input.countByType.get(0)
                );
                break;
            }
            case 2: {
                //поиск максимального числа
                answer = Collections.min(input.countByType);
                break;
            }
            default:{
                throw new IllegalArgumentException("Неизвестное значение mode " + input.mode);
            }
        }
        PrintWriter printWriter = new PrintWriter(new File(working_dir, "casting.out"));
        try {
            printWriter.print(answer);
        } finally {
            printWriter.close();
        }
    }
}
