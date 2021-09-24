package de.janka.capstonedrazen.controller;


import de.janka.capstonedrazen.api.Flat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/flats")
public class FlatsController {

    @GetMapping
    public List<Flat> findAllFlats() {
        return List.of(
                new Flat("1","http://source.unsplash.com/random/400x400?room","56","1200","example@google.com"),
                new Flat("2","http://source.unsplash.com/random/400x400?room", "78", "1450","example@google.com"),
                new Flat("3","http://source.unsplash.com/random/400x400?room","30","600","example@google.com")
        );
    }

}
