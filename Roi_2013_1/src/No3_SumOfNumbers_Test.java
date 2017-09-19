
import java.io.*;

public class No3_SumOfNumbers_Test
{
    
    No3_SumOfNumbers.LongNumber max_number;
    File working_dir;

    public No3_SumOfNumbers_Test(No3_SumOfNumbers.LongNumber max_number, File working_dir)
    {
        this.max_number = max_number;
        this.working_dir = working_dir;
    }
    
    public void run() throws IOException {
        No3_SumOfNumbers impl = new No3_SumOfNumbers(working_dir);
        PrintWriter pw = new PrintWriter(new File(working_dir, "aplusb.tsv"));
        try {
            while(max_number.ciphers.length>max_number.start_pos){
                No3_SumOfNumbers.LongNumber cur_number = (No3_SumOfNumbers.LongNumber) max_number.clone();
                cur_number.write(pw);
                pw.print("\t");
                No3_SumOfNumbers.LongNumber answer = impl.computeAnswer(cur_number);
                answer.write(pw);
                pw.println();
                max_number.decrement();
                if(max_number.ciphers[max_number.ciphers.length-1]==0){
                    byte[] new_ciphers = new byte[max_number.ciphers.length-1];
                    System.arraycopy(max_number.ciphers, 0, new_ciphers, 0, new_ciphers.length);
                    max_number = new No3_SumOfNumbers.LongNumber(
                        new_ciphers,
                        max_number.start_pos
                    );
                }
            }
        } finally {
            pw.close();
        }
    }
    
    public static void main(String[] args) throws IOException {
        File working_dir;
        if("Dalvik".equals(System.getProperty("java.vm.name"))){
            working_dir = new File("/storage/emulated/0/AppProjects/YandexContest/Roi_2013_1/working-dir");
        } else {
            working_dir = new File(".");
        }
        byte[] ciphers = {0, 0, 0, 1};
        No3_SumOfNumbers.LongNumber max_number = new No3_SumOfNumbers.LongNumber(ciphers, 0);
        new No3_SumOfNumbers_Test(max_number, working_dir).run();
    }
}
