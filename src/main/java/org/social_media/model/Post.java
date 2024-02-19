package org.social_media.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String caption;
    private String image;
    private String video;
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;
    @Column(columnDefinition = "JSON")
    @Convert(converter = NumberListConverter.class)
    private List<Long> likes;
}
