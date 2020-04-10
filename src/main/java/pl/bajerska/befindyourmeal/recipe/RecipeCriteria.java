package pl.bajerska.befindyourmeal.recipe;

import javax.persistence.*;
import java.util.ArrayList;


public class RecipeCriteria {

    private ArrayList<String> ingredients;

    @Enumerated(EnumType.STRING)
    private ArrayList<DietLabel> diet;

    @Enumerated(EnumType.STRING)
    private ArrayList<HealthLabel> health;

    private ArrayList<String> excluded;

    public RecipeCriteria() {
        this.ingredients = new ArrayList<String>();
        this.diet = new ArrayList<DietLabel>();
        this.health = new ArrayList<HealthLabel>();
        this.excluded = new ArrayList<String>();
    }

    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    public void addIngredient(String ingredient) {
        this.ingredients.add(ingredient);
    }

    public ArrayList<DietLabel> getDiet() {
        return diet;
    }

    public void addDietLabels(DietLabel diet) {
        this.diet.add(diet);
    }

    public ArrayList<HealthLabel> getHealth() {
        return health;
    }

    public void addHealthLabels(HealthLabel health) {
        this.health.add(health);
    }

    public ArrayList<String> getExcluded() {
        return excluded;
    }

    public void addExcluded(String excluded) {
        this.excluded.add(excluded);
    }


}

