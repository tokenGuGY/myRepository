package com.boot.security.server.controller.mycontroller;


import com.boot.security.server.common.response.BaseResponse;
import com.boot.security.server.dao.UserDao;
import com.boot.security.server.model.SysUser;
import com.boot.security.server.page.table.PageTableRequest;
import com.boot.security.server.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "我的用户")
@RestController
@RequestMapping("/myUser")
public class MyUserController {
    private static final Logger log = LoggerFactory.getLogger("adminLogger");


    @Autowired
    private UserService userService;
    @Autowired
    private UserDao userDao;


    @GetMapping
    @ApiOperation(value = "获取用户列表")
    /*@PreAuthorize("hasAuthority('sys:user:query')")*/
    public BaseResponse getListUser(PageTableRequest request){
        List<SysUser> list = userDao.list(request.getParams(), request.getOffset(), request.getLimit());
        return new BaseResponse(1,"成功",list);
    }
}
