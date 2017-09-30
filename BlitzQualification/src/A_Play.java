import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class A_Play {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line = reader.readLine();
        int n = Integer.parseInt(line);
        line = reader.readLine();
        String[] numbersS = line.split(" ");
        int[] numbers = new int[numbersS.length];
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = Integer.parseInt(numbersS[i]);
        }
        int petyaSum = 0;
        int vasyaSum = 0;
        int iterCount = n / 3;
        for (int i = 0; i < iterCount; i++) {
            int startCardNum = i * 3;
            int petyaCard = numbers[startCardNum];
            int vasyaCard = numbers[startCardNum+1];
            int somebodyCard = numbers[startCardNum+2];
            petyaSum += petyaCard;
            vasyaSum += vasyaCard;
            if(petyaCard<vasyaCard){
                petyaSum += somebodyCard;
            } else {
                vasyaSum += somebodyCard;
            }
        }
        if(petyaSum>vasyaSum){
            System.out.println("Petya");
        } else {
            System.out.println("Vasya");
        }
    }
}
