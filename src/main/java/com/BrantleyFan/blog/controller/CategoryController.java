package com.BrantleyFan.blog.controller;

import com.BrantleyFan.blog.service.CategoryService;
import com.BrantleyFan.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/listCategory")
    private Result getCategoryList(){
        return categoryService.getCategoryList();
    }
}
