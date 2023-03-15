package com.company;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.lang.Math;

public class Main {

    private static final ArrayList<Team> teams = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        //teamBuilderSportsReference("resources/NHL/HRRegular19-20.csv");
        //teamBuilderSportsReference("resources/NHL/HRPlayoff2020.csv");
        //teamBuilderSportsReferenceNBA("resources/NBA/2020bubble.csv");
        //teamBuilderStandard("resources/NFL/NFL2021.csv");
        //teamBuilderSportsReference("resources/College Football (FBS)/20.csv");
        teamBuilderMM("2023");
        Scanner tRec = new Scanner(System.in);
        //teamBuilderMM2017(tRec.nextLine());
        while (true) {
            String[] t = tRec.nextLine().split("/");
            String t1 = t[0];
            String t2 = t[1];
            gamePicker(t1, t2);
            TgamePicker(t1, t2);
        }
    }

    private static void teamBuilderStandard(String filePath) throws IOException {
        double beta = 10.1;
        //play around with beta value
        File file = new File(filePath);
        Scanner fileReader = new Scanner(file);
        while(fileReader.hasNextLine()) {
            String [] temp = fileReader.nextLine().split(",");
            if (temp[0].equalsIgnoreCase("Team1") || temp[0].equalsIgnoreCase("") || temp[0].contains("week") || temp[0].contains("//")) continue;
            if (temp.length>2) {if (temp[2].equalsIgnoreCase("")) { continue;}}

            int count = teams.size();
            for (Team t: teams) {
                if (t.name.equals(temp[0])) break;
                count--;
            }
            if (count == 0) {
                Team x = new Team(temp[0]);
                teams.add(x);
            }
            count = teams.size();
            for (Team t: teams) {
                if (temp.length>1) {
                    if (t.name.equals(temp[1])) break;
                }
                count--;
            }
            if (count == 0) {
                if (temp.length>1) {
                    Team x = new Team(temp[1]);
                    teams.add(x);
                }

            }


            if (temp.length>2) {
                if (temp[2].equals("1")) {
                    Team winner = teamFinder(temp[0]);
                    Team loser = teamFinder(temp[1]);

                    assert winner != null;
                    double oldWinnerMu = winner.mu;
                    assert loser != null;
                    double oldLoserMu = loser.mu;
                    double oldWinnerSigma = winner.sigma;
                    double oldLoserSigma = loser.sigma;

                    double c = Math.sqrt(2*Math.pow(beta, 2) + Math.pow(oldWinnerSigma, 2) + Math.pow(oldLoserSigma, 2));
                    double t = (oldWinnerMu-oldLoserMu)/c;

                    winner.setMu(oldWinnerMu + ((Math.pow(oldWinnerSigma, 2))/c) * v(t));
                    loser.setMu(oldLoserMu - ((Math.pow(oldLoserSigma, 2))/c) * v(t));
                    winner.setSigma(Math.sqrt((Math.pow(oldWinnerSigma, 2)) * (1 - ((Math.pow(oldWinnerSigma, 2)) / Math.pow(c, 2)) * w(t))));
                    loser.setSigma(Math.sqrt((Math.pow(oldLoserSigma, 2)) * (1 - ((Math.pow(oldLoserSigma, 2)) / Math.pow(c, 2)) * w(t))));
                }
                else {
                    Team winner = teamFinder(temp[1]);
                    Team loser = teamFinder(temp[0]);

                    assert winner != null;
                    double oldWinnerMu = winner.mu;
                    assert loser != null;
                    double oldLoserMu = loser.mu;
                    double oldWinnerSigma = winner.sigma;
                    double oldLoserSigma = loser.sigma;

                    double c = Math.sqrt(2*Math.pow(beta, 2) + Math.pow(oldWinnerSigma, 2) + Math.pow(oldLoserSigma, 2));
                    double t = (oldWinnerMu-oldLoserMu)/c;

                    winner.setMu(oldWinnerMu + ((Math.pow(oldWinnerSigma, 2))/c) * v(t));
                    loser.setMu(oldLoserMu - ((Math.pow(oldLoserSigma, 2))/c) * v(t));
                    winner.setSigma(Math.sqrt((Math.pow(oldWinnerSigma, 2)) * (1 - ((Math.pow(oldWinnerSigma, 2)) / Math.pow(c, 2)) * w(t))));
                    loser.setSigma(Math.sqrt((Math.pow(oldLoserSigma, 2)) * (1 - ((Math.pow(oldLoserSigma, 2)) / Math.pow(c, 2)) * w(t))));
                }
            }
        }
    }

    private static void teamBuilderSportsReference(String filePath) throws IOException {
        double beta = 10.1;
        //play around with beta value
        File file = new File(filePath);
        Scanner fileReader = new Scanner(file);
        while(fileReader.hasNextLine()) {
            String [] temp = fileReader.nextLine().split(",");
            if (temp[0].equalsIgnoreCase("Date") || temp[0].contains("//")) continue;

            int count = teams.size();
            for (Team t: teams) {
                if (t.name.equals(temp[1])) break;
                count--;
            }
            if (count == 0) {
                Team x = new Team(temp[1]);
                teams.add(x);
            }
            count = teams.size();
            for (Team t: teams) {
                if (t.name.equals(temp[3])) break;
                count--;
            }
            if (count == 0) {
                Team x = new Team(temp[3]);
                teams.add(x);

            }

            if (Integer.parseInt(temp[2])>Integer.parseInt(temp[4])) {
                Team winner = teamFinder(temp[1]);
                Team loser = teamFinder(temp[3]);

                assert winner != null;
                double oldWinnerMu = winner.mu;
                assert loser != null;
                double oldLoserMu = loser.mu;
                double oldWinnerSigma = winner.sigma;
                double oldLoserSigma = loser.sigma;

                double c = Math.sqrt(2*Math.pow(beta, 2) + Math.pow(oldWinnerSigma, 2) + Math.pow(oldLoserSigma, 2));
                double t = (oldWinnerMu-oldLoserMu)/c;

                winner.setMu(oldWinnerMu + ((Math.pow(oldWinnerSigma, 2))/c) * v(t));
                loser.setMu(oldLoserMu - ((Math.pow(oldLoserSigma, 2))/c) * v(t));
                winner.setSigma(Math.sqrt((Math.pow(oldWinnerSigma, 2)) * (1 - ((Math.pow(oldWinnerSigma, 2)) / Math.pow(c, 2)) * w(t))));
                loser.setSigma(Math.sqrt((Math.pow(oldLoserSigma, 2)) * (1 - ((Math.pow(oldLoserSigma, 2)) / Math.pow(c, 2)) * w(t))));
            }
            else {
                Team winner = teamFinder(temp[3]);
                Team loser = teamFinder(temp[1]);

                assert winner != null;
                double oldWinnerMu = winner.mu;
                assert loser != null;
                double oldLoserMu = loser.mu;
                double oldWinnerSigma = winner.sigma;
                double oldLoserSigma = loser.sigma;

                double c = Math.sqrt(2*Math.pow(beta, 2) + Math.pow(oldWinnerSigma, 2) + Math.pow(oldLoserSigma, 2));
                double t = (oldWinnerMu-oldLoserMu)/c;

                winner.setMu(oldWinnerMu + ((Math.pow(oldWinnerSigma, 2))/c) * v(t));
                loser.setMu(oldLoserMu - ((Math.pow(oldLoserSigma, 2))/c) * v(t));
                winner.setSigma(Math.sqrt((Math.pow(oldWinnerSigma, 2)) * (1 - ((Math.pow(oldWinnerSigma, 2)) / Math.pow(c, 2)) * w(t))));
                loser.setSigma(Math.sqrt((Math.pow(oldLoserSigma, 2)) * (1 - ((Math.pow(oldLoserSigma, 2)) / Math.pow(c, 2)) * w(t))));
            }
        }
    }

    private static void teamBuilderSportsReferenceNBA(String filePath) throws IOException {
        double beta = 10.1;
        //play around with beta value
        File file = new File(filePath);
        Scanner fileReader = new Scanner(file);
        while(fileReader.hasNextLine()) {
            String [] temp = fileReader.nextLine().split(",");
            if (temp[0].equalsIgnoreCase("Date") || temp[0].contains("//")) continue;

            int count = teams.size();
            String [] uno = temp[1].split(" ");
            String [] dos = temp[3].split(" ");
            String t1 = uno[uno.length-1];
            String t2 = dos[dos.length-1];
            for (Team t: teams) {
                if (t.name.equals(t1)) break;
                count--;
            }
            if (count == 0) {
                Team x = new Team(t1);
                teams.add(x);
            }
            count = teams.size();
            for (Team t: teams) {
                if (t.name.equals(t2)) break;
                count--;
            }
            if (count == 0) {
                Team x = new Team(t2);
                teams.add(x);

            }

            if (Integer.parseInt(temp[2])>Integer.parseInt(temp[4])) {
                Team winner = teamFinder(t1);
                Team loser = teamFinder(t2);

                assert winner != null;
                double oldWinnerMu = winner.mu;
                assert loser != null;
                double oldLoserMu = loser.mu;
                double oldWinnerSigma = winner.sigma;
                double oldLoserSigma = loser.sigma;

                double c = Math.sqrt(2*Math.pow(beta, 2) + Math.pow(oldWinnerSigma, 2) + Math.pow(oldLoserSigma, 2));
                double t = (oldWinnerMu-oldLoserMu)/c;

                winner.setMu(oldWinnerMu + ((Math.pow(oldWinnerSigma, 2))/c) * v(t));
                loser.setMu(oldLoserMu - ((Math.pow(oldLoserSigma, 2))/c) * v(t));
                winner.setSigma(Math.sqrt((Math.pow(oldWinnerSigma, 2)) * (1 - ((Math.pow(oldWinnerSigma, 2)) / Math.pow(c, 2)) * w(t))));
                loser.setSigma(Math.sqrt((Math.pow(oldLoserSigma, 2)) * (1 - ((Math.pow(oldLoserSigma, 2)) / Math.pow(c, 2)) * w(t))));
            }
            else {
                Team winner = teamFinder(t2);
                Team loser = teamFinder(t1);

                assert winner != null;
                double oldWinnerMu = winner.mu;
                assert loser != null;
                double oldLoserMu = loser.mu;
                double oldWinnerSigma = winner.sigma;
                double oldLoserSigma = loser.sigma;

                double c = Math.sqrt(2*Math.pow(beta, 2) + Math.pow(oldWinnerSigma, 2) + Math.pow(oldLoserSigma, 2));
                double t = (oldWinnerMu-oldLoserMu)/c;

                winner.setMu(oldWinnerMu + ((Math.pow(oldWinnerSigma, 2))/c) * v(t));
                loser.setMu(oldLoserMu - ((Math.pow(oldLoserSigma, 2))/c) * v(t));
                winner.setSigma(Math.sqrt((Math.pow(oldWinnerSigma, 2)) * (1 - ((Math.pow(oldWinnerSigma, 2)) / Math.pow(c, 2)) * w(t))));
                loser.setSigma(Math.sqrt((Math.pow(oldLoserSigma, 2)) * (1 - ((Math.pow(oldLoserSigma, 2)) / Math.pow(c, 2)) * w(t))));
            }
        }
    }

    private static void teamBuilderSportsReferenceFBS(String filePath) throws IOException {
        double beta = 10.1;
        //play around with beta value
        File file = new File(filePath);
        Scanner fileReader = new Scanner(file);
        while(fileReader.hasNextLine()) {
            String [] temp = fileReader.nextLine().split(",");
            if (temp[0].equalsIgnoreCase("wk") || temp[0].equalsIgnoreCase("") || temp[0].contains("//")) continue;

            int count = teams.size();
            for (Team t: teams) {
                if (t.name.equals(temp[2])) break;
                count--;
            }
            if (count == 0) {
                Team x = new Team(temp[2]);
                teams.add(x);
            }
            count = teams.size();
            for (Team t: teams) {
                if (t.name.equals(temp[4])) break;
                count--;
            }
            if (count == 0) {
                Team x = new Team(temp[4]);
                teams.add(x);

            }


            Team winner = teamFinder(temp[2]);
            Team loser = teamFinder(temp[4]);

            assert winner != null;
            double oldWinnerMu = winner.mu;
            assert loser != null;
            double oldLoserMu = loser.mu;
            double oldWinnerSigma = winner.sigma;
            double oldLoserSigma = loser.sigma;

            double c = Math.sqrt(2*Math.pow(beta, 2) + Math.pow(oldWinnerSigma, 2) + Math.pow(oldLoserSigma, 2));
            double t = (oldWinnerMu-oldLoserMu)/c;

            winner.setMu(oldWinnerMu + ((Math.pow(oldWinnerSigma, 2))/c) * v(t));
            loser.setMu(oldLoserMu - ((Math.pow(oldLoserSigma, 2))/c) * v(t));
            winner.setSigma(Math.sqrt((Math.pow(oldWinnerSigma, 2)) * (1 - ((Math.pow(oldWinnerSigma, 2)) / Math.pow(c, 2)) * w(t))));
            loser.setSigma(Math.sqrt((Math.pow(oldLoserSigma, 2)) * (1 - ((Math.pow(oldLoserSigma, 2)) / Math.pow(c, 2)) * w(t))));
        }
    }

    private static void teamBuilderMM2017(String year) throws IOException {
        double beta = 10.1;
        //play around with beta value
        File file1 = new File("resources/mens-machine-learning-competition-2019/DataFiles/Teams.csv");
        Scanner fileReader1 = new Scanner(file1);
        while(fileReader1.hasNextLine()) {
            String [] temp = fileReader1.nextLine().split(",");
            if (temp[0].equalsIgnoreCase("Team_Id")) continue;

            int count = teams.size();
            for (Team t: teams) {
                if (t.name.equals(temp[1])) break;
                count--;
            }
            if (count == 0) {
                Team x = new Team(temp[1],temp[0]);
                teams.add(x);
            }
            count = teams.size();
            for (Team t: teams) {
                if (t.name.equals(temp[1])) break;
                count--;
            }
            if (count == 0) {
                Team x = new Team(temp[1],temp[0]);
                teams.add(x);
            }
        }
        File file2 = new File("resources/mens-machine-learning-competition-2019/Prelim2019_RegularSeasonCompactResults/Prelim2019_RegularSeasonCompactResults.csv");
        Scanner fileReader2 = new Scanner(file2);
        while(fileReader2.hasNextLine()) {
            String [] temp = fileReader2.nextLine().split(",");
            if (!temp[0].equalsIgnoreCase(year)) continue;

            if (temp.length>1) {
                Team winner = teamFinderID(temp[2]);
                Team loser = teamFinderID(temp[4]);

                assert winner != null;
                double oldWinnerMu = winner.mu;
                assert loser != null;
                double oldLoserMu = loser.mu;
                double oldWinnerSigma = winner.sigma;
                double oldLoserSigma = loser.sigma;

                double c = Math.sqrt(2*Math.pow(beta, 2) + Math.pow(oldWinnerSigma, 2) + Math.pow(oldLoserSigma, 2));
                double t = (oldWinnerMu-oldLoserMu)/c;

                winner.setMu(oldWinnerMu + ((Math.pow(oldWinnerSigma, 2))/c) * v(t));
                loser.setMu(oldLoserMu - ((Math.pow(oldLoserSigma, 2))/c) * v(t));
                winner.setSigma(Math.sqrt((Math.pow(oldWinnerSigma, 2)) * (1 - ((Math.pow(oldWinnerSigma, 2)) / Math.pow(c, 2)) * w(t))));
                loser.setSigma(Math.sqrt((Math.pow(oldLoserSigma, 2)) * (1 - ((Math.pow(oldLoserSigma, 2)) / Math.pow(c, 2)) * w(t))));
            }
        }
    }

    private static void teamBuilderMM(String year) throws IOException {
        double beta = 10.1;
        String location = "resources/MM/" + year +"/MTeams.csv";
        //play around with beta value
        File file1 = new File(location);
        Scanner fileReader1 = new Scanner(file1);
        while(fileReader1.hasNextLine()) {
            String [] temp = fileReader1.nextLine().split(",");
            if (temp[0].equalsIgnoreCase("TeamID")) continue;

            int count = teams.size();
            for (Team t: teams) {
                if (t.name.equals(temp[1])) break;
                count--;
            }
            if (count == 0) {
                Team x = new Team(temp[1],temp[0]);
                teams.add(x);
            }
            count = teams.size();
            for (Team t: teams) {
                if (t.name.equals(temp[1])) break;
                count--;
            }
            if (count == 0) {
                Team x = new Team(temp[1],temp[0]);
                teams.add(x);
            }
        }
        location = "resources/MM/" + year + "/MRegularSeasonCompactResults.csv";
        File file2 = new File(location);
        Scanner fileReader2 = new Scanner(file2);
        while(fileReader2.hasNextLine()) {
            String [] temp = fileReader2.nextLine().split(",");
            if (!temp[0].equalsIgnoreCase(year)) continue;

            if (temp.length>1) {
                Team winner = teamFinderID(temp[2]);
                Team loser = teamFinderID(temp[4]);

                assert winner != null;
                double oldWinnerMu = winner.mu;
                assert loser != null;
                double oldLoserMu = loser.mu;
                double oldWinnerSigma = winner.sigma;
                double oldLoserSigma = loser.sigma;

                double c = Math.sqrt(2*Math.pow(beta, 2) + Math.pow(oldWinnerSigma, 2) + Math.pow(oldLoserSigma, 2));
                double t = (oldWinnerMu-oldLoserMu)/c;

                winner.setMu(oldWinnerMu + ((Math.pow(oldWinnerSigma, 2))/c) * v(t));
                loser.setMu(oldLoserMu - ((Math.pow(oldLoserSigma, 2))/c) * v(t));
                winner.setSigma(Math.sqrt((Math.pow(oldWinnerSigma, 2)) * (1 - ((Math.pow(oldWinnerSigma, 2)) / Math.pow(c, 2)) * w(t))));
                loser.setSigma(Math.sqrt((Math.pow(oldLoserSigma, 2)) * (1 - ((Math.pow(oldLoserSigma, 2)) / Math.pow(c, 2)) * w(t))));
            }
        }
    }

    private static void gamePicker(String team1Name, String team2Name) {
        if ((teamFinder(team1Name).mu/(teamFinder(team1Name).mu + teamFinder(team2Name).mu) * 100) >=50.0 ) {
            System.out.println(team1Name + " has a " + (teamFinder(team1Name).mu/(teamFinder(team1Name).mu + teamFinder(team2Name).mu) * 100) + " percent chance of winning");
        }
        else if ((teamFinder(team2Name).mu/(teamFinder(team1Name).mu + teamFinder(team2Name).mu) * 100) >=50.0 ) {
            System.out.println(team2Name + " has a " + (teamFinder(team2Name).mu/(teamFinder(team1Name).mu + teamFinder(team2Name).mu) * 100) + " percent chance of winning");
        }
        else {
            System.out.println("The game will likely be a tie");
        }
    }

    //uses mu
    private static Team teamFinder(String teamName) {
        for (Team t:teams) {
            if (t.name.equalsIgnoreCase(teamName)) {
                return t;
            }
        }
        return null;
    }

    private static Team teamFinderID(String id) {
        for (Team t:teams) {
            if (t.id.equalsIgnoreCase(id)) {
                return t;
            }
        }
        return null;
    }

    //uses trueMu
    private static void TgamePicker(String team1Name, String team2Name) {
        if ((teamFinder(team1Name).trueMu/(teamFinder(team1Name).trueMu + teamFinder(team2Name).trueMu) * 100) >=50.0 ) {
            System.out.println(team1Name + " has a " + (teamFinder(team1Name).trueMu/(teamFinder(team1Name).trueMu + teamFinder(team2Name).trueMu) * 100) + " percent chance of winning");
        }
        else if ((teamFinder(team2Name).trueMu/(teamFinder(team1Name).trueMu + teamFinder(team2Name).trueMu) * 100) >=50.0 ) {
            System.out.println(team2Name + " has a " + (teamFinder(team2Name).trueMu/(teamFinder(team1Name).trueMu + teamFinder(team2Name).trueMu) * 100) + " percent chance of winning");
        }
        else {
            System.out.println("The game will likely be a tie");
        }
    }

    //general normal distribution
    public static double gnd(double x, double mu, double sigma) {
        return ((1.0/sigma) * (snd((x-mu)/sigma)));
    }
    //standard normal distribution
    private static double snd(double x) {
        return ((1.0/(Math.sqrt(2.0 * Math.PI))) * (Math.pow(Math.E, -0.5 * Math.pow(x, 2.0))));
    }

    //Mean Additive Truncated Gaussian Function: “v” (non-draw)
    private static double v(double t) {
        return (snd(t))/ Gaussian.cdf(t);
    }

    //Variance Multiplicative Function: “w” (non-drawn)
    private static double w(double t) {
        return v(t) * (v(t) + t);
    }
}
