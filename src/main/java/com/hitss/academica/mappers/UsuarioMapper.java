package com.hitss.academica.mappers;

import com.hitss.academica.dto.auth.MeResponseDTO;
import com.hitss.academica.dto.auth.RegisterRequestDTO;
import com.hitss.academica.dto.usuario.UsuarioResponseDTO;
import com.hitss.academica.entities.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    Usuario registerRequestDtoToUsuario(RegisterRequestDTO registerRequestDTO);

    @Mapping(source = "rol.rol", target = "rol")
    UsuarioResponseDTO usuarioToUsuarioResponseDto(Usuario usuario);

    List<UsuarioResponseDTO> usuariosToUsuarioResponseDtos(List<Usuario> usuarios);

    @Mapping(source = "rol.rol", target = "rol")
    @Mapping(source = "profesor.id", target = "profesorId")
    @Mapping(source = "estudiante.id", target = "estudianteId")
    MeResponseDTO usuarioToMeResponseDto(Usuario usuario);
}