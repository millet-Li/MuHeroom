package com.room.controller;


import com.alibaba.fastjson.JSON;
import com.room.dto.Visitors;
import com.room.mapper.VisitorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class VisitorController {
    @Autowired
    private VisitorMapper visitorMapper;

    @ResponseBody
    @RequestMapping("/getRoomVisitor")
    public String getRoomVisitor(@RequestParam("v_master") String v_master,
                                 @RequestParam("page") int page){
        int pages = page*8;
        List<Visitors> roomVisitor = visitorMapper.getRoomVis(v_master,pages);
        if (roomVisitor != null){
            String json = JSON.toJSONString(roomVisitor);
            /*System.out.println(json);*/
            return json;
        }
        return "no visitor";
    }

}
