package br.com.docket.registryoffice.services;

import br.com.docket.registryoffice.models.Certificate;
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
        // percorre as certidões e salva as que forem novas (considerando o ID) no banco local
        for (Object certificate: certificates) {
            int certificateId = ((LinkedHashMap) certificate).get("id").hashCode();
            String certificateNome = ((LinkedHashMap) certificate).get("nome").toString();

            boolean getCertificate = this.certificateRepository.existsById((long) certificateId);

            if (!getCertificate) {
                Certificate newCertificate = new Certificate();

                newCertificate.setId((long) certificateId);
                newCertificate.setNome(certificateNome);

                certificateRepository.save(newCertificate);
                System.out.println("Certidão "+ certificateNome +" salva com sucesso!");
            }
        }
    }
}
