package com.joaopedro.librarymanager.controller;

import com.joaopedro.librarymanager.dto.EditoraDTO;
import com.joaopedro.librarymanager.service.IEditoraService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/editoras")
public class EditoraController {

    private final IEditoraService editoraService;

    @Autowired
    public EditoraController(IEditoraService editoraService) {
        this.editoraService = editoraService;
    }

    @GetMapping
    @ApiOperation(value = "Retorna uma lista paginada de editoras")
    public ResponseEntity<Page<EditoraDTO>> findAll(@RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return new ResponseEntity<>(editoraService.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping("/all")
    @ApiOperation(value = "Retorna uma lista de editoras")
    public ResponseEntity<List<EditoraDTO>> findAll() {
        return new ResponseEntity<>(editoraService.findAll(), HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Cria uma editora")
    public EditoraDTO create(@RequestBody @Valid EditoraDTO editoraDTO){
        return editoraService.create(editoraDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Deleta uma editora")
    public void deleteById(@PathVariable Long id) {
        editoraService.deleteById(id);
    }

    @PutMapping
    @ApiOperation(value = "Altera uma editora")
    public EditoraDTO update(@RequestBody @Valid EditoraDTO editoraDTO) {
        return editoraService.update(editoraDTO);
    }
}
