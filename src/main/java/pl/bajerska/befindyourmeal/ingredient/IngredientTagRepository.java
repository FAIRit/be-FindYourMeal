package pl.bajerska.befindyourmeal.ingredient;

import org.springframework.data.repository.CrudRepository;
import pl.bajerska.befindyourmeal.ingredient.IngredientTag;

public interface IngredientTagRepository extends CrudRepository<IngredientTag, Long> {

    IngredientTag findByTag(String tag);
}
