import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.List;

public class E_Comments {

    void printNo(){
        System.out.println("NO");
    }

    void printYes(){
        System.out.println("YES");
    }

    List<int[]> findCurrentComments(char[] program){
        List<int[]> result = new ArrayList<>();
        Integer curCommentBegin = null;
        int i = 0;
        while (i<program.length) {
            if(curCommentBegin != null){
                if(program[i] != '*') {
                    i++;
                    continue;
                }
                if(!(((i+1)<program.length) && (program[i+1] == '/'))) {
                    i++;
                    continue;
                }
                i+=2;
                result.add(new int[]{curCommentBegin, i});
                curCommentBegin = null;
            } else {
                if(program[i] != '/'){
                    i++;
                    continue;
                }
                if(!(((i+1)<program.length) && (program[i+1] == '*'))) {
                    i++;
                    continue;
                }
                curCommentBegin = i;
                i+=2;
            }
        }
        if(curCommentBegin != null){
            result.add(new int[]{curCommentBegin, program.length});
        }
        return result;
    }

    int findRightComment(List<int[]> comments, int pos){
        for (int commentNum = 0; commentNum < comments.size(); commentNum++) {
            if(comments.get(commentNum)[0] > pos){
                return commentNum;
            }
        }
        return -1;
    }

    /**
     *
     * @param comments упорядоченный по возврастанию неперекрывающихся интервалов список комментариев
     *                 нулевой элемент каждого массива - начало комментария (включительно)
     *                 первый элемент каждого массива - окончание комментария (исключительно, т.е. первый символ
     *                 после комментария)
     * @param pos
     * @return
     */
    int findSurroundingComment(List<int[]> comments, int pos){
        return findSurroundingComment(comments, pos, findRightComment(comments, pos));
    }

    int findSurroundingComment(List<int[]> comments, int pos, int rightCommentNum){
        if(rightCommentNum == -1){
            if(comments.isEmpty()){
                return -1;
            }
            if(comments.get(comments.size()-1)[1] <= pos){
                return -1;
            }
            return comments.size()-1;
        }
        if(rightCommentNum == 0){
            return -1;
        }
        rightCommentNum--;
        if(comments.get(rightCommentNum)[1] <= pos){
            return -1;
        }
        return rightCommentNum;
    }

    /**
     * Возвращает позицию последнего символа, относящегося к комментарию в предположении о том, что символ с заданной
     * позицией находится внутри комментария
     * @param program
     * @param start
     * @param stop
     * @return первый символ, не относящийся к комметарю, с позицией не более, чем stop. Если все символы до stop относятся
     * к комментарию, то будет возвращен stop
     */
    int findCommentEnd(char[] program, int start, int stop){
        for (int i = start; i<stop; i++){
            if(program[i] == '*'){
               if(((i+1)<stop) && (program[i+1] == '/')){
                   return i+2;
               }
            }
        }
        return stop;
    }

    int findCommentBegin(char[] program, int start, int stop){
        for (int i = start; i<stop; i++){
            if(program[i] == '/'){
                if(((i+1)<stop) && (program[i+1] == '*')){
                    return i;
                }
            }
        }
        return stop;
    }

    private final static boolean DEBUG = false;

    void run() throws FileNotFoundException {
        Scanner scanner = new Scanner(new FileInputStream("input.txt"));
        char[] program = scanner.nextLine().toCharArray();
        if(DEBUG){
            System.out.println("debug: program = " + Arrays.toString(program));
        }
        List<int[]> comments = findCurrentComments(program);
        int n = scanner.nextInt();
        for (int commandNum = 0; commandNum < n; commandNum++) {
            int command = scanner.nextInt();
            int pos = scanner.nextInt() - 1;
            switch (command){
                case 1: {
                    //изменить символ в строке
                    char newChar = scanner.nextLine().charAt(1);
                    program[pos] = newChar;
                    if(DEBUG){
                        System.out.println("debug: program = " + Arrays.toString(program));
                    }
                    int rightCommentNum = findRightComment(comments, pos);
                    int surroundingCommentNum = findSurroundingComment(comments, pos, rightCommentNum);
                    if(surroundingCommentNum == -1){
                        //изменяемый символ находится вне комментария
                        int newCommentBeginPos;
                        switch (newChar){
                            case '*': {
                                if((pos > 0) && (program[pos-1] == '/')){
                                    newCommentBeginPos = pos-1;
                                } else {
                                    newCommentBeginPos = -1;
                                }
                                break;
                            }
                            case '/': {
                                if((pos < program.length-1) && (program[pos + 1] == '*')){
                                    newCommentBeginPos = pos;
                                } else {
                                    newCommentBeginPos = -1;
                                }
                                break;
                            }
                            default: {
                                newCommentBeginPos = -1;
                            }
                            //символ '.' никак не повлияет на расположение комментариев, если добавляется вне комментариев
                        }
                        if(newCommentBeginPos == -1){
                            continue;
                        }
                        //найти ближайшую подстроку, закрывающую комментарий
                        if(rightCommentNum == -1){
                            int commentEnd = findCommentEnd(program, newCommentBeginPos, program.length);
                            comments.add(new int[]{newCommentBeginPos, commentEnd});
                            continue;
                        }
                        int[] rightComment = comments.get(rightCommentNum);
                        int newCommentEndPos = findCommentEnd(program, newCommentBeginPos, rightComment[0]);
                        if(newCommentEndPos == rightComment[0]){
                            rightComment[0] = newCommentBeginPos;
                            continue;
                        }
                        int[] newComment = {newCommentBeginPos, newCommentEndPos};
                        comments.add(rightCommentNum, newComment);
                        continue;
                    }
                    //изменяемый символ находится внутри комментария
                    int[] surroundingComment = comments.get(surroundingCommentNum);
                    if(((pos == surroundingComment[0]) && (newChar != '/')) || ((pos == (surroundingComment[0]+1)) && (newChar != '*'))){
                        int commentBegin = findCommentBegin(program, surroundingComment[0]+1, surroundingComment[1]);
                        if(commentBegin == surroundingComment[1]){
                            comments.remove(surroundingCommentNum);
                            continue;
                        }
                        surroundingComment[0] = commentBegin;
                        continue;
                    }
                    if(((pos == surroundingComment[1]-2) && (newChar != '*')) || ((pos == (surroundingComment[1]-1)) && (newChar != '/'))){
                        if(rightCommentNum == -1){
                            int commentEnd = findCommentEnd(program, pos-1, program.length);
                            surroundingComment[1] = commentEnd;
                            continue;
                        }
                        int[] rightComment = comments.get(rightCommentNum);
                        int commentEnd = findCommentEnd(program, pos-1, rightComment[0]);
                        if(commentEnd == rightComment[0]){
                            surroundingComment[1] = rightComment[1];
                            comments.remove(rightCommentNum);
                            continue;
                        }
                        surroundingComment[1] = commentEnd;
                        continue;
                    }
                    //изменения внутри комментария (нет воздействия на границы)
                    int newCommentEnd;
                    switch (newChar){
                        case '*': {
                            if(program[pos+1] == '/'){
                                newCommentEnd = pos+2;
                            } else {
                                newCommentEnd = -1;
                            }
                            break;
                        }
                        case '/': {
                            if(program[pos-1] == '*'){
                                newCommentEnd = pos+1;
                            } else {
                                newCommentEnd = -1;
                            }
                            break;
                        }
                        default: {
                            newCommentEnd = -1;
                        }
                    }
                    if(newCommentEnd == -1){
                        continue;
                    }
                    int newCommentBegin = findCommentBegin(program, newCommentEnd, surroundingComment[1]);
                    if(newCommentBegin != surroundingComment[1]){
                        comments.add(surroundingCommentNum+1, new int[]{newCommentBegin, surroundingComment[1]});
                    }
                    surroundingComment[1] = newCommentEnd;
                    continue;
                }
                case 2: {
                    //узнать, входит ли символ в комментарий
                    int commentNum = findSurroundingComment(comments, pos);
                    if(commentNum == -1){
                        printNo();
                    } else {
                        printYes();
                    }
                    continue;
                }
                default:
                    throw new IllegalArgumentException("Неизвестный код команды " + command);
            }
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        new E_Comments().run();
    }
}
