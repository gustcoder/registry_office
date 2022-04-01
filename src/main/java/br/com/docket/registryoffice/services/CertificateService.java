package br.com.docket.registryoffice.services;

import br.com.docket.registryoffice.repository.CertificateRepository;

import java.util.LinkedHashMap;
import java.util.List;

public class CertificateService {

    private final CertificateRepository certificateRepository;

    public CertificateService(CertificateRepository certificateRepository) {
        this.certificateRepository = certificateRepository;
    }

    public void saveCertificates(List<Object> certificates)
    {
        for (Object certificate: certificates) {
            int certificateId = ((LinkedHashMap) certificate).get("id").hashCode();

            boolean getCertificate = this.certificateRepository.existsById((long) certificateId);

            if (!getCertificate) {
                // certificateRepository.save(certificate);
            }

            System.out.println("Salvar certificado");
        }
    }
}
