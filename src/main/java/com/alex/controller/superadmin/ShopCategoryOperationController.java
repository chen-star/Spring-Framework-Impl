package com.alex.controller.superadmin;

import com.alex.entity.bo.ShopCategory;
import com.alex.entity.dto.Result;
import com.alex.service.solo.ShopCategoryService;
import org.alex.core.annotation.Controller;
import org.alex.inject.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author Alex CHEN
 * @version 1.0
 * @since 2020-07-28 17:12
 */
@Controller
public class ShopCategoryOperationController {

    @Autowired
    private ShopCategoryService shopCategoryService;

    public Result<Boolean> addHeadLine(HttpServletRequest req, HttpServletResponse resp) {
        return shopCategoryService.addShopCategory(new ShopCategory());
    }

    public Result<Boolean> removeHeadLine(HttpServletRequest req, HttpServletResponse resp) {
        return shopCategoryService.removeShopCategory(1);
    }

    public Result<Boolean> modifyHeadLine(HttpServletRequest req, HttpServletResponse resp) {
        return shopCategoryService.modifyShopCategory(new ShopCategory());
    }

    public Result<ShopCategory> queryHeadLineById(HttpServletRequest req, HttpServletResponse resp) {
        return shopCategoryService.queryShopCategoryById(1);
    }

    public Result<List<ShopCategory>> queryHeadLine(HttpServletRequest req, HttpServletResponse resp) {
        return shopCategoryService.queryShopCategory(null, 1, 100);
    }
}
