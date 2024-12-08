package com.example.demofiveminutelog.controller;


import com.example.demofiveminutelog.common.tlo.TloLogMethod;
import com.example.demofiveminutelog.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @TloLogMethod(svcClass = "A01")
    @PostMapping("/member")
    public void createMember() {
        memberService.createMember();
    }



    @TloLogMethod(svcClass = "A02")
    @PostMapping("/memberDBError")
    public void createMemberDBError() {
        memberService.createMemberDBError();
    }


    @TloLogMethod(svcClass = "A03")
    @PostMapping("/memberServiceError")
    public void createMemberServiceError() {
        memberService.createMemberServiceError();
    }
}
