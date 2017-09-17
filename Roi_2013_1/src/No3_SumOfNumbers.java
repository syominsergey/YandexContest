import java.io.*;

public class No3_SumOfNumbers
{
    
    File working_dir;

    public No3_SumOfNumbers(File working_dir)
    {
        this.working_dir = working_dir;
    }
    
    byte[] readNumberFromFile(File file) throws IOException {
        int size = (int) file.length();
        byte[] number = new byte[size];
        InputStream in = new BufferedInputStream(new FileInputStream(file));
        try {
            for(int i = size-1; i>=0; i--){
                int nextCipher = in.read();
                if(nextCipher==-1){
                    throw new IOException("Преждевременное окончание файла!");
                }
                //todo проверка диапазона цифр
                number[i] = (byte) (nextCipher - '0');
            }
        } finally {
            in.close();
        }
        return number;
    }
    
    void incrementNumber(byte[] number){
        for(int i = 0; i<number.length; i++){
            if(number[i]<9){
                number[i]++;
                return;
            } else {
                number[i] = 0;
            }
        }
    }
    
    void decrementNumber(byte[] number){
        for(int i = 0; i<number.length; i++){
            if(number[i]>0){
                number[i]--;
                return;
            } else {
                number[i] = 9;
            }
        }
    }
    
    boolean checkNumberIsBeautiful(byte[] number){
        int bound = number.length - 1;
        for(int i = 0; i<bound; i++){
            if(number[i]==number[i+1]){
                return false;
            }
        }
        return true;
    }
    
    byte[] computeAnswer(byte[] number){
        byte[] answer = new byte[10];
        if(number[number.length-1]==1){
            return answer;
        }
        byte[] numberA = new byte[number.length];
        numberA[numberA.length - 1] = 1;
        byte[] numberB = (byte[]) number.clone();
        numberB[numberB.length - 1]--;
        while(true){
            try {
                if(!checkNumberIsBeautiful(numberA)){
                    continue;
                }
                if(!checkNumberIsBeautiful(numberB)){
                    continue;
                }
                incrementNumber(answer);
                if(answer[answer.length-1] != 1){
                    continue;
                }
                if(answer[0] != 7){
                    continue;
                }
                answer[answer.length-1] = 0;
                answer[0] = 0;
            } finally {
                //изменение чисел a и b или выход из цикла
                decrementNumber(numberB);
                if(numberB[numberB.length-1]==0){
                    break;
                }
                incrementNumber(numberA);
            }
        }
        return answer;
    }
    
    void writeAnswer(byte[] answer, File file) throws IOException {
        PrintWriter pw = new PrintWriter(file);
        try {
            int start_pos = answer.length - 1;
            for(; start_pos>=0; start_pos--){
                if(answer[start_pos]>0){
                    break;
                }
            }
            if(start_pos==-1){
                pw.print('0');
                return;
            }
            for(int i = start_pos; i>=0; i--){
                pw.print(answer[i]);
            }
        } finally {
            pw.close();
        }
    }
    
    public void run() throws IOException {
        byte[] number = readNumberFromFile(new File(working_dir, "aplusb.in"));
        byte[] answer = computeAnswer(number);
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
