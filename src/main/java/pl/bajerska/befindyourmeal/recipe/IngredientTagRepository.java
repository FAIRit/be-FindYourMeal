package pl.bajerska.befindyourmeal.recipe;

import org.springframework.data.repository.CrudRepository;

public interface IngredientTagRepository extends CrudRepository<IngredientTag, Long> {

    IngredientTag findByTag(String tag);
}
