package org.mjulikelion.businessmessenger.dto.responsedata.employee;

import lombok.Builder;
import lombok.Getter;
import org.mjulikelion.businessmessenger.dto.responsedata.message.MessageResponseData;

import java.util.List;

@Builder
@Getter
public class EmployeeResponseData {
    private String name;
    private String email;
    private List<MessageResponseData> messages; // 받은 메시지함
}
