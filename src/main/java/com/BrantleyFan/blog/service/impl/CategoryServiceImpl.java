package com.BrantleyFan.blog.service.impl;

import com.BrantleyFan.blog.mapper.CategoryMapper;
import com.BrantleyFan.blog.pojo.Category;
import com.BrantleyFan.blog.service.CategoryService;
import com.BrantleyFan.blog.vo.Result;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public Category findCategoryNameById(int categoryId) {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(Category::getId,categoryId);
        return categoryMapper.selectOne(queryWrapper);
    }

    @Override
    public Result getCategoryList() {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        return Result.success(categoryMapper.selectList(queryWrapper));
    }
}
