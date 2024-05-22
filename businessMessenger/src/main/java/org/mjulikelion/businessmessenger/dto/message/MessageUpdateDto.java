package org.mjulikelion.businessmessenger.dto.message;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class MessageUpdateDto {

    @NotBlank(message = "내용이 비어있습니다.")
    private String content;
}
