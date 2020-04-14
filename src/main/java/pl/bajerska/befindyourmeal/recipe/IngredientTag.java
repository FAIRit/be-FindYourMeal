package pl.bajerska.befindyourmeal.recipe;

import pl.bajerska.befindyourmeal.model.Recipe;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "ingredient_tag")
public class IngredientTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tag;

    @ManyToMany
    private List<Recipe> recipes;

    public IngredientTag(String tag) {
        this.tag = tag;
    }
}
