package com.alex.service.solo.impl;

import com.alex.entity.bo.HeadLine;
import com.alex.entity.dto.Result;
import com.alex.service.solo.HeadLineService;
import lombok.extern.slf4j.Slf4j;
import org.alex.core.annotation.Service;

import java.util.List;

/**
 * @author Alex CHEN
 * @version 1.0
 * @since 2020-07-28 16:49
 */
@Slf4j
@Service
public class HeadLineServiceImpl implements HeadLineService {

    @Override
    public Result<Boolean> addHeadLine(HeadLine headLine) {
        log.info("I'm adding Headline!");
        return null;
    }

    @Override
    public Result<Boolean> removeHeadLine(int headLineId) {
        return null;
    }

    @Override
    public Result<Boolean> modifyHeadLine(HeadLine headLine) {
        return null;
    }

    @Override
    public Result<HeadLine> queryHeadLineById(int headLineId) {
        return null;
    }

    @Override
    public Result<List<HeadLine>> queryHeadLine(HeadLine headLineCondition, int pageIndex, int pageSize) {
        return null;
    }
}
