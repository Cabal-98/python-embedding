package it.leonardo.diabetes_prediction.mapper;

import java.util.List;

/**
 * Contract for a generic dto to entity mapper.
 *
 * @param <D> - DTO type parameter.
 * @param <E> - Entity type parameter.
 */

public interface EntityMapper<D, E> {
    D toDto(E entity);
    List<D> toDto(List<E> entityList);

    E toEntity(D dto);
    List<E> toEntity(List<D> dtoList);

}
/*
    Fine di EntityMapper.java
*/
