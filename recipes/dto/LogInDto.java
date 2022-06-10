package recipes.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LogInDto {

    @Pattern(regexp = ".+@.+\\..+")
    String email;

    @Size(min = 8)
    @NotBlank
    String password;
}
