package com.alex.service.solo.impl;

import com.alex.entity.bo.ShopCategory;
import com.alex.entity.dto.Result;
import com.alex.service.solo.ShopCategoryService;
import org.alex.core.annotation.Service;

import java.util.List;

/**
 * @author Alex CHEN
 * @version 1.0
 * @since 2020-07-28 16:50
 */
@Service
public class ShopCategoryServiceImpl implements ShopCategoryService {

    @Override
    public Result<Boolean> addShopCategory(ShopCategory category) {
        return null;
    }

    @Override
    public Result<Boolean> removeShopCategory(int shopCategoryId) {
        return null;
    }

    @Override
    public Result<Boolean> modifyShopCategory(ShopCategory category) {
        return null;
    }

    @Override
    public Result<ShopCategory> queryShopCategoryById(int ShopCategoryId) {
        return null;
    }

    @Override
    public Result<List<ShopCategory>> queryShopCategory(ShopCategory category, int pageIndex, int pageSize) {
        return null;
    }
}
