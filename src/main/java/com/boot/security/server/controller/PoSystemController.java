package com.boot.security.server.controller;

import java.util.List;

import com.boot.security.server.dto.LoginUser;
import com.boot.security.server.utils.DateUtils;
import com.boot.security.server.utils.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.boot.security.server.page.table.PageTableRequest;
import com.boot.security.server.page.table.PageTableHandler;
import com.boot.security.server.page.table.PageTableResponse;
import com.boot.security.server.page.table.PageTableHandler.CountHandler;
import com.boot.security.server.page.table.PageTableHandler.ListHandler;
import com.boot.security.server.dao.PoSystemDao;
import com.boot.security.server.model.PoSystem;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/poSystems")
public class PoSystemController {

    @Autowired
    private PoSystemDao poSystemDao;
    @Autowired
    private Environment env;

    @PostMapping
    @ApiOperation(value = "保存")
    public PoSystem save(@RequestBody PoSystem poSystem) {
        //获当前登录的用户信息
        LoginUser user= UserUtil.getLoginUser();
        //系统code的前缀
        String port= env.getProperty("system.prefix");
        poSystem.setCreateUser(user.getUsername());
        poSystem.setCreateTime(DateUtils.getCurrentDateTime());
        poSystemDao.save(poSystem);
        return poSystem;
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据id获取")
    public PoSystem get(@PathVariable String id) {
        return poSystemDao.getById(id);
    }

    @PutMapping
    @ApiOperation(value = "修改")
    public PoSystem update(@RequestBody PoSystem poSystem) {
        LoginUser user= UserUtil.getLoginUser();
        poSystem.setUpdateUser(user.getUsername());
        poSystem.setUpdateTime(DateUtils.getCurrentDateTime());
        poSystemDao.update(poSystem);
        return poSystem;
    }

    @GetMapping
    @ApiOperation(value = "列表")
    public PageTableResponse list(PageTableRequest request) {
        return new PageTableHandler(new CountHandler() {

            @Override
            public int count(PageTableRequest request) {
                return poSystemDao.count(request.getParams());
            }
        }, new ListHandler() {

            @Override
            public List<PoSystem> list(PageTableRequest request) {
                return poSystemDao.list(request.getParams(), request.getOffset(), request.getLimit());
            }
        }).handle(request);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除")
    public void delete(@PathVariable String id) {
        poSystemDao.delete(id);
    }
}
