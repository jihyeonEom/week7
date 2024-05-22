package org.mjulikelion.businessmessenger.controller;

import lombok.AllArgsConstructor;
import org.mjulikelion.businessmessenger.authentication.employee.AuthenticatedEmployee;
import org.mjulikelion.businessmessenger.dto.message.MessageReplySendDto;
import org.mjulikelion.businessmessenger.dto.message.MessageSendDto;
import org.mjulikelion.businessmessenger.dto.message.MessageUpdateDto;
import org.mjulikelion.businessmessenger.dto.response.ResponseDto;
import org.mjulikelion.businessmessenger.dto.responsedata.message.MessageResponseData;
import org.mjulikelion.businessmessenger.model.Employee;
import org.mjulikelion.businessmessenger.service.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@AllArgsConstructor
public class MessageController {

    private final MessageService messageService;

    // 메시지 전송하기
    @PostMapping("/messages")
    public ResponseEntity<ResponseDto<Void>> sendMessage(@AuthenticatedEmployee Employee employee,
                                                         @RequestBody MessageSendDto messageSendDto) {
        this.messageService.sendMessage(employee, messageSendDto);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.CREATED, "create Message"), HttpStatus.CREATED);
    }

    // 답장 전송하기
    @PostMapping("/messages/reply/{msgId}")
    public ResponseEntity<ResponseDto<Void>> sendMessageReply(@AuthenticatedEmployee Employee employee,
                                                              @PathVariable("msgId") UUID msgIdToReply,
                                                              @RequestBody MessageReplySendDto messageReplySendDto) {
        this.messageService.sendMessageReply(employee, msgIdToReply, messageReplySendDto);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.CREATED, "reply Message"), HttpStatus.CREATED);
    }

    // 메시지 아이디로 메시지 조회하기
    @GetMapping("/messages/{msgId}")
    public ResponseEntity<ResponseDto<MessageResponseData>> getMessage(@PathVariable("msgId") UUID id) {
        MessageResponseData messageResponseData = this.messageService.getMessage(id);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "get Message", messageResponseData), HttpStatus.OK);
    }

    // 메시지 수정하기
    @PatchMapping("/messages/{msgId}")
    public ResponseEntity<ResponseDto<Void>> updateMessage(@AuthenticatedEmployee Employee employee,
                                                           @PathVariable("msgId") UUID id,
                                                           @RequestBody MessageUpdateDto messageUpdateDto) {
        this.messageService.validateAccessAndUpdatable(employee, id);
        this.messageService.updateMessage(id, messageUpdateDto);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "Message updated"), HttpStatus.OK);
    }

    // 메시지 삭제하기
    @DeleteMapping("/messages/{msgId}")
    public ResponseEntity<ResponseDto<Void>> deleteMessage(@AuthenticatedEmployee Employee employee,
                                                           @PathVariable("msgId") UUID id) {
        this.messageService.validateAccessAndUpdatable(employee, id);
        this.messageService.deleteMessage(id);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "Message deleted"), HttpStatus.OK);
    }

}
