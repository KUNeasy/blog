package com.lkun.blog.service;

import com.lkun.blog.po.Blog;
import com.lkun.blog.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * @author LK
 * @date 2019/9/3 21:25:23
 * @description
 */
public interface BlogService {
    Blog getBlog(Long id);

    Blog getAndConvert(Long id);

    Page<Blog> listBlog(Pageable pageable, BlogQuery blog);

    Page<Blog> listBlog(Pageable pageable);

    Page<Blog> listBlog(Long id,Pageable pageable);

    Page<Blog> listBlog(String query,Pageable pageable);

    Map<String,List<Blog>> archiveBlog();

    Long countBlog();

    List<Blog> listBlogTop(Integer size);

    Blog saveBlog(Blog blog);

    Blog updateBlog(long id, Blog blog);

    void deleteBlog(Long id);
}
