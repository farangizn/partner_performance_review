package org.example.marketing.repo;

import org.example.marketing.entity.Blogger;
import org.example.marketing.entity.BloggerClient;
import org.example.marketing.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BloggerClientRepository extends JpaRepository<BloggerClient, Integer> {

     List<BloggerClient> findBloggerClientByBlogger(Blogger blogger);
}