package com.alex.service.combine.impl;

import com.alex.entity.bo.HeadLine;
import com.alex.entity.bo.ShopCategory;
import com.alex.entity.dto.MainPageInfoDTO;
import com.alex.entity.dto.Result;
import com.alex.service.combine.HeadLineShopCategoryCombineService;
import com.alex.service.solo.HeadLineService;
import com.alex.service.solo.ShopCategoryService;
import org.alex.core.annotation.Service;
import org.alex.inject.annotation.Autowired;

import java.util.List;

/**
 * @author Alex CHEN
 * @version 1.0
 * @since 2020-07-28 16:52
 */
@Service
public class HeadLineShopCategoryCombineServiceImpl implements HeadLineShopCategoryCombineService {

    @Autowired
    private HeadLineService headLineService;

    @Autowired
    private ShopCategoryService shopCategoryService;

    @Override
    public Result<MainPageInfoDTO> getMainPageInfo() {
        HeadLine headLineCondition = new HeadLine();
        headLineCondition.setEnableStatus(1);
        Result<List<HeadLine>> headLineResult = headLineService.queryHeadLine(headLineCondition, 1, 4);

        ShopCategory shopCategoryCondition = new ShopCategory();
        Result<List<ShopCategory>> shopCategoryResult = shopCategoryService.queryShopCategory(shopCategoryCondition, 1, 100);

        Result<MainPageInfoDTO>  result = mergeMainPageInfoResult(headLineResult, shopCategoryResult);
        return result;
    }

    private Result<MainPageInfoDTO> mergeMainPageInfoResult(Result<List<HeadLine>> headLineResult, Result<List<ShopCategory>> shopCategoryResult) {
        return null;
    }
}
