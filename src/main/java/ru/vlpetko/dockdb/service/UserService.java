package ru.vlpetko.dockdb.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.vlpetko.dockdb.model.DockUser;
import ru.vlpetko.dockdb.repository.DockRepository;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final RestTemplate restTemplate;

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

    public String getRowValue(){
        String resultsJson = "";

        String shedulerUrl
                = "http://localhost:9091/api/getrowcount";

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> responseEntity =
                restTemplate.getForEntity(shedulerUrl, String.class);

        log.info("server status: " + responseEntity.getStatusCode());
        if (responseEntity.getStatusCode() == HttpStatus.valueOf(200)){
            resultsJson = Objects.requireNonNull(responseEntity.getBody());
            log.info("server ansver: " + resultsJson);
        }
        return resultsJson;
    }
}
