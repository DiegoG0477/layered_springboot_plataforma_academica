package com.hitss.academica.services.impl;

import com.hitss.academica.dto.rol.RolResponseDTO;
import com.hitss.academica.mappers.RolMapper;
import com.hitss.academica.repositories.RolRepository;
import com.hitss.academica.services.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RolServiceImpl implements RolService {

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private RolMapper rolMapper;

    @Override
    @Transactional(readOnly = true)
    public List<RolResponseDTO> findAll() {
        return rolMapper.rolesToRolResponseDtos(rolRepository.findAll());
    }
}