package br.com.docket.registryoffice.controllers;

import br.com.docket.registryoffice.exceptions.ApiCustomException;
import br.com.docket.registryoffice.models.RegistryOffice;
import br.com.docket.registryoffice.repository.RegistryOfficeRepository;
import br.com.docket.registryoffice.services.RegistryOfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping(path = "registry-office")
public class RegistryOfficeController {

    @Autowired
    private RegistryOfficeRepository registryOfficeRepository;

    @GetMapping(path = "index")
    public String index(Model model) {

        List<RegistryOffice> allRegistryOffices = registryOfficeRepository.findAll();

        model.addAttribute("allRegistryOffices", allRegistryOffices);

        return "registry-office/index";
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

    @GetMapping(path = "new-registry-office")
    public String newRegistryOffice(@ModelAttribute("registryOffice") RegistryOffice registryOffice)
    {
        return "/registry-office/store";
    }

    @PostMapping(path = "store")
    public String store(@ModelAttribute("registryOffice") RegistryOffice registryOffice) {

        registryOfficeRepository.save(registryOffice);

        return "redirect:index";
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
    public String delete(@PathVariable Long registryOfficeId) {

        registryOfficeRepository.deleteById(registryOfficeId);

        return "redirect:index";
    }

    @GetMapping(path = "about")
    public String about()
    {
        return "registry-office/about";
    }
}
