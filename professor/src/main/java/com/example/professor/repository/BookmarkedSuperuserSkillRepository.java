package com.example.professor.repository;

import com.example.professor.entity.BookmarkedSuperuserSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookmarkedSuperuserSkillRepository extends JpaRepository<BookmarkedSuperuserSkill, String> {
    @Query("SELECT b FROM BookmarkedSuperuserSkill b WHERE b.superuserId = :superuserId")
    List<BookmarkedSuperuserSkill> findAllBySuperuserId(String superuserId);

    @Query("SELECT b FROM BookmarkedSuperuserSkill b WHERE b.superuserId = :superuserId AND b.skillId = :skillId")
    Optional<BookmarkedSuperuserSkill> findBySuperuserIdAndSkillId(String superuserId, String skillId);
} 