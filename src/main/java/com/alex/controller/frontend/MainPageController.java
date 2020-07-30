package com.alex.controller.frontend;

import com.alex.entity.dto.MainPageInfoDTO;
import com.alex.entity.dto.Result;
import com.alex.service.combine.HeadLineShopCategoryCombineService;
import lombok.Getter;
import org.alex.core.annotation.Controller;
import org.alex.inject.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Alex CHEN
 * @version 1.0
 * @since 2020-07-28 17:11
 */
@Controller
@Getter
public class MainPageController {
    @Autowired(value = "HeadLineShopCategoryCombineServiceImpl")
    private HeadLineShopCategoryCombineService headLineShopCategoryCombineService;

    public Result<MainPageInfoDTO> getMainPageInfo(HttpServletRequest req, HttpServletResponse resp) {
        return headLineShopCategoryCombineService.getMainPageInfo();
    }
}
