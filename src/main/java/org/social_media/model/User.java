package org.social_media.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(length = 45)
    private String firstName = "";
    @Column(length = 45)
    private String lastName = "";
    @Column(length = 60)
    private String email = "";
    @Column(length = 120)
    private String password = "";
    @Column(columnDefinition = "JSON")
    @Convert(converter = ListConverter.class)
    private List<Long> followers = new ArrayList<>();
    @Column(columnDefinition = "JSON")
    @Convert(converter = ListConverter.class)
    private List<Long> following = new ArrayList<>();
    private String gender="";
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp startDate = new Timestamp(System.currentTimeMillis());
}
