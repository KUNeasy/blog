package com.lkun.blog.web;


import com.lkun.blog.NotFoundException;
import com.lkun.blog.service.BlogService;
import com.lkun.blog.service.CommentService;
import com.lkun.blog.service.TagService;
import com.lkun.blog.service.TypeService;
import com.lkun.blog.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * @author LK
 * @date 2019/8/25 21:56:52
 * @description
 */
@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/")
    public String index(@PageableDefault(size=8,sort={"updateTime"}, direction= Sort.Direction.DESC) Pageable pageable, BlogQuery blog, Model model){

        model.addAttribute("page",blogService.listBlog(pageable));
        model.addAttribute("types",typeService.listTypeTop(6));
        model.addAttribute("tags",tagService.listTagTop(10));
        model.addAttribute("recommendBlogs",blogService.listBlogTop(8));
        model.addAttribute("tags");
        return "index";
    }

    @PostMapping("/search")
    public String search(@PageableDefault(size=8,sort={"updateTime"}, direction= Sort.Direction.DESC) Pageable pageable, BlogQuery blog,
                         @RequestParam String query, Model model){
        model.addAttribute("page",blogService.listBlog("%"+query+"%",pageable));
        model.addAttribute("query",query);
        return  "search";
    }

    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id, Model model){
        model.addAttribute("blog",blogService.getAndConvert(id));
        //修改，第一次页面添加comments
        model.addAttribute("comments",commentService.listCommentByBlogId(id));
        return "blog";
    }

    @GetMapping("/footer/newblog")
    public String newblogs(Model model){
        model.addAttribute("newBlogs",blogService.listBlogTop(3));
        return "_fragments :: newBlogList";
    }
}
