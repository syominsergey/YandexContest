import java.io.*;
import java.text.*;
import java.util.*;

public class No3_SumOfNumbers
{
    
    File working_dir;

    public No3_SumOfNumbers(File working_dir)
    {
        this.working_dir = working_dir;
    }
    
    public static class LongNumber {
        byte[] ciphers;
        int start_pos;

        public LongNumber(byte[] ciphers, int start_pos)
        {
            this.ciphers = ciphers;
            this.start_pos = start_pos;
        }
        
        @Override
        public String toString()
        {
            return String.format("LongNumber{ciphers=%s, start_pos=%s}", Arrays.toString(ciphers), start_pos);
        }
    }
    
    LongNumber readNumberFromFile(File file) throws IOException {
        int size = (int) file.length();
        if(size==0){
            throw new IOException("входной файл имеет нулевой размер");
        }
        PushbackInputStream in = new PushbackInputStream(new BufferedInputStream(new FileInputStream(file)), 1);
        try {
            int nextCipher = -1;
            while(true){
                if(size==0){
                    throw new IllegalArgumentException("во входном файле не обнаружено цифр");
                }
                nextCipher = in.read();
                if(nextCipher==-1){
                    throw new IOException("Преждевременное окончание файла!");
                }
                if(nextCipher>'9'){
                    size--;
                    continue;
                }
                if(nextCipher<'0'){
                    size--;
                    continue;
                }
                in.unread(nextCipher);
                break;
            }
            byte[] number = new byte[size];
            int start_pos;
            for(start_pos = size-1; start_pos>=0; start_pos--){
                nextCipher = in.read();
                if(nextCipher==-1){
                    throw new IOException("Преждевременное окончание файла!");
                }
                if(nextCipher>'9'){
                    start_pos++;
                    break;
                }
                if(nextCipher<'0'){
                    start_pos++;
                    break;
                }
                number[start_pos] = (byte) (nextCipher - '0');
            }
            return new LongNumber(number, start_pos);
        } finally {
            in.close();
        }
    }
    
    void incrementNumber(LongNumber number){
        for(int i = number.start_pos; i<number.ciphers.length; i++){
            if(number.ciphers[i]<9){
                number.ciphers[i]++;
                return;
            } else {
                number.ciphers[i] = 0;
            }
        }
    }
    
    void decrementNumber(LongNumber number){
        for(int i = number.start_pos; i<number.ciphers.length; i++){
            if(number.ciphers[i]>0){
                number.ciphers[i]--;
                return;
            } else {
                number.ciphers[i] = 9;
            }
        }
    }
    
    boolean checkNumberIsBeautiful(LongNumber number){
        int bound = number.ciphers.length - 1;
        for(int i = number.start_pos; i<bound; i++){
            if(number.ciphers[i]==number.ciphers[i+1]){
                return false;
            }
        }
        return true;
    }
    
    LongNumber computeAnswer(LongNumber number){
        LongNumber answer = new LongNumber(new byte[10], 0);
        if(number.ciphers[number.ciphers.length-1]==1){
            return answer;
        }
        LongNumber numberA = new LongNumber(new byte[number.ciphers.length], number.start_pos);
        numberA.ciphers[numberA.ciphers.length - 1] = 1;
        LongNumber numberB = number;
        numberB.ciphers[numberB.ciphers.length - 1]--;
        while(true){
            try {
                if(!checkNumberIsBeautiful(numberA)){
                    continue;
                }
                if(!checkNumberIsBeautiful(numberB)){
                    continue;
                }
                incrementNumber(answer);
                if(answer.ciphers[answer.ciphers.length-1] != 1){
                    continue;
                }
                if(answer.ciphers[answer.start_pos] != 7){
                    continue;
                }
                answer.ciphers[answer.ciphers.length-1] = 0;
                answer.ciphers[answer.start_pos] = 0;
            } finally {
                //изменение чисел a и b или выход из цикла
                decrementNumber(numberB);
                if(numberB.ciphers[numberB.ciphers.length-1]==0){
                    break;
                }
                incrementNumber(numberA);
            }
        }
        return answer;
    }
    
    void writeAnswer(LongNumber answer, File file) throws IOException {
        PrintWriter pw = new PrintWriter(file);
        try {
            int start_pos = answer.ciphers.length - 1;
            for(; start_pos>=answer.start_pos; start_pos--){
                if(answer.ciphers[start_pos]>0){
                    break;
                }
            }
            if(start_pos==answer.start_pos-1){
                pw.print('0');
                return;
            }
            for(int i = start_pos; i>=answer.start_pos; i--){
                pw.print(answer.ciphers[i]);
            }
        } finally {
            pw.close();
        }
    }
    
    public void run() throws IOException {
        LongNumber number = readNumberFromFile(new File(working_dir, "aplusb.in"));
        LongNumber answer = computeAnswer(number);
        writeAnswer(answer, new File(working_dir, "aplusb.out"));
    }
    
    public static void main(String[] atgs) throws IOException{
        File working_dir;
        if("Dalvik".equals(System.getProperty("java.vm.name"))){
            working_dir = new File("/storage/emulated/0/AppProjects/YandexContest/Roi_2013_1/working-dir");
        } else {
            working_dir = new File(".");
        }
        new No3_SumOfNumbers(working_dir).run();
    }
    
}
