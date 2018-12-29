package com.company;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.lang.Math;

public class Main {

    private static ArrayList<Team> teams = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        teamBuilderStandard("resources/NFL1819.csv");
        String t1 = "Saints";
        String t2 = "Cardinals";
        gamePicker(t1, t2);
        TgamePicker(t1, t2);
    }

    private static void teamBuilderStandard(String filePath) throws IOException {
        double beta = 10.1;
        //play around with beta value
        File file = new File(filePath);
        Scanner fileReader = new Scanner(file);
        while(fileReader.hasNextLine()) {
            String [] temp = fileReader.nextLine().split(",");
            if (temp[0].equalsIgnoreCase("Team1") || temp[0].equalsIgnoreCase("") || temp[0].contains("week") || temp[0].contains("//")) continue;

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


            if (temp.length>1) {
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
            if (t.name.equals(teamName)) {
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
