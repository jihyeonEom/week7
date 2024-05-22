package org.mjulikelion.businessmessenger.service;

import lombok.AllArgsConstructor;
import org.mjulikelion.businessmessenger.dto.message.MessageReplySendDto;
import org.mjulikelion.businessmessenger.dto.message.MessageSendDto;
import org.mjulikelion.businessmessenger.dto.message.MessageUpdateDto;
import org.mjulikelion.businessmessenger.dto.responsedata.message.MessageResponseData;
import org.mjulikelion.businessmessenger.errorcode.ErrorCode;
import org.mjulikelion.businessmessenger.exception.ForbiddenException;
import org.mjulikelion.businessmessenger.exception.NotFoundException;
import org.mjulikelion.businessmessenger.model.Employee;
import org.mjulikelion.businessmessenger.model.Message;
import org.mjulikelion.businessmessenger.repository.EmployeeRepository;
import org.mjulikelion.businessmessenger.repository.MessageRepository;
import org.mjulikelion.businessmessenger.status.Status;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final EmployeeRepository employeeRepository;
    private static final String REPLY_TYPE = "Re: ";

    // 메시지 전송하기
    public void sendMessage(Employee sender, MessageSendDto messageSendDto) {
        List<Employee> receivers = new ArrayList<>();

        for(String email : messageSendDto.getReceivers()){
            Employee employee = employeeRepository.findByEmail(email);
            if(employee == null){
                throw new NotFoundException(ErrorCode.EMPLOYEE_NOT_FOUND);
            }
            receivers.add(employee);
        }

        for(Employee receiver : receivers){
            assert sender != null;
            Message message = Message.builder()
                    .content(messageSendDto.getContent())
                    .status(Status.NOT_READ)
                    .employee(receiver)
                    .senderEmail(sender.getEmail())
                    .build();
            receiver.getMessages().add(message);
            this.messageRepository.save(message);
        }
    }

    // 답장 전송하기
    public void sendMessageReply(Employee sender, UUID msgIdToReply, MessageReplySendDto messageReplySendDto) {
        Message messageToReply = this.messageRepository.findById(msgIdToReply).orElseThrow(
                () -> new NotFoundException(ErrorCode.MESSAGE_NOT_FOUND)); // 답장 할 메시지
        Employee receiver = this.employeeRepository.findByEmail(messageToReply.getSenderEmail()); // 메시지 보낸 사람 == 답장을 받을 사람

        // 답장 형식으로 바꾸기
        String content = REPLY_TYPE + messageToReply.getId() + " " + messageReplySendDto.getContent();

        // 답장으로 보낼 메시지
        Message message = Message.builder()
                .content(content)
                .status(Status.NOT_READ)
                .employee(receiver)
                .senderEmail(sender.getEmail())
                .build();
        receiver.getMessages().add(message);
        this.messageRepository.save(message);
    }

    // 메시지 아이디로 메시지 조회하기
    public MessageResponseData getMessage(UUID id) {
        Message message = messageRepository.findById(id).orElseThrow(() -> new NotFoundException(ErrorCode.MESSAGE_NOT_FOUND));
        return MessageResponseData.builder()
                .senderEmail(message.getSenderEmail())
                .receiverEmail(message.getEmployee().getEmail())
                .sentAt(message.getCreatedAt())
                .id(message.getId())
                .content(message.getContent())
                .build();
    }

    // 메시지 수정하기
    public void updateMessage(UUID id, MessageUpdateDto messageUpdateDto) {
        Message message = messageRepository.findById(id).orElseThrow(() -> new NotFoundException(ErrorCode.MESSAGE_NOT_FOUND));
        message.updateMessage(messageUpdateDto);
        this.messageRepository.save(message);
    }

    // 메시지 삭제하기
    public void deleteMessage(UUID id) {
        Message message = messageRepository.findById(id).orElseThrow(() -> new NotFoundException(ErrorCode.MESSAGE_NOT_FOUND));
        this.messageRepository.delete(message);
    }


    // 메시지가 수정 및 삭제 가능한가
    public void isUpdatable(Message message) {
        if(message.getStatus() == Status.READ) {
            throw new ForbiddenException(ErrorCode.MESSAGE_CANNOT_BE_EDITED);
        }
    }

    // 메시지 수정 및 삭제 권한 확인하기
    public void validateAccessAndUpdatable(Employee employee, UUID msgId) {
        Message message = messageRepository.findById(msgId).orElseThrow(() -> new NotFoundException(ErrorCode.MESSAGE_NOT_FOUND));
        this.isUpdatable(message);

        if(!message.getSenderEmail().equals(employee.getEmail())) {
            throw new ForbiddenException(ErrorCode.FORBIDDEN_EMPLOYEE);
        }
    }
}
