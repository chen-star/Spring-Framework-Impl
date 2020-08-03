package com.alex.controller.superadmin;

import com.alex.entity.bo.HeadLine;
import com.alex.entity.dto.Result;
import com.alex.service.solo.HeadLineService;
import lombok.extern.slf4j.Slf4j;
import org.alex.core.annotation.Controller;
import org.alex.inject.annotation.Autowired;
import org.alex.mvc.annotation.RequestMapping;
import org.alex.mvc.annotation.RequestParam;
import org.alex.mvc.annotation.ResponseBody;
import org.alex.mvc.type.ModelAndView;
import org.alex.mvc.type.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author Alex CHEN
 * @version 1.0
 * @since 2020-07-28 17:12
 */
@Slf4j
@Controller
@RequestMapping(value = "/headline")
public class HeadLineOperationController {

    @Autowired(value = "HeadLineServiceImpl")
    private HeadLineService headLineService;

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView addHeadLine(@RequestParam("lineName") String lineName,
                                    @RequestParam("lineLink") String lineLink,
                                    @RequestParam("lineImg") String lineImg,
                                    @RequestParam("priority") Integer priority) {
        HeadLine headLine = new HeadLine();
        headLine.setLineName(lineName);
        headLine.setLineLink(lineLink);
        headLine.setLineImg(lineImg);
        headLine.setPriority(priority);
        Result<Boolean> result = headLineService.addHeadLine(headLine);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setView("addheadline.jsp").addViewData("result", result);
        return modelAndView;
    }

    @RequestMapping(value = "/remove", method = RequestMethod.GET)
    public Result<Boolean> removeHeadLine() {
        return headLineService.removeHeadLine(1);
    }

    public Result<Boolean> modifyHeadLine(HttpServletRequest req, HttpServletResponse resp) {
        return headLineService.modifyHeadLine(new HeadLine());
    }

    public Result<HeadLine> queryHeadLineById(HttpServletRequest req, HttpServletResponse resp) {
        return headLineService.queryHeadLineById(1);
    }

    @RequestMapping(value = "/query", method = RequestMethod.GET)
    @ResponseBody
    public Result<List<HeadLine>> queryHeadLine() {
        return headLineService.queryHeadLine(null, 1, 100);
    }
}
