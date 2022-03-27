package br.com.docket.registryoffice.controllers;

import br.com.docket.registryoffice.exceptions.ApiCustomException;
import br.com.docket.registryoffice.models.RegistryOffice;
import br.com.docket.registryoffice.repository.RegistryOfficeRepository;
import br.com.docket.registryoffice.services.RegistryOfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

//@RestController
@Controller
@RequestMapping(path = "registry-office")
public class RegistryOfficeController {

    @Autowired
    private RegistryOfficeRepository registryOfficeRepository;

    @GetMapping(path = "index")
    public ResponseEntity<Object> index() {

        List<RegistryOffice> allRegistryOffices = registryOfficeRepository.findAll();

        if (allRegistryOffices.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .body("Cartório não encontrado");
        }

        return ResponseEntity.status(HttpStatus.OK).body(allRegistryOffices);
    }

    @GetMapping(path = "show/{registryOfficeId}")
    public ResponseEntity<Object> show(@PathVariable Long registryOfficeId) {

        Optional<RegistryOffice> registryOffice = registryOfficeRepository.findById(registryOfficeId);

        if (!registryOffice.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .body("Cartório não encontrado");
        }

        return ResponseEntity.status(HttpStatus.OK).body(registryOffice);
    }

    @PostMapping(path = "store", consumes = {"application/json", "application/x-www-form-urlencoded"})
    @ExceptionHandler(ApiCustomException.class)
    @ResponseBody
    public ResponseEntity<Object> store(@RequestBody RegistryOffice registryOffice) {

        RegistryOfficeService registryOfficeService = new RegistryOfficeService(registryOfficeRepository);

        String validationMessage = registryOfficeService.validateRegistryOffice(registryOffice);
        if (!validationMessage.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_ACCEPTABLE)
                    .body(validationMessage);
        }

        String validationNameExists = registryOfficeService.checkIfRegistryOfficeNameAlreadyExists(registryOffice);
        if (!validationNameExists.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_ACCEPTABLE)
                    .body(validationNameExists);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(registryOfficeService.save(registryOffice));
    }

    @PutMapping(path = "update/{registryOfficeId}")
    public ResponseEntity<Object> update(
            @PathVariable Long registryOfficeId,
            @RequestBody RegistryOffice registryOffice
    ) {

        RegistryOfficeService registryOfficeService = new RegistryOfficeService(registryOfficeRepository);

        String validationMessage = registryOfficeService.validateRegistryOffice(registryOffice);
        if (!validationMessage.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_ACCEPTABLE)
                    .body(validationMessage);
        }

        String validationExistsMessage = registryOfficeService.checkIfRegistryOfficeExistsById(registryOfficeId);
        if (!validationExistsMessage.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(validationExistsMessage);
        }

        Optional<RegistryOffice> updateRegistryOffice = registryOfficeRepository.findById(registryOfficeId);

        if (!updateRegistryOffice.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Cartório ID não encontrado");
        }

        RegistryOffice registryOfficeData = updateRegistryOffice.get();

        registryOfficeData.setName(registryOffice.getName());
        registryOfficeData.setAddress(registryOffice.getAddress());


        return ResponseEntity.status(HttpStatus.OK).body(registryOfficeService.save(registryOfficeData));
    }

    @DeleteMapping(path = "delete/{registryOfficeId}")
    public ResponseEntity<Object> delete(@PathVariable Long registryOfficeId) {
        RegistryOfficeService registryOfficeService = new RegistryOfficeService(registryOfficeRepository);

        String validationExistsMessage = registryOfficeService.checkIfRegistryOfficeExistsById(registryOfficeId);
        if (!validationExistsMessage.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(validationExistsMessage);
        }

        registryOfficeRepository.deleteById(registryOfficeId);
        return ResponseEntity.status(HttpStatus.OK).body("Cartório " + registryOfficeId + " deletado com sucesso!");
    }

    @GetMapping(path = "certificates-raw")
    public ResponseEntity<String> getCertificatesRaw()
    {
        RestTemplate restTemplate = new RestTemplate();
        String certificateURL = "https://docketdesafiobackend.herokuapp.com/api/v1/certidoes";

        return restTemplate.getForEntity(certificateURL, String.class);
    }

    @GetMapping(path = "certificates")
    public List<Object> getCertificates()
    {
        RestTemplate restTemplate = new RestTemplate();
        String certificateURL = "https://docketdesafiobackend.herokuapp.com/api/v1/certidoes";

        String response = restTemplate.getForEntity(certificateURL, String.class).getBody();

        JsonParser jsonParser = JsonParserFactory.getJsonParser();

        return jsonParser.parseList(response);
    }

    @GetMapping(path = "about")
    public String about()
    {
        return "registry-office/about";
    }
}
