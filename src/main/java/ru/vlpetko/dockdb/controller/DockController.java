package ru.vlpetko.dockdb.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.vlpetko.dockdb.config.props.RabbitMQProperties;
import ru.vlpetko.dockdb.controller.dto.DockUserDto;
import ru.vlpetko.dockdb.mapper.DockUserMapper;
import ru.vlpetko.dockdb.service.RabbitMQProducerService;
import ru.vlpetko.dockdb.service.UserService;

import static ru.vlpetko.dockdb.config.RabbitConfig.QUEUE_NAME;

@RestController
@RequestMapping({"/api"})
@RequiredArgsConstructor
public class DockController {

    private final UserService userService;

    private final RabbitMQProducerService rabbitMQProducerService;

    private final RabbitMQProperties rabbitMQProperties;


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
    @GetMapping("/getrowcount")
    public String getRowCount(){
        return userService.getRowValue();
    }
    @GetMapping("/send")
    public void sendMessageToRabbit(@RequestParam String message) {
        rabbitMQProducerService.sendMessage(message);
    }
}
