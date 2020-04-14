package pl.bajerska.befindyourmeal.edamam;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.bajerska.befindyourmeal.model.EdamamOutput;
import pl.bajerska.befindyourmeal.recipe.RecipeCriteria;

import java.util.stream.Collectors;

@Service
public class ApiServiceImpl implements ApiService {


    @Override
    public String test() {
        RestTemplate rest = new RestTemplate();

        ResponseEntity<String> exchange = rest.exchange(
                "https://api.edamam.com/search?app_id=2f71b0fa&app_key=c0ed4fc9f52cd3c0d3d1f74b8f6e220d&q=haddock",
                HttpMethod.GET,
                HttpEntity.EMPTY,
                String.class);
        return exchange.getBody();
    }

    @Override
    public EdamamOutput findRecipe(RecipeCriteria recipeCriteria) {

        RestTemplate rest = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity httpEntity = new HttpEntity(httpHeaders);
        String criteria = recipeCriteria.getIngredients().stream().collect(Collectors.joining(","));

        ResponseEntity<EdamamOutput> exchange = rest.exchange(
                "https://api.edamam.com/search?app_id=2f71b0fa&app_key=c0ed4fc9f52cd3c0d3d1f74b8f6e220d&q="
                        + criteria,
                HttpMethod.GET,
                httpEntity,
                EdamamOutput.class);

        return exchange.getBody();
    }

}
