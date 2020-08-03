package com.alex.service.solo.impl;

import com.alex.entity.bo.HeadLine;
import com.alex.entity.dto.Result;
import com.alex.service.solo.HeadLineService;
import lombok.extern.slf4j.Slf4j;
import org.alex.core.annotation.Service;

import javax.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
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
        log.info(headLine.toString());
        Result<Boolean> result = new Result<>();
        result.setCode(HttpServletResponse.SC_OK);
        result.setMsg("Add success");
        result.setData(true);
        return result;
    }

    @Override
    public Result<Boolean> removeHeadLine(int headLineId) {
        Result<Boolean> result = new Result<>();
        result.setCode(HttpServletResponse.SC_OK);
        result.setMsg("Delete Success!");
        log.info("[HeadLineServiceImpl - removeHeadLine] Delete Success");
        return result;
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
        List<HeadLine> headLineList = new ArrayList<>();

        HeadLine headLine1 = new HeadLine();
        headLine1.setLineId(0l);
        headLine1.setLineName("Head line 1");
        headLine1.setPriority(0);
        headLine1.setLineLink("https://www.github.com");
        headLine1.setCreateTime(Date.valueOf(LocalDate.now()));
        HeadLine headLine2 = new HeadLine();
        headLine2.setLineId(1l);
        headLine2.setLineName("Head line 1");
        headLine2.setPriority(1);
        headLine2.setLineLink("https://www.google.com");
        headLine2.setCreateTime(Date.valueOf(LocalDate.now()));
        headLineList.add(headLine1);
        headLineList.add(headLine2);

        Result<List<HeadLine>> result = new Result<>();
        result.setData(headLineList);
        result.setCode(HttpServletResponse.SC_OK);
        result.setMsg("Query Headline success");
        return result;
    }
}
