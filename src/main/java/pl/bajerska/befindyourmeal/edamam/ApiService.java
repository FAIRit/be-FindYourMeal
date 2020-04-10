package pl.bajerska.befindyourmeal.edamam;

import pl.bajerska.befindyourmeal.model.EdamamOutput;
import pl.bajerska.befindyourmeal.model.Recipe;
import pl.bajerska.befindyourmeal.recipe.RecipeCriteria;

public interface ApiService {

    String test();

    EdamamOutput findRecipe(RecipeCriteria recipeCriteria);

}
