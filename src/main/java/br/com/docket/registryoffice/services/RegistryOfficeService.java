package br.com.docket.registryoffice.services;

import br.com.docket.registryoffice.models.RegistryOffice;
import br.com.docket.registryoffice.repository.RegistryOfficeRepository;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class RegistryOfficeService {

    private final RegistryOfficeRepository registryOfficeRepository;
    public RegistryOfficeService(RegistryOfficeRepository registryOfficeRepository) {
        this.registryOfficeRepository = registryOfficeRepository;
    }

    public String checkIfRegistryOfficeExistsById(Long registryOfficeId) {

        if (!this.registryOfficeRepository.existsById(registryOfficeId)) {
            return "ID do Cartório informado não existe.";
        }

        return "";
    }

    public String checkIfRegistryOfficeNameAlreadyExists(RegistryOffice registryOffice) {

        RegistryOffice registryNameOffice =
                this.registryOfficeRepository.findByNameContainingIgnoreCase(registryOffice.getName()).orElse(null);

        if (registryNameOffice != null) {
            return "Já existe um Cartório com o nome informado.";
        }

        return "";
    }

    public String validateRegistryOffice(RegistryOffice registryOffice) {
        if (registryOffice.getName() == null) {
            return "Por favor informe um nome para o Cartório.";
        }

        if (registryOffice.getAddress() == null) {
            return "Por favor informe um endereço para o Cartório.";
        }

        return "";
    }

    public RegistryOffice save(RegistryOffice registryOffice) {
        return registryOfficeRepository.save(registryOffice);
    }
}
