import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

public class A_CombatEngineer {

    private static final int[][] shifts = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    void bfs(int[][] map, int row, int col, int areaCode){
        LinkedList<int[]> queue = new LinkedList<>();
        queue.addFirst(new int[]{row, col});
        while (!queue.isEmpty()){
            int[] curCellPos = queue.removeLast();
            if(map[curCellPos[0]][curCellPos[1]] != 0){
                continue;
            }
            map[curCellPos[0]][curCellPos[1]] = areaCode;
            for (int[] shift : shifts) {
                int nextCellRow = curCellPos[0] + shift[0];
                if((nextCellRow <0) || (nextCellRow >= map.length)){
                    continue;
                }
                int nextCellCol = curCellPos[1] + shift[1];
                if((nextCellCol <0) || (nextCellCol >= map[nextCellRow].length)){
                    continue;
                }
                if(map[nextCellRow][nextCellCol] != 0){
                    continue;
                }
                queue.addFirst(new int[]{nextCellRow, nextCellCol});
            }
        }
    }

    void dfs(int[][] map, int row, int col, int areaCode){
        if((row < 0) || (row >= map.length)){
            return;
        }
        if((col < 0) || (col >= map[row].length)){
            return;
        }
        if(map[row][col] != 0){
            return;
        }
        map[row][col] = areaCode;
        for (int[] shift : shifts) {
            dfs(map, row+shift[0], col+shift[1], areaCode);
        }
    }

    void run() throws FileNotFoundException {
        Scanner scanner = new Scanner(new FileInputStream("input.txt"));
        int height = scanner.nextInt();
        int width = scanner.nextInt();
        int[][] map = new int[height][];
        for (int i = 0; i < map.length; i++) {
            map[i] = new int[width];
        }
        int bombCount = scanner.nextInt();
        for (int i = 0; i < bombCount; i++) {
            int bombRow = scanner.nextInt() - 1;
            int bombCol = scanner.nextInt() - 1;
            map[bombRow][bombCol] = -1;
        }
        int nextAreaCode = 1;
        for (int row = 0; row < map.length; row++) {
            int[] curRow = map[row];
            for (int col = 0; col < curRow.length; col++) {
                if(curRow[col] == 0){
                    //результаты тестирования на одном и том же наборе данных показали, что bfs работает
                    //чуть быстрее dfs
                    //dfs(map, row, col, nextAreaCode);
                    bfs(map, row, col, nextAreaCode);
                    nextAreaCode++;
                }
            }
        }
        System.out.println(nextAreaCode-1);
    }

    public static void main(String[] args) throws FileNotFoundException {
        new A_CombatEngineer().run();
    }
}
