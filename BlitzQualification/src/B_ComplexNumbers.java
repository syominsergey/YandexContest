import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class B_ComplexNumbers {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line = reader.readLine();
        int maxK = Integer.parseInt(line);
        Set<Integer> vals = new HashSet<>();
        int maxDivResult = 0;
        for (int k = 1; k <= maxK; k++) {
            String kS = Integer.toString(k);
            int sum = 0;
            for (int j = 0; j < kS.length(); j++) {
                int cipher = Integer.parseInt(kS.substring(j, j+1));
                sum += cipher;
            }
            int num = 3 * k;
            int denom = sum*sum;
            int divResult = num / denom;
            if(num != (denom * divResult)){
                continue;
            }
            vals.add(divResult);
            if(divResult > maxDivResult){
                maxDivResult = divResult;
            }
        }
        System.out.println("maxDivResult = " + maxDivResult);
        System.out.println("минимальное не полученное значение: ");
        for (int i = 1; i <= maxDivResult; i++) {
            if(!vals.contains(i)){
                //ответ 61
                System.out.println(i);
                break;
            }
        }
    }
}
