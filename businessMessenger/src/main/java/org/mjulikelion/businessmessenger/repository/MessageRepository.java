package org.mjulikelion.businessmessenger.repository;

import org.mjulikelion.businessmessenger.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MessageRepository extends JpaRepository<Message, UUID> {
}
