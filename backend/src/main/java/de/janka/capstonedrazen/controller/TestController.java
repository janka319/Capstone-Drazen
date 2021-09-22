package de.janka.capstonedrazen.controller;

import de.janka.capstonedrazen.rest.ImmoScoutAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    private final ImmoScoutAPI immoScoutAPI;

    @Autowired
    public TestController(ImmoScoutAPI immoScoutAPI) {
        this.immoScoutAPI = immoScoutAPI;
    }

    @GetMapping("test")
    public void test(){
        immoScoutAPI.getImmoScout();
    }
}
