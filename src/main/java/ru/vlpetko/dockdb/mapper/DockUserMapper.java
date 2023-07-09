package ru.vlpetko.dockdb.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.vlpetko.dockdb.controller.dto.DockUserDto;
import ru.vlpetko.dockdb.model.DockUser;

@Mapper
public interface DockUserMapper {

    DockUserMapper INSTANCE = Mappers.getMapper(DockUserMapper.class);

    DockUserDto mapToDockUserDto(DockUser dockUser);

    DockUser mapToDockUser(DockUserDto dockUserDto);
}
