package ru.vlpetko.dockdb.controller.dto;

import lombok.Data;

@Data
public class DockUserDto {

    private Long id;

    private String firstName;

    private String lastName;

    private int randomNumber;
}
