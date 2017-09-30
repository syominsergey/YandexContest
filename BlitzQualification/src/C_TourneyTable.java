import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class C_TourneyTable {

    private static final int UNDEFINED_PLACE = -1;

    public static class IntPair {
        public int a, b;

        public IntPair(int a, int b) {
            this.a = a;
            this.b = b;
        }

        public IntPair() {
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            IntPair intPair = (IntPair) o;

            if (a != intPair.a) return false;
            return b == intPair.b;
        }

        @Override
        public int hashCode() {
            int result = a;
            result = 31 * result + b;
            return result;
        }
    }

    public static class Results {
        /**
         * команды отсортированы по алфавиту
         */
        String[] teamNames;

        Map<IntPair, IntPair> scores;
    }

    /**
     * считать результаты матчей команд
     * @return
     */
    public Results readResults() throws IOException {
        List<String[]> teamPairs = new ArrayList<>();
        List<IntPair> scorePairs = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] fields = line.split(" - ");
            String[] teams = new String[2];
            teams[0] = fields[0];
            teams[1] = fields[1];
            teamPairs.add(teams);
            String[] scoresS = fields[2].split(":");
            IntPair scores = new IntPair(Integer.parseInt(scoresS[0]), Integer.parseInt(scoresS[1]));
            scorePairs.add(scores);
        }
        Map<String, Integer> teamMap = new TreeMap<>();
        for (String[] teamPair : teamPairs) {
            teamMap.put(teamPair[0], -1);
            teamMap.put(teamPair[1], -1);
        }
        String[] teamNames = new String[teamMap.size()];
        int teamNum = 0;
        for (Map.Entry<String, Integer> entry : teamMap.entrySet()) {
            entry.setValue(teamNum);
            teamNames[teamNum] = entry.getKey();
            teamNum++;
        }
        Results results = new Results();
        results.teamNames = teamNames;
        results.scores = new HashMap<>(scorePairs.size());
        for (int i = 0; i < scorePairs.size(); i++) {
            String[] teamPairS = teamPairs.get(i);
            IntPair teamPair = new IntPair(teamMap.get(teamPairS[0]), teamMap.get(teamPairS[1]));
            results.scores.put(teamPair, scorePairs.get(i));
        }
        return results;
    }

    public static class TeamInfo implements Comparable<TeamInfo> {
        String name;
        int scores;
        int winCount;
        int place;

        @Override
        public int compareTo(TeamInfo o) {
            if(this.scores > o.scores){
                return 1;
            } else if (this.scores < o.scores){
                return -1;
            }
            if(this.winCount > o.winCount){
                return 1;
            } else if (this.winCount < o.winCount){
                return -1;
            }
            return 0;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            TeamInfo teamInfo = (TeamInfo) o;

            if (scores != teamInfo.scores) return false;
            return winCount == teamInfo.winCount;
        }

        @Override
        public int hashCode() {
            int result = scores;
            result = 31 * result + winCount;
            return result;
        }
    }

    public static class TableData {
        TeamInfo[] teams;
        char[][] summary;
    }

    TableData computeTable(Results results){
        TableData tableData = new TableData();
        tableData.teams = new TeamInfo[results.teamNames.length];
        for (int i = 0; i < tableData.teams.length; i++) {
            tableData.teams[i] = new TeamInfo();
            tableData.teams[i].place = UNDEFINED_PLACE;
            tableData.teams[i].name = results.teamNames[i];
        }
        tableData.summary = new char[tableData.teams.length][];
        for (int i = 0; i < tableData.summary.length; i++) {
            tableData.summary[i] = new char[tableData.teams.length];
            for (int i1 = 0; i1 < tableData.summary[i].length; i1++) {
                tableData.summary[i][i1] = ' ';
            }
            tableData.summary[i][i] = 'X';
        }
        for (Map.Entry<IntPair, IntPair> oneGameEntry : results.scores.entrySet()) {
            IntPair teams = oneGameEntry.getKey();
            IntPair scores = oneGameEntry.getValue();
            if(scores.a > scores.b){
                tableData.teams[teams.a].winCount++;
                tableData.teams[teams.a].scores+=3;
                tableData.summary[teams.a][teams.b]='W';
                tableData.summary[teams.b][teams.a]='L';
            } else if (scores.a < scores.b) {
                tableData.teams[teams.b].winCount++;
                tableData.teams[teams.b].scores+=3;
                tableData.summary[teams.b][teams.a]='W';
                tableData.summary[teams.a][teams.b]='L';
            } else {
                tableData.teams[teams.a].scores+=1;
                tableData.teams[teams.b].scores+=1;
                tableData.summary[teams.b][teams.a]='D';
                tableData.summary[teams.a][teams.b]='D';
            }
        }
        TeamInfo[] teamsClone = tableData.teams.clone();
        Arrays.sort(teamsClone);
        teamsClone[teamsClone.length-1].place = 1;
        for (int i = teamsClone.length-2; i >= 0 ; i--) {
            if(teamsClone[i].equals(teamsClone[i+1])){
                teamsClone[i].place = teamsClone[i+1].place;
            } else {
                if(teamsClone[i+1].place == 3){
                    break;
                }
                teamsClone[i].place = teamsClone[i+1].place + 1;
            }
        }
        return tableData;
    }

    void writeTable(TableData tableData) throws IOException {
        int teamNameColumnWidth = 0;
        int scoreColumnWidth = 0;
        int numColumnWidth = Integer.toString(tableData.teams.length).length();
        for (TeamInfo team : tableData.teams) {
            int length = team.name.length();
            if(teamNameColumnWidth < length){
                teamNameColumnWidth = length;
            }
            int scoresLength = Integer.toString(team.scores).length();
            if(scoreColumnWidth < scoresLength){
                scoreColumnWidth = scoresLength;
            }
        }
        teamNameColumnWidth++;
        StringBuilder sb = new StringBuilder();
        sb.append("+");
        for (int i = 0; i < numColumnWidth; i++) {
            sb.append("-");
        }
        sb.append("+");
        for (int i = 0; i < teamNameColumnWidth; i++) {
            sb.append("-");
        }
        for (int i = 0; i < tableData.teams.length; i++) {
            sb.append("+-");
        }
        sb.append("+");
        for (int i = 0; i < scoreColumnWidth; i++) {
            sb.append("-");
        }
        sb.append("+-+");
        String delim = sb.toString();
        sb.setLength(0);
        for (int i = 0; i < tableData.teams.length; i++) {
            System.out.println(delim);
            sb.append("|");
            TeamInfo team = tableData.teams[i];
            String teamNum = Integer.toString(i+1);
            for (int j = 0; j < numColumnWidth - teamNum.length(); j++) {
                sb.append(" ");
            }
            sb.append(teamNum);
            sb.append("|");
            sb.append(team.name);
            for (int j = 0; j < teamNameColumnWidth - team.name.length(); j++) {
                sb.append(" ");
            }
            sb.append("|");
            for (int i1 = 0; i1 < tableData.summary[i].length; i1++) {
                sb.append(tableData.summary[i][i1]);
                sb.append("|");
            }
            String scores = Integer.toString(team.scores);
            for (int j = 0; j < scoreColumnWidth - scores.length(); j++) {
                sb.append(" ");
            }
            sb.append(scores);
            sb.append("|");
            if(team.place == UNDEFINED_PLACE){
                sb.append(" ");
            } else {
                sb.append(team.place);
            }
            sb.append("|");
            System.out.println(sb.toString());
            sb.setLength(0);
        }
        System.out.println(delim);
    }

    public void run() throws IOException {
        Results results = readResults();
        TableData tableData = computeTable(results);
        writeTable(tableData);
    }

    public static void main(String[] args) throws IOException {
        new C_TourneyTable().run();
    }
}
