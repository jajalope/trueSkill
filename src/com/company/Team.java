package com.company;

public class Team {
    double mu;
    double sigma;
    private double trueMu = mu-(sigma*3);
    String name;

    Team (String teamName){
        name = teamName;
        mu = 25;
        sigma = 25/3;
    }

    void setMu(double muVal) {
        this.mu = muVal;
        this.trueMu = mu-(sigma*3);
    }

    public double getMu() {
        return mu;
    }

    void setSigma(double sigma) {
        this.sigma = sigma;
        this.trueMu = mu-(sigma*3);
    }

    public double getSigma() {
        return sigma;
    }
}
