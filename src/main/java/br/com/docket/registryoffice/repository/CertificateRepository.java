package br.com.docket.registryoffice.repository;

import br.com.docket.registryoffice.models.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CertificateRepository  extends JpaRepository<Certificate, Long> {

    Optional<Certificate> findByNome(String nome);
}
