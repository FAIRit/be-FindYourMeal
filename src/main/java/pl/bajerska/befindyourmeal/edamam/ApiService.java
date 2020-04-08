package pl.bajerska.befindyourmeal.edamam;

import pl.bajerska.befindyourmeal.recipe.RecipeCriteria;

public interface ApiService {

    String test();

    String findRecipe(RecipeCriteria recipeCriteria);

}
