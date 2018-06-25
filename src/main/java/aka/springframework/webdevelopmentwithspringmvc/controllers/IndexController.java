package aka.springframework.webdevelopmentwithspringmvc.controllers;

import aka.springframework.webdevelopmentwithspringmvc.bootstrap.InsertingRecipes;
import aka.springframework.webdevelopmentwithspringmvc.domain.Category;
import aka.springframework.webdevelopmentwithspringmvc.domain.UnitOfMeasure;
import aka.springframework.webdevelopmentwithspringmvc.repositories.CategoryRepository;
import aka.springframework.webdevelopmentwithspringmvc.repositories.RecipeRepository;
import aka.springframework.webdevelopmentwithspringmvc.repositories.UnitOfMeasureRepository;
import aka.springframework.webdevelopmentwithspringmvc.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by AP
 */
@Slf4j
@Controller
public class IndexController {

    /*@Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UnitOfMeasureRepository unitOfMeasureRepository;

    @Autowired
    private RecipeRepository recipeRepository;*/

    @Autowired
    private RecipeService recipeService;

    @RequestMapping({"", "/", "/index"})
    public String getIndexPage(Model model) {

        /*Optional<Category> categoryOptional = categoryRepository.findByDescription("Mexican");
        Optional<UnitOfMeasure> unitOfMeasureOptional = unitOfMeasureRepository.findByDescription("Teaspoo");

        System.out.println("CAT ID is: " + categoryOptional.get().getId());
        System.out.println("UOM ID is: " + unitOfMeasureOptional.get().getId());
        System.out.println(categoryOptional.get().toString());
        Set<Category> categorySet = InsertingRecipes.collect(categoryOptional, Collectors.toSet());
        categorySet.stream().forEach(x -> System.out.println(x.getId() +" "+ x.getDescription()));

        UnitOfMeasure uom = unitOfMeasureOptional.orElseGet(() -> {throw new RuntimeException("Expected UOM Not Found");});
        System.out.println(uom.getId() + " " + uom.getDescription());*/

        log.debug("I'm in index controller class");
        model.addAttribute("recipes", recipeService.getRecipes());

        return "index";
    }

}
