package org.social_media.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {
    private long id;
    private String caption;
    private String image;
    private String video;
    private UserDTO user;
    private List<Long> likes;
}
