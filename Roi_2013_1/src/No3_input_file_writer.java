import java.io.*;

public class No3_input_file_writer
{
    public static void main(String[] args) throws IOException {
        File working_dir;
        if("Dalvik".equals(System.getProperty("java.vm.name"))){
            working_dir = new File("/storage/emulated/0/AppProjects/YandexContest/Roi_2013_1/working-dir");
        } else {
            working_dir = new File(".");
        }
        File out_file=new File(working_dir, "aplusb.in");
        PrintWriter pw = new PrintWriter(out_file);
        try {
            pw.print("239");
        }finally{
            pw.close();
        }
    }
    
}
