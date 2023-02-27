package com.BrantleyFan.blog.service;

import com.BrantleyFan.blog.pojo.Category;
import com.BrantleyFan.blog.vo.Result;

public interface CategoryService {
    Category findCategoryNameById(int categoryId);

    Result getCategoryList();
}
