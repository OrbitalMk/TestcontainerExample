package com.example.testcontainer.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostJdbcRepository extends JpaRepository<Post, Long> {
}
