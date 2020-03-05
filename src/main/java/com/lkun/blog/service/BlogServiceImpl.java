package com.lkun.blog.service;

import com.lkun.blog.NotFoundException;
import com.lkun.blog.Util.MarkdownUtils;
import com.lkun.blog.Util.MyBeanUtils;
import com.lkun.blog.dao.BlogRespository;
import com.lkun.blog.po.Blog;
import com.lkun.blog.po.Type;
import com.lkun.blog.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.*;

/**
 * @author LK
 * @date 2019/9/3 21:28:39
 * @description
 */
@Service
public class BlogServiceImpl implements BlogService{

    @Autowired
    private BlogRespository blogRespository;

    @Override
    public Blog getBlog(Long id) {
        return blogRespository.getOne(id);
    }

    @Transactional
    @Override
    public Blog getAndConvert(Long id) {
        Blog blog = blogRespository.getOne(id);
        if(blog == null){
            throw new NotFoundException("该博客不存在");
        }
        if(blog.getViews()==null){
            blog.setViews(0);
        }
        blogRespository.updateViews(id);
        System.out.println("博客阅读量："+blog.getViews());
        Blog b = new Blog();
        BeanUtils.copyProperties(blog,b);
        String content = b.getContent();
        b.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
        return b;
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {
        return blogRespository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if(!"".equals(blog.getTitle())&& blog.getTitle() != null){
                    predicates.add(cb.like(root.<String>get("title"),"%" + blog.getTitle()));
                }
                if(blog.getTypeId() != null){
                    predicates.add(cb.equal(root.<Type>get("type").get("id"), blog.getTypeId()));
                }
                if(blog.isRecommend()){
                    predicates.add(cb.equal(root.get("recommend"),blog.isRecommend()));
                }
                cq.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        }, pageable);
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable) {
        return blogRespository.findAll(pageable);
    }

    @Override
    public Page<Blog> listBlog(Long tagId, Pageable pageable) {
        return blogRespository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> aq, CriteriaBuilder cb) {
                Join join = root.join("tags");
                return cb.equal(join.get("id"),tagId);
            }
        },pageable);
    }

    @Override
    public Page<Blog> listBlog(String query, Pageable pageable) {
        return blogRespository.findByQuery(query,pageable);
    }

    @Override
    public Map<String, List<Blog>> archiveBlog() {
        List<String> years = blogRespository.findGroupYear();
        Map<String,List<Blog>> map = new HashMap<>();
        for(String year : years){
            map.put(year,blogRespository.findByYear(year));
        }
        return map;
    }

    @Override
    public Long countBlog() {
        return blogRespository.count();
    }

    @Override
    public List<Blog> listBlogTop(Integer size) {
        Sort sort = new Sort(Sort.Direction.DESC,"updateTime");
        Pageable pageable = new PageRequest(0,size,sort);
        return blogRespository.findTop(pageable);
    }

    @Transactional
    @Override
    public Blog saveBlog(Blog blog) {
        if(blog.getId()==null){
            blog.setCreateTime(new Date());
            blog.setUpdateTime(new Date());
            blog.setViews(0);
        } else{
            blog.setUpdateTime(new Date());
        }

        return blogRespository.save(blog);
    }

    @Transactional
    @Override
    public Blog updateBlog(long id, Blog blog) {
        Blog b = blogRespository.getOne(id);
        if(b == null){
            throw new NotFoundException("该博客不存在");
        }
        BeanUtils.copyProperties(blog,b, MyBeanUtils.getNullPropertyNames(blog));
        b.setUpdateTime(new Date());
        return blogRespository.save(blog);
    }

    @Transactional
    @Override
    public void deleteBlog(Long id) {
        blogRespository.deleteById(id);

    }
}
