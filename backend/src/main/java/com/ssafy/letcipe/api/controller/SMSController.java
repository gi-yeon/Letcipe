package com.ssafy.letcipe.api.controller;

import com.ssafy.letcipe.api.dto.sms.ReqPostSMSDto;
import com.ssafy.letcipe.api.dto.sms.ResPostSMSDto;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sms")
public class SMSController {
    private final DefaultMessageService messageService;

    public SMSController(){
        this.messageService= NurigoApp.INSTANCE.initialize("APIKEY 입력","API SECRET Key","https://api.coolsms.co.kr");
    }
    @PostMapping("")
    public ResponseEntity sendOne(@RequestBody ReqPostSMSDto requestDto) {
        Message message = new Message();
        // 발신번호 및 수신번호는 반드시 01012345678 형태로 입력되어야 합니다.
        message.setFrom("휴대폰 번호");
        message.setTo(requestDto.getPhoneNo());
        Integer randCode = (int)(Math.random()*1000000);
        message.setText("SSAFY 7기 특화 프로젝트 A705 인증코드 [" + randCode+"]");

        SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(message));

        return ResponseEntity.ok().body(new ResPostSMSDto(randCode.toString()));
    }
}
