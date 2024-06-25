package org.example.marketing.repo;

import org.example.marketing.entity.Blogger;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BloggerRepository extends JpaRepository<Blogger, Integer> {
    Optional<Blogger> findBloggerByName(String blogger);
}