package com.hitss.academica.services.impl;

import com.hitss.academica.dto.auth.RegisterRequestDTO;
import com.hitss.academica.dto.usuario.UsuarioResponseDTO;
import com.hitss.academica.dto.usuario.UsuarioUpdateRequestDTO;
import com.hitss.academica.entities.Rol;
import com.hitss.academica.entities.Usuario;
import com.hitss.academica.mappers.UsuarioMapper;
import com.hitss.academica.repositories.RolRepository;
import com.hitss.academica.repositories.UsuarioRepository;
import com.hitss.academica.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private UsuarioMapper usuarioMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UsuarioResponseDTO register(RegisterRequestDTO registerRequestDTO) {
        if (usuarioRepository.existsByEmail(registerRequestDTO.getEmail())) {
            throw new IllegalArgumentException("El correo electrónico ya está en uso.");
        }

        Usuario nuevoUsuario = usuarioMapper.registerRequestDtoToUsuario(registerRequestDTO);
        nuevoUsuario.setPassword(passwordEncoder.encode(registerRequestDTO.getPassword()));

        Rol rolAsignado = rolRepository.findById(registerRequestDTO.getRolId())
                .orElseThrow(() -> new RuntimeException("Error: Rol no encontrado con ID: " + registerRequestDTO.getRolId()));

        nuevoUsuario.setRol(rolAsignado);

        Usuario usuarioGuardado = usuarioRepository.save(nuevoUsuario);
        return usuarioMapper.usuarioToUsuarioResponseDto(usuarioGuardado);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioResponseDTO> findAll() {
        return usuarioMapper.usuariosToUsuarioResponseDtos(usuarioRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioResponseDTO findById(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
        return usuarioMapper.usuarioToUsuarioResponseDto(usuario);
    }

    @Override
    @Transactional
    public UsuarioResponseDTO update(Long id, UsuarioUpdateRequestDTO updateRequestDTO) {
        Usuario usuarioAActualizar = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));

        if (updateRequestDTO.getNombre() != null) {
            usuarioAActualizar.setNombre(updateRequestDTO.getNombre());
        }
        if (updateRequestDTO.getApellidoPaterno() != null) {
            usuarioAActualizar.setApellidoPaterno(updateRequestDTO.getApellidoPaterno());
        }
        if (updateRequestDTO.getApellidoMaterno() != null) {
            usuarioAActualizar.setApellidoMaterno(updateRequestDTO.getApellidoMaterno());
        }
        if (updateRequestDTO.getEmail() != null && !updateRequestDTO.getEmail().equals(usuarioAActualizar.getEmail())) {
            if (usuarioRepository.existsByEmail(updateRequestDTO.getEmail())) {
                throw new IllegalArgumentException("El nuevo correo electrónico ya está en uso.");
            }
            usuarioAActualizar.setEmail(updateRequestDTO.getEmail());
        }
        if (updateRequestDTO.getPassword() != null) {
            usuarioAActualizar.setPassword(passwordEncoder.encode(updateRequestDTO.getPassword()));
        }
        if(updateRequestDTO.getRolId() != null) {
            Rol nuevoRol = rolRepository.findById(updateRequestDTO.getRolId())
                    .orElseThrow(() -> new RuntimeException("Rol no encontrado con ID: " + updateRequestDTO.getRolId()));
            usuarioAActualizar.setRol(nuevoRol);
        }

        Usuario usuarioActualizado = usuarioRepository.save(usuarioAActualizar);
        return usuarioMapper.usuarioToUsuarioResponseDto(usuarioActualizado);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado con ID: " + id);
        }
        usuarioRepository.deleteById(id);
    }
}