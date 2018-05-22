package com.webshop.model.entity;

import com.webshop.model.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Category extends LanguageAwareEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @NotNull
    private String name;
    //@Pattern(regexp="[a-zA-Z0-9]*")
    @Size(min = 2, max = 20)
    @NotNull
    private String slug;
    @Lob
    private String description;

    private Status status;

    public static final Category categoryRoot = Category.builder()
            .id(0L)
            .name("root")
            .description("root")
            .slug("")
            .build();
}
