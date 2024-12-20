package com.ch.personmis.controller;

import com.ch.personmis.entity.PeriodByCon;
import com.ch.personmis.entity.Staff;
import com.ch.personmis.entity.StaffByCon;
import com.ch.personmis.service.StaffService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Map;

@RestController
public class StaffController {
    @Resource
    private StaffService staffService;
    @PostMapping("/addStaff")
    public  String addStaff(@RequestBody Staff staff ){
        return staffService.addStaff(staff);
    }
    @GetMapping("/getStaffByPage")
    public Map<String, Object> selectStaffByPage(StaffByCon staffByCon) {
        return staffService.selectStaffByPage(staffByCon);
    }
    @PostMapping("/selectStaffByCon")
    public Map<String, Object> selectStaffByCon(@RequestBody StaffByCon staffByCon){
        return staffService.selectStaffByCon(staffByCon);
    }
    @GetMapping("/getStaffDetail")
    public Staff getStaffDetail(int id){
        return staffService.getStaffDetail(id);
    }
    @PostMapping("/updateStaff")
    public  String updateStaff(@RequestBody Staff staff ){
        return staffService.updateStaff(staff);
    }
    //试用期管理
    @GetMapping("/getPeriods")
    public Map<String, Object> selectPeriodsByPage(PeriodByCon periodByCon) {
        return staffService.selectPeriodsByPage(periodByCon);
    }
    @PostMapping("/selectPeriodByCon")
    public Map<String, Object> selectPeriodByCon(@RequestBody PeriodByCon periodByCon){
        return staffService.selectPeriodByCon(periodByCon);
    }
    @PostMapping("/periodOp")
    public String periodOp(int id, String status){
        return staffService.periodOp(id, status);
    }
    @PostMapping("/deleteStaff")
    public  String deleteStaff(int id){
        return staffService.deleteStaff(id);
    }
    @GetMapping("/getStaffCount")
    public int getStaffCount(){
        return staffService.getStaffCount();
    }
    @GetMapping("/getAverageTenure")
    public BigDecimal getAverageTenure() {
        return staffService.getAverageTenure();
    }
}
