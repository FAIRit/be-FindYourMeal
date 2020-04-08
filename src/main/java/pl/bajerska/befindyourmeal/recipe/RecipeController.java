package pl.bajerska.befindyourmeal.recipe;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.bajerska.befindyourmeal.edamam.ApiService;

@Controller
public class RecipeController {

    private ApiService apiService;

    public RecipeController(ApiService apiService) {
        this.apiService = apiService;
    }

    @RequestMapping(value = {"describerecipe"}, method = RequestMethod.GET)
    public String getRecipe(Model model) {
        RecipeCriteria recipeCriteria = new RecipeCriteria();
        recipeCriteria.ingredients.add("");
        model.addAttribute("recipeCriteria", recipeCriteria);
        return "describerecipe";
    }

    @RequestMapping(value = {"/findrecipe"}, method = RequestMethod.POST)
    public String findRecipe(RecipeCriteria recipeCriteria, Model model) {
        model.addAttribute("recipe", apiService.findRecipe(recipeCriteria));
        return "recipe";
    }

}
