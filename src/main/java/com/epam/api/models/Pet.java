package com.epam.api.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Pet {
    private long id;
    private Category category;
    private String name;
    private String[] photoUrls;
    private Tag[] tags;
    private String status;
}
