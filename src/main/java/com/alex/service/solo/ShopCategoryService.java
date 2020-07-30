package com.alex.service.solo;

import com.alex.entity.bo.ShopCategory;
import com.alex.entity.dto.Result;

import java.util.List;

/**
 * @author Alex CHEN
 * @version 1.0
 * @since 2020-07-28 16:45
 */
public interface ShopCategoryService {
    Result<Boolean> addShopCategory(ShopCategory category);

    Result<Boolean> removeShopCategory(int shopCategoryId);

    Result<Boolean> modifyShopCategory(ShopCategory category);

    Result<ShopCategory> queryShopCategoryById(int ShopCategoryId);

    Result<List<ShopCategory>> queryShopCategory(ShopCategory category, int pageIndex, int pageSize);
}
