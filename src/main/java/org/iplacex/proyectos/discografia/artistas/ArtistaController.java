package org.iplacex.proyectos.discografia.artistas;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ArtistaController {

    @Autowired
    private IArtistaRepository artistaRepo;

    @PostMapping(
        value = "/artista",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Artista> HandleInsertArtistaRequest(@RequestBody Artista artista){
        Artista temp = artistaRepo.insert(artista);
        return ResponseEntity.status(HttpStatus.CREATED).body(temp);
    }

    @GetMapping(
        value = "/artista/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Artista> HandleGetArtistaRequest(@PathVariable("id") String id){
        Optional<Artista> temp = artistaRepo.findById(id);

        if(!temp.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(temp.get());
    }

    @GetMapping(
        value = "/artistas",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<Artista>> HandleGetAristasRequest(){
        List<Artista> artistas = artistaRepo.findAll();
        return ResponseEntity.ok(artistas);
    }

    @PutMapping(
        value = "/artista/{id}",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Artista> HandleUpdateArtistaRequest(
        @PathVariable("id") String id,
        @RequestBody Artista artista
    ){
        if(!artistaRepo.existsById(id)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        artista._id = id;
        Artista temp = artistaRepo.save(artista);

        return ResponseEntity.ok(temp);
    }

    @DeleteMapping(value = "/artista/{id}")
    public ResponseEntity<Artista> HandleDeleteArtistaRequest(@PathVariable("id") String id){
        if(!artistaRepo.existsById(id)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Artista temp = artistaRepo.findById(id).get();
        artistaRepo.deleteById(id);

        return ResponseEntity.ok(temp);
    }
}

