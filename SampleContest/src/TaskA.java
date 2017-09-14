import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TaskA {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            String line = reader.readLine();
            String[] fields = line.split(" ");
            String aString = fields[0];
            String bString = fields[1];
            long aLong = Long.parseLong(aString);
            long bLong = Long.parseLong(bString);
            long sum = aLong + bLong;
            System.out.print(sum);
        } finally {
            reader.close();
        }
    }
}
