package com.alex.service.combine;

import com.alex.entity.dto.MainPageInfoDTO;
import com.alex.entity.dto.Result;

/**
 * @author Jiaxin CHEN
 * @version 1.0
 * @since 2020-07-28-16-51
 */
public interface HeadLineShopCategoryCombineService {

    Result<MainPageInfoDTO> getMainPageInfo();
}
