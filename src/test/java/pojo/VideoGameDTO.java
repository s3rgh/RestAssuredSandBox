package pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.Accessors;

@Data
@JsonIgnoreProperties
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class VideoGameDTO {
    private int reviewScore;
    private String releaseDate;
    private String name;
    private String rating;
    private int id;
    private String category;

    public static VideoGameDTO videoGameDTOBuilder() {
        return VideoGameDTO.builder().build();
    }
}
