package br.com.docket.registryoffice.repository;

import br.com.docket.registryoffice.models.RegistryOffice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RegistryOfficeRepository extends JpaRepository<RegistryOffice, Long> {

    Optional<RegistryOffice> findByName(String name);
}
