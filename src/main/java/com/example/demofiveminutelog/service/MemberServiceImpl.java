package com.example.demofiveminutelog.service;


import com.example.demofiveminutelog.common.exception.InvalidRequestException;
import com.example.demofiveminutelog.entity.Member;
import com.example.demofiveminutelog.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;

    @Override
    public void createMember() {
        Member alice = Member.builder()
                .name("Alice")
                .age(3)
                .birthday(LocalDate.of(2021, 1, 1))
                .build();

        memberRepository.save(alice);
    }

    @Override
    public void createMemberDBError() {
        Member alice = Member.builder()
                .name("Alice-Alice-Alice-Alice-Alice-Alice-Alice-Alice-Alice-Alice-Alice-Alice-Alice-")  //max length 30
                .age(3)
                .birthday(LocalDate.of(2021, 1, 1))
                .build();

        memberRepository.save(alice);
    }

    @Override
    public void createMemberServiceError() {
        throw new InvalidRequestException("0001", "Invalid Request");
    }
}
