package com.example.wisdombooks.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.wisdombooks.common.BaseContext;
import com.example.wisdombooks.common.R;
import com.example.wisdombooks.entity.Admin;
import com.example.wisdombooks.entity.Notice;
import com.example.wisdombooks.entity.Recharge;
import com.example.wisdombooks.entity.Vip;
import com.example.wisdombooks.service.AdminService;
import com.example.wisdombooks.service.NoticeService;
import com.example.wisdombooks.service.RechargeService;
import com.example.wisdombooks.service.VipService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private NoticeService noticeService;
    @Autowired
    private VipService vipService;
    @Autowired
    private RechargeService rechargeService;

    /**
     * 登录
     *
     * @param request
     * @param admin
     * @return
     */
    @PostMapping("/login")
    public R<String> login(HttpServletRequest request, @RequestBody Admin admin) {
        log.info("管理员账号:{}登录", admin.getIdCard());
        // 根据用户提交的idCard查询数据库(用户名为唯一属性)
        LambdaQueryWrapper<Admin> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Admin::getIdCard, admin.getIdCard());
        Admin one = adminService.getOne(lambdaQueryWrapper);
        // 没有查询到结果
        if (one == null) {
            return R.error("登录失败!");
        }

        // 密码比对
        if (!one.getPassWd().equals(admin.getPassWd())) {
            return R.error("登录失败!");
        }

        // 登录成功，将idCard存入Session并返回登录成功结果
        request.getSession().setAttribute("idCard", one.getIdCard());
        BaseContext.setCurrentId(one.getIdCard());
        return R.success("登录成功!");
    }

    /**
     * 退出登录
     *
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request) {
        log.info("退出登录!");
        request.getSession().removeAttribute("idCard");
        BaseContext.remove();
        return R.success("退出成功!");
    }

    /**
     * 修改密码
     *
     * @param newPassWd
     * @return
     */
    @PutMapping("/update")
    public R<String> update(HttpServletRequest request, String newPassWd) {
        UpdateWrapper<Admin> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id_card", BaseContext.getCurrentId())
                .set("passWd", newPassWd);
        adminService.update(updateWrapper);
        request.getSession().removeAttribute("idCard");
        BaseContext.remove();
        return R.success("修改密码成功,请重新登录!");
    }

    /**
     * 创建公告
     *
     * @param notice
     * @return
     */
    @PostMapping("/create")
    public R<String> create(@RequestBody Notice notice) {
        notice.setUId(BaseContext.getCurrentId());
        notice.setUName("管理员");
        boolean b = noticeService.save(notice);
        if (b) {
            return R.success("创建公告成功!");
        } else {
            return R.error("创建公告失败!");
        }
    }


    /**
     * 修改公告
     *
     * @param notice
     * @return
     */
    @PostMapping("/updateNotice")
    public R<String> updateNotice(@RequestBody Notice notice) {
        notice.setUId(BaseContext.getCurrentId());
        notice.setUName("管理员");
        boolean b = noticeService.updateById(notice);
        if (b) {
            return R.success("修改公告成功!");
        } else {
            return R.error("修改公告失败!");
        }
    }


    /**
     * 删除公告
     *
     * @param id
     * @return
     */
    @DeleteMapping("/delNotice")
    public R<String> delNotice(Long id) {
        boolean b = noticeService.removeById(id);
        if (b) {
            return R.success("删除公告成功!");
        } else {
            return R.error("删除公告失败!");
        }
    }


    /**
     * 公告列表
     *
     * @return
     */
    @GetMapping("/listNotice")
    public R<List<Notice>> listNotice() {
        List<Notice> list = noticeService.list();
        return R.success(list);
    }


    /**
     * 发布公告列表(首页展示用)
     *
     * @return
     */
    @GetMapping("/listNotice2")
    public R<List<Notice>> listNotice2() {
        LambdaQueryWrapper<Notice> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Notice::getSendStatus, 1);
        List<Notice> list = noticeService.list(queryWrapper);
        return R.success(list);
    }

    /**
     * 获取VIP用户分页数据
     *
     * @return
     */
    @GetMapping("/vipList")
    public Page<Vip> vipList(int page, int pageSize) {
        Page<Vip> vipPage = new Page<>(page, pageSize);
        vipService.page(vipPage);
        return vipPage;
    }


    /**
     * 获取充值订单分页数据
     *
     * @return
     */
    @GetMapping("/rechargeList")
    public Page<Recharge> RechargeList(int page, int pageSize) {
        Page<Recharge> rechargePage = new Page<>(page, pageSize);
        rechargeService.page(rechargePage);
        return rechargePage;
    }

    /**
     * 查询时间段内有多少人充值了会员
     *
     * @param timeRang 时间范围
     * @return
     */
    @GetMapping("/timeRangeVip")
    public Map<String, Long> timeRangeBuyVip(int timeRang) {
        return vipService.timeRange(timeRang);
    }


    /**
     * 查询时间段内充值总额
     *
     * @param timeRang 时间范围
     * @return
     */
    @GetMapping("/timeRangeRecharge")
    public Map<String, Date> timeRangeRecharge(int timeRang) {
        return rechargeService.timeRange(timeRang);
    }

}
