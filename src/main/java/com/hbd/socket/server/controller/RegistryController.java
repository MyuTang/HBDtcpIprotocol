package com.hbd.socket.server.controller;

import com.hbd.socket.server.netty.IndexRegistry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/registry")
public class RegistryController {

    public RegistryController(IndexRegistry indexRegistry) {
        this.indexRegistry = indexRegistry;
    }

    private final IndexRegistry indexRegistry;

    @GetMapping
    Set<String> registeredIndex() {
        return indexRegistry.registeredIndexs();
    }

}
