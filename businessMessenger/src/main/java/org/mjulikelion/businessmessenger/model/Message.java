package org.mjulikelion.businessmessenger.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.mjulikelion.businessmessenger.dto.message.MessageUpdateDto;
import org.mjulikelion.businessmessenger.status.Status;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "message")
public class Message extends BaseEntity {

    @Column(length = 2000, nullable = false)
    private String content;

    @Column(nullable = false)
    @Setter
    private Status status;

    @Column(nullable = false)
    private String senderEmail;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Employee employee;


    public void updateMessage(MessageUpdateDto messageUpdateDto){
        this.content = messageUpdateDto.getContent();
    }
}
