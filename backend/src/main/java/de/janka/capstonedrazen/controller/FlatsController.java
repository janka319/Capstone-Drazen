package de.janka.capstonedrazen.controller;

import de.janka.capstonedrazen.api.Flat;
import de.janka.capstonedrazen.model.FlatEntity;
import de.janka.capstonedrazen.model.UserEntity;
import de.janka.capstonedrazen.service.FlatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/flats")
public class FlatsController {

    private FlatService flatService;

    @Autowired
    public FlatsController(FlatService flatService) {
        this.flatService = flatService;
    }


    @GetMapping("/search")
    public ResponseEntity<List<Flat>> findAllFlats() {
        List<Flat> flats = flatService.findAll().stream().map(this::map).toList();
        return ok(flats);
    }

    @PostMapping("/publish")
    public ResponseEntity<Flat> create(@AuthenticationPrincipal UserEntity authUser, @RequestBody Flat flat){

        FlatEntity createdFlatEntity = flatService.create(flat);
        Flat createdFlat = map(createdFlatEntity);

        return ok(createdFlat);
    }




    private Flat map(FlatEntity flatEntity){
        return Flat.builder()
                .id(flatEntity.getId())
                .rent(flatEntity.getRent())
                .size(flatEntity.getSize())
                .image(flatEntity.getImage())
                .email(flatEntity.getEmail())
                .address(flatEntity.getAddress())
                .build();
    }

}
