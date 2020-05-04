package pl.bajerska.befindyourmeal.swagger;


import com.google.common.base.Predicate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import pl.bajerska.befindyourmeal.edamam.WebApplicationController;
import pl.bajerska.befindyourmeal.recipe.RecipeController;
import pl.bajerska.befindyourmeal.user.UserController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;


@Configuration
@EnableSwagger2
@ComponentScan(basePackageClasses = {UserController.class, RecipeController.class, WebApplicationController.class})
public class SwaggerConfig {

    @Bean
    public Docket swaggerConfiguration() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths(getSwaggerPaths())
                .apis(RequestHandlerSelectors.basePackage("pl.bajerska"))
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Find Your meal")
                .contact(new Contact("Zofia Bajerska", "will provide soon an url",
                        "zofia.bajerska@gmail.com"))
                .build();


    }

    private Predicate<String> getSwaggerPaths() {
        return or(
                regex("/add.*"),
                regex("/register.*"),
                regex("/user.*"),
                regex("/admin.*"),
                regex("/findform.*"),
                regex("/find.*"),
                regex("/index.*"),
                regex("/update.*"),
                regex("/test.*"),
                regex("/describerecipe.*"));
    }
}
