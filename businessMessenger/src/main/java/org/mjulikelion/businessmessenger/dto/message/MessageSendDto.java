package org.mjulikelion.businessmessenger.dto.message;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

import java.util.List;


@Getter
public class MessageSendDto {
    @NotBlank(message = "내용이 비어있습니다.")
    private String content;

    @NotBlank(message = "받는 사람이 비었습니다.")
    @Pattern(regexp = "^[A-Za-z0-9]+@[A-Za-z0-9]+.[A-Za-z]{2,6}$",
            message = "이메일 형식이 맞지 않습니다.")
    private List<String> receivers;
}
