package com.example.demofiveminutelog.repository;

import com.example.demofiveminutelog.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {


}
