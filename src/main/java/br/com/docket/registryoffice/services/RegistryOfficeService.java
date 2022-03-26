package br.com.docket.registryoffice.services;

import br.com.docket.registryoffice.exceptions.ApiCustomException;
import br.com.docket.registryoffice.models.RegistryOffice;


public class RegistryOfficeService {

    public static void validateRegistryOffice(RegistryOffice registryOffice) throws ApiCustomException {
        if (registryOffice.getName() == null) {
            throw new ApiCustomException("Por favor informe um nome para o Cartório.");
        }

        if (registryOffice.getAddress() == null) {
            throw new ApiCustomException("Por favor informe um endereço para o Cartório.");
        }
    }
}
