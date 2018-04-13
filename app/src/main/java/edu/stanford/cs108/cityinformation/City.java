package edu.stanford.cs108.cityinformation;

/**
 * Created by emohelw on 2/11/2018.
 */

public class City {
    private String name;
    private String continent;
    private int population;

    public City(String name, String continent, int population){
        this.name = name;
        this.continent = continent;
        this.population = population;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }
}
