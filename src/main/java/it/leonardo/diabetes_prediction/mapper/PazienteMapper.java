package it.leonardo.diabetes_prediction.mapper;

import it.leonardo.diabetes_prediction.db.entity.PazienteEntity;
import it.leonardo.diabetes_prediction.dto.PazienteDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PazienteMapper extends EntityMapper<PazienteDTO, PazienteEntity> { }
