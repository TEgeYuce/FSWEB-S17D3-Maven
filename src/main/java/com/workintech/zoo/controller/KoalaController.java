package com.workintech.zoo.controller;
import java.util.*;

import com.workintech.zoo.entity.Koala;
import com.workintech.zoo.exceptions.ZooException;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import jakarta.annotation.PostConstruct;


@RestController
@RequestMapping("/koalas")
public class KoalaController {

    private Map<Integer, Koala> koalas;

    @PostConstruct
    public void init() {
        koalas = new HashMap<>();
        koalas.put(1, Koala.builder()
                .id(1).name("Koala1").weight(47.5).sleepHour(10.5).gender("Female").build());
    }

    @GetMapping
    public List<Koala> getAll() {
        return new ArrayList<>(koalas.values());
    }

    @GetMapping("/{id}")
    public Koala getById(@PathVariable("id") Integer id) {
        Koala k = koalas.get(id);

        if (k == null) {
            throw new ZooException(id + " -> This koala isn't found ", HttpStatus.NOT_FOUND);
        }

        return k;
    }

    @PostMapping
    public ResponseEntity<Koala> create(@RequestBody Koala request) {

        if (request.getId() == null) {
            throw new ZooException("Id is necessary/required (Must need id) ", HttpStatus.BAD_REQUEST);
        }
        if (koalas.containsKey(request.getId())) {
            throw new ZooException(request.getId() + " -> This koala is already exists ", HttpStatus.CONFLICT);
        }
        koalas.put(request.getId(), request);

        return ResponseEntity.status(HttpStatus.CREATED).body(request);
    }

    @PutMapping("/{id}")
    public Koala update(@PathVariable("id") Integer id, @RequestBody Koala incoming) {
        Koala existing = koalas.get(id);

        if (existing == null) {
            throw new ZooException(id + " -> This koala isn't found ", HttpStatus.NOT_FOUND);
        }
        incoming.setId(id);
        koalas.put(id, incoming);

        return incoming;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        Koala removed = koalas.remove(id);

        if (removed == null) {
            throw new ZooException(id + " -> This koala isn't found ", HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok().build();
    }


}

