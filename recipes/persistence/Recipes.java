package recipes.persistence;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="RECIPES")
public class Recipes {
    @Id
    @JsonIgnore
    @Min(value = 1)
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @NotEmpty
    @NotBlank
    @Column(name = "name")
    String name;

    @NotEmpty
    @NotBlank
    @Column(name = "category")
    String category;

    @NotEmpty
    @NotBlank
    @Column(name = "description")
    String description;

    @NotEmpty
    @ElementCollection
    @Size(min = 1)
    @CollectionTable(name = "INGREDIENTS", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "ingredients")
    List<String> ingredients;

    @NotEmpty
    @ElementCollection
    @Size(min = 1)
    @CollectionTable(name = "DIRECTIONS", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "directions")
    List<String> directions;

    @Column(name = "date")
    LocalDateTime date;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "owner")
    Owner owner;
}