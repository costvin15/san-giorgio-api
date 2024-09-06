package com.desafio.san_giorgio_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.desafio.san_giorgio_api.repository.entities.ClientEntity;

@Repository
public interface ClientRepository extends JpaRepository<ClientEntity, String> {
    @Query(value = "SELECT EXISTS (SELECT 1 FROM clients WHERE client_id = :clientId)", nativeQuery = true)
    boolean checkIfClientExists(String clientId);
}
