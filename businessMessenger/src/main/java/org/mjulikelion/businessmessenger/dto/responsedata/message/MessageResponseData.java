package org.mjulikelion.businessmessenger.dto.responsedata.message;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
public class MessageResponseData {
    private String senderEmail;
    private String receiverEmail;
    private LocalDateTime sentAt;
    private UUID id;
    private String content;
}
