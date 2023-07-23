package com.cdtn.kltn.repository.blogs;

import com.cdtn.kltn.entity.Blogs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BlogsRepository extends JpaRepository<Blogs, Long> {
    @Query("SELECT b FROM Blogs b WHERE b.title LIKE %:title%")
    Page<Blogs> findAllByTitleContaining(String title, Pageable pageable);
}
