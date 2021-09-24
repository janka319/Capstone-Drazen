package de.janka.capstonedrazen.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class Flat {

    private String id;
    private String image;
    private String size;
    private String rent;
    private String email;

}
