package ru.vlpetko.dockdb.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.vlpetko.dockdb.controller.dto.DockUserDto;
import ru.vlpetko.dockdb.mapper.DockUserMapper;
import ru.vlpetko.dockdb.service.UserService;

@RestController
@RequestMapping({"/api"})
@RequiredArgsConstructor
public class DockController {

    private final UserService userService;

    @GetMapping("/data")
    public void sendData(@RequestParam String data) {
        userService.getDataFromContoller(data);
    }

    @GetMapping("/getuser")
    public DockUserDto getDockUser(@RequestParam Long id){
        return DockUserMapper.INSTANCE.mapToDockUserDto(userService.getDockUser(id));
    }
    @PostMapping("/usersave")
    public DockUserDto saveDockUser(@RequestBody DockUserDto dockUserDto){
        return DockUserMapper.INSTANCE.mapToDockUserDto(userService.setDockUser
                (DockUserMapper.INSTANCE.mapToDockUser(dockUserDto)));
    }
}
