package pl.bajerska.befindyourmeal.recipe;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.bajerska.befindyourmeal.edamam.ApiService;
import pl.bajerska.befindyourmeal.model.EdamamOutput;
import pl.bajerska.befindyourmeal.model.Recipe;

import java.util.List;


@Controller
public class RecipeController {

    private ApiService apiService;
    private RecipeService recipeService;

    public RecipeController(ApiService apiService, RecipeService recipeService ) {
        this.apiService = apiService;
        this.recipeService = recipeService;
    }

    @RequestMapping(value = {"describerecipe"}, method = RequestMethod.GET)
    public String getRecipe(Model model) {
        RecipeCriteria recipeCriteria = new RecipeCriteria();
        model.addAttribute("recipeCriteria", recipeCriteria);
        return "describerecipe";
    }

    @PostMapping(value = {"/findrecipe"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public String findRecipe(RecipeCriteria recipeCriteria, Model model) {

        recipeCriteria.getIngredients().removeIf(String::isEmpty);

        List<Recipe> found = recipeService.find(recipeCriteria);
        if (found.size() == 0) {
            EdamamOutput output = apiService.findRecipe(recipeCriteria);
            output.getHits().stream().forEach(h -> {
                recipeService.save(h.getRecipe(),output.getQ());
                found.add(h.getRecipe());
            });
        }

        model.addAttribute("recipeList", found);
        // model.addAttribute("edamamOutput", output);

        return "recipe";
    }

}
