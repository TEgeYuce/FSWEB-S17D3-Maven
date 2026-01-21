package com.workintech.zoo.controller;
import java.util.*;

import com.workintech.zoo.entity.Kangaroo;
import com.workintech.zoo.exceptions.ZooException;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import jakarta.annotation.PostConstruct;


@RestController
@RequestMapping("/kangaroos")
public class KangarooController {

    private Map<Integer, Kangaroo> kangaroos;

    @PostConstruct
    public void init() {
        kangaroos = new HashMap<>();
        kangaroos.put(1, Kangaroo.builder()
                .id(1).name("Kangaroo1").height(1.4).weight(45.0).gender("Male").isAggressive(true).build());
    }

    @GetMapping
    public List<Kangaroo> getAll() {
        return new ArrayList<>(kangaroos.values());
    }

    @GetMapping("/{id}")
    public Kangaroo getById(@PathVariable("id") Integer id) {
        Kangaroo k = kangaroos.get(id);

        if (k == null) {
            throw new ZooException(id + " -> This kangaroo isn't found ", HttpStatus.NOT_FOUND);
        }

        return k;
    }

    @PostMapping
    public ResponseEntity<Kangaroo> create(@RequestBody Kangaroo request) {

        if (request.getId() == null) request.setId(kangaroos.size() + 1);
        if (kangaroos.containsKey(request.getId())) {
            throw new ZooException(request.getId() + " -> This kangaroo is already exists ", HttpStatus.CONFLICT);
        }
        kangaroos.put(request.getId(), request);

        return ResponseEntity.status(HttpStatus.CREATED).body(request);
    }

    @PutMapping("/{id}")
    public Kangaroo update(@PathVariable("id") Integer id, @RequestBody Kangaroo incoming) {
        Kangaroo existing = kangaroos.get(id);

        if (existing == null) {
            throw new ZooException(id + " -> This kangaroo isn't found ", HttpStatus.NOT_FOUND);
        }
        incoming.setId(id);
        kangaroos.put(id, incoming);

        return incoming;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        Kangaroo removed = kangaroos.remove(id);

        if (removed == null) {
            throw new ZooException(id + " -> This kangaroo isn't found ", HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok().build();
    }


}

