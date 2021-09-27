package com.joaopedro.librarymanager.service;

import com.joaopedro.librarymanager.dto.UsuarioDTO;
import com.joaopedro.librarymanager.mapper.UsuarioMapper;
import com.joaopedro.librarymanager.model.Usuario;
import com.joaopedro.librarymanager.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    private final UsuarioMapper usuarioMapper = UsuarioMapper.INSTANCE;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<UsuarioDTO> findAll() {
        return usuarioRepository.findAll()
                .stream()
                .map(usuarioMapper::toDTO)
                .collect(Collectors.toList());
    }

    public UsuarioDTO create(UsuarioDTO usuarioDTO) {
        Usuario usuarioToCreate = usuarioMapper.toModel(usuarioDTO);
        Usuario usuarioCreated = usuarioRepository.save(usuarioToCreate);

        return usuarioMapper.toDTO(usuarioCreated);
    }

    public void delete(Long id) {
        usuarioRepository.deleteById(id);
    }

    public UsuarioDTO update(UsuarioDTO usuarioDTO) {
        Usuario usuarioToUpdate = usuarioMapper.toModel(usuarioDTO);
        Usuario usuarioUpdated = usuarioRepository.save(usuarioToUpdate);

        return usuarioMapper.toDTO(usuarioUpdated);
    }
}
