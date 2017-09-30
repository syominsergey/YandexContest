import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class F_Permutations {

    static class Input {
        int[][] permutations;
        int[] selection;
    }

    Input readInput() throws IOException {
        Input input = new Input();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line = reader.readLine();
        String[] fields = line.split(" ");
        int n = Integer.parseInt(fields[0]);
        int m = Integer.parseInt(fields[1]);
        input.permutations = new int[m][];
        for (int i = 0; i < m; i++) {
            line = reader.readLine();
            fields = line.split(" ");
            int[] permutation = new int[n];
            for (int j = 0; j < n; j++) {
                permutation[j] = Integer.parseInt(fields[j]) - 1;
            }
            input.permutations[i] = permutation;
        }
        line = reader.readLine();
        int k = Integer.parseInt(line);
        line = reader.readLine();
        fields = line.split(" ");
        input.selection = new int[k];
        for (int i = 0; i < k; i++) {
            input.selection[i] = Integer.parseInt(fields[i]) - 1;
        }
        return input;
    }

    int[] composePermutations(int[] a, int[] b){
        int[] result = new int[a.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = b[a[i]];
        }
        return result;
    }

    void solve(Input input){
        if(input.selection.length == 1){
            System.out.println("1");
            return;
        }
        int[][] leftPermutations = new int[input.selection.length][];
        int[][] rightPermutations = new int[input.selection.length][];
        leftPermutations[0] = input.permutations[input.selection[0]];
        for (int i = 1; i < leftPermutations.length; i++) {
            leftPermutations[i] = composePermutations(leftPermutations[i-1], input.permutations[input.selection[i]]);
        }
        rightPermutations[rightPermutations.length-1] = input.permutations[input.selection[input.selection.length-1]];
        for (int i = rightPermutations.length-2; i >=0 ; i--) {
            rightPermutations[i] = composePermutations(input.permutations[input.selection[i]], rightPermutations[i+1]);
        }
        System.out.println(rightPermutations[1][0] + 1);
        for (int i = 1; i <= input.selection.length - 2; i++) {
            int[] leftPermutation = leftPermutations[i-1];
            int[] rightPermutation = rightPermutations[i+1];
            int[] compositePermutation = composePermutations(leftPermutation, rightPermutation);
            System.out.println(compositePermutation[0] + 1);
        }
        System.out.println(leftPermutations[leftPermutations.length-2][0] + 1);
    }

    public void run() throws IOException {
        Input input = readInput();
        solve(input);
    }

    public static void main(String[] args) throws IOException {
        new F_Permutations().run();
    }
}
