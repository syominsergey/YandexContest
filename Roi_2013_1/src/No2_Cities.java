import java.io.File;
import java.io.*;

public class No2_Cities
{
    
    private File working_dir;

    public No2_Cities(File working_dir)
    {
        this.working_dir = working_dir;
    }
    
    boolean[][] readCities() throws IOException {
        BufferedReader in = new BufferedReader(new FileReader(new File(working_dir, "cities.in")));
        try {
            String line = in.readLine();
            int size = Integer.parseInt(line);
            boolean[][] result = new boolean[size][];
            for(int rowNum=0; rowNum<size; rowNum++){
                boolean[] row = new boolean[size];
                line = in.readLine();
                for(int colNum=0; colNum<size; colNum++){
                    row[colNum] = (line.charAt(colNum)=='C');
                }
                result[rowNum] = row;
            }
            return result;
        } finally {
            in.close();
        }
    }
    
    int countCities(boolean[][] map){
        int cityCount = 0;
        for(int row = 0; row<map.length; row++){
            for(int col = 0; col<map[row].length; col++){
                if(map[row][col]){
                    cityCount++;
                }
            }
        }
        return cityCount;
    }
    
    int findBorderCell(boolean[][] map, int cityCount){
        int targetCityCount = cityCount / 2;
        int borderCellNum = 0;
        for(int row = 0; row<map.length; row++){
            for(int col = 0; col<map[row].length; col++){
                if(map[row][col]){
                    targetCityCount--;
                    if(targetCityCount==0){
                        return borderCellNum;
                    }
                }
                borderCellNum++;
            }
        }
        return -1;
    }
    
    void writeMap(int mapSize, int borderCellNum) throws IOException {
        PrintWriter pw = new PrintWriter(new File(working_dir,"cities.out"));
        try {
            int cellNum = 0;
            for(int row = 0; row<mapSize; row++){
                for(int col = 0; col<mapSize; col++){
                    if(cellNum<=borderCellNum){
                        pw.write('1');
                    } else {
                        pw.write('2');
                    }
                    cellNum++;
                }
                pw.println();
            }
        } finally {
            pw.close();
        }
    }

    public void run() throws Exception
    {
        boolean[][] map = readCities();
        int cityCount =countCities(map);
        int borderCellNum = findBorderCell(map, cityCount);
        writeMap(map.length, borderCellNum);
    }
    
    public static void main(String[] args) throws Exception {
        File working_dir;
        if("Dalvik".equals(System.getProperty("java.vm.name"))){
            working_dir = new File("/storage/emulated/0/AppProjects/YandexContest/Roi_2013_1/working-dir");
        } else {
            working_dir = new File(".");
        }
        new No2_Cities(working_dir).run();
    }
}
