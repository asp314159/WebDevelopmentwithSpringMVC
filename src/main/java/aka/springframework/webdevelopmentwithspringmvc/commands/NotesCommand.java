package aka.springframework.webdevelopmentwithspringmvc.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by AP
 */
@Getter
@Setter
@NoArgsConstructor
public class NotesCommand {

    private Long id;
    private String notes;
}
