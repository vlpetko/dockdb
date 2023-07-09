package ru.vlpetko.dockdb.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.vlpetko.dockdb.model.DockUser;
import ru.vlpetko.dockdb.repository.DockRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private  final DockRepository dockRepository;

    public void getDataFromContoller(String data){
        log.info(String.format("data from controller: %s",data));
    }

    public DockUser getDockUser(Long id){
        return dockRepository.getById(id);
    }

    public DockUser setDockUser(DockUser dockUser){
        return dockRepository.save(dockUser);
    }
}
