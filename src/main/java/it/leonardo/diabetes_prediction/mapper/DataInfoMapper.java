package it.leonardo.diabetes_prediction.mapper;

import it.leonardo.diabetes_prediction.db.entity.DataInfoEntity;
import it.leonardo.diabetes_prediction.dto.DataInfoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface DataInfoMapper extends EntityMapper<DataInfoDTO, DataInfoEntity>{



}
