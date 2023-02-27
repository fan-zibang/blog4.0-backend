package com.BrantleyFan.blog.controller;

import com.BrantleyFan.blog.pojo.Tag;
import com.BrantleyFan.blog.service.TagService;
import com.BrantleyFan.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tag")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/tagList")
    public Result getTagList(){
        return tagService.getTagList();
    }

    @PostMapping("/edit")
    public Result editTag(@RequestBody Tag tagParams){
        return tagService.editTag(tagParams);
    }

    @PostMapping("/delete/{id}")
    public Result deleteTag(@PathVariable int id){
        return tagService.deleteTag(id);
    }
}
