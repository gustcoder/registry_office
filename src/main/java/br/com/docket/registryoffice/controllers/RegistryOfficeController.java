package br.com.docket.registryoffice.controllers;

import br.com.docket.registryoffice.models.RegistryOffice;
import br.com.docket.registryoffice.repository.RegistryOfficeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@RestController
@RequestMapping(path = "registry-office")
public class RegistryOfficeController {

    @Autowired
    private RegistryOfficeRepository registryOfficeRepository;

    @PostMapping(path = "store")
    public String store() {
        return "Cartório cadastrado com sucesso!";
    }

    @GetMapping(path = "index")
    public List<RegistryOffice> index() {
        return registryOfficeRepository.findAll();
    }

    @GetMapping(path = "show/{registryOfficeId}")
    public String show(@PathVariable Integer registryOfficeId) {
        return "Listando cartório " + registryOfficeId;
    }

    @PutMapping(path = "update/{registryOfficeId}")
    public String update(@PathVariable Integer registryOfficeId) {
        return "Editando cartório " + registryOfficeId;
    }

    @DeleteMapping(path = "delete/{registryOfficeId}")
    public String delete(@PathVariable Integer registryOfficeId) {
        return "Deletando cartório " + registryOfficeId;
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
}
