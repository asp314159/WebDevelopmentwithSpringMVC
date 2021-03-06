package aka.springframework.webdevelopmentwithspringmvc.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by AP
 */
@Data
@Entity
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private int prepTime;
    private int cookTime;
    private int servings;
    private String source;
    private String url;

    @Lob
    private String directions;

    @Lob //will create blob field in the DB
    private Byte[] image;

    @Enumerated (value = EnumType.STRING)
    private Difficulty difficulty;

    @OneToOne (cascade = CascadeType.ALL) //Bidirectional relationship
    private Notes notes;

    @OneToMany (fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "recipe")
    private Set<Ingredient> ingredients = new HashSet<>();

    @ManyToMany
    @JoinTable (name = "recipe_category",
        joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories = new HashSet<>();


    public void setNotes(Notes notes) {
        this.notes = notes;
        notes.setRecipe(this);
    }

    public Recipe addIngredient(Ingredient ingredient){
        ingredient.setRecipe(this);
        this.ingredients.add(ingredient);
        return this;
    }
}
