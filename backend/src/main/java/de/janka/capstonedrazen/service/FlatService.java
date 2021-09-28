package de.janka.capstonedrazen.service;

import de.janka.capstonedrazen.api.Flat;
import de.janka.capstonedrazen.model.FlatEntity;
import de.janka.capstonedrazen.repo.FlatRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Getter
@Setter
public class FlatService {

    private FlatRepository flatRepository;

    @Autowired
    public FlatService(FlatRepository flatRepository) {
        this.flatRepository = flatRepository;
    }


    public List<FlatEntity> findAll() {
        return flatRepository.findAll();
    }

    public FlatEntity create(Flat flat) {
        FlatEntity flatEntity = map(flat);
        flatRepository.save(flatEntity);
        return flatEntity;
    }

    private FlatEntity map(Flat flat) {
        FlatEntity flatEntity = new FlatEntity();
        flatEntity.setRent(flat.getRent());
        flatEntity.setSize(flat.getSize());
        flatEntity.setImage(flat.getImage());
        flatEntity.setEmail(flat.getEmail());
        flatEntity.setAddress(flat.getAddress());
        return flatEntity;
    }
}


