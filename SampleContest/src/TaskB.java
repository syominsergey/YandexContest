import java.io.*;

public class TaskB {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("input.txt"));
        try {
            String line = reader.readLine();
            String[] fields = line.split(" ");
            String aString = fields[0];
            String bString = fields[1];
            long aLong = Long.parseLong(aString);
            long bLong = Long.parseLong(bString);
            long sum = aLong + bLong;
            FileWriter writer = new FileWriter("output.txt");
            try {
                writer.write(Long.toString(sum));
            } finally {
                writer.close();
            }
        } finally {
            reader.close();
        }
    }
}
