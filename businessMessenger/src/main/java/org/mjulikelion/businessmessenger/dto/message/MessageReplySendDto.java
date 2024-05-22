package org.mjulikelion.businessmessenger.dto.message;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MessageReplySendDto {
    @NotBlank(message = "내용이 비어있습니다.")
    private String content;
}
