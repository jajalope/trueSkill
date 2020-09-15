package com.company;

public class Team {
    double mu;
    double sigma;
    double trueMu = mu-(sigma*3);
    String name;
    String id;

    Team (String teamName, String tID) {
        id = tID;
        name = teamName;
        mu = 25.0;
        sigma = 25.0/3.0;
    }

    Team (String teamName){
        name = teamName;
        mu = 25.0;
        sigma = 25.0/3.0;
    }

    void setMu(double muVal) {
        this.mu = muVal;
        this.trueMu = mu-(sigma*3.0);
    }

    public double getMu() {
        return mu;
    }

    void setSigma(double sigma) {
        this.sigma = sigma;
        this.trueMu = mu-(sigma*3.0);
    }

    public double getSigma() {
        return sigma;
    }
}
