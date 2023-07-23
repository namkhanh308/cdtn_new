package com.cdtn.kltn.service;

import com.cdtn.kltn.common.UtilsPage;
import com.cdtn.kltn.entity.Blogs;
import com.cdtn.kltn.exception.StoreException;
import com.cdtn.kltn.repository.blogs.BlogsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BlogsService {

    private final BlogsRepository blogsRepository;

    public void addBlogs(Blogs blogs){
        blogsRepository.save(blogs);
    }

    public void deleteBlogs(Long id){
        blogsRepository.deleteById(id);
    }

    public Page<?> getPage(String searchName, int page, int size){
        Pageable pageable = UtilsPage.getPage("DESC", "id", page, size);
        return blogsRepository.findAllByTitleContaining(searchName, pageable);
    }

    public Blogs detailBlogs(Long id){
        return blogsRepository.findById(id).orElseThrow(() -> new StoreException("Không tìm thấy blogs"));
    }

}
