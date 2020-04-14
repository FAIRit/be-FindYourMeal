package pl.bajerska.befindyourmeal.recipe;

import org.springframework.data.repository.CrudRepository;
import pl.bajerska.befindyourmeal.model.Recipe;

import java.util.List;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {

    Recipe findByLabel(String label);

}
