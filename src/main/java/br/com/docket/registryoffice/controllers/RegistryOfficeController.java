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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

@RestController
@RequestMapping(path = "registry-office")
public class RegistryOfficeController {

    @Autowired
    private RegistryOfficeRepository registryOfficeRepository;

    @PostMapping(path = "store")
    @ExceptionHandler(ApiCustomException.class)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public RegistryOffice store(@RequestBody RegistryOffice registryOffice) throws ApiCustomException {

        RegistryOfficeService.validateRegistryOffice(registryOffice);

        return registryOfficeRepository.save(registryOffice);
    }

    @GetMapping(path = "index")
    public List<RegistryOffice> index() {
        return registryOfficeRepository.findAll();
    }

    @GetMapping(path = "show/{registryOfficeId}")
    public Optional<RegistryOffice> show(
            @PathVariable Long registryOfficeId,
            HttpServletResponse response
    ) throws ApiCustomException {

        RegistryOffice registryOffice = registryOfficeRepository.findById(registryOfficeId).orElse(null);

        if (registryOffice == null) {
            response.setStatus(204);
        }

        return registryOfficeRepository.findById(registryOfficeId);
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
