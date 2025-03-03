package com.frankit.repository;

import com.frankit.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUuid(Long uuid);
    Optional<Member> findByEmail(String email);
}
