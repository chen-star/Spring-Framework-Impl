package com.alex.service.solo;

import com.alex.entity.bo.HeadLine;
import com.alex.entity.dto.Result;

import java.util.List;

/**
 * @author Alex CHEN
 * @version 1.0
 * @since 2020-07-28 16:45
 */
public interface HeadLineService {
    Result<Boolean> addHeadLine(HeadLine headLine);

    Result<Boolean> removeHeadLine(int headLineId);

    Result<Boolean> modifyHeadLine(HeadLine headLine);

    Result<HeadLine> queryHeadLineById(int headLineId);

    Result<List<HeadLine>> queryHeadLine(HeadLine headLineCondition, int pageIndex, int pageSize);
}
