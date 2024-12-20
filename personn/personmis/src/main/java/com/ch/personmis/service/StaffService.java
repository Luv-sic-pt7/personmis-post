package com.ch.personmis.service;

import com.ch.personmis.entity.PeriodByCon;
import com.ch.personmis.entity.Staff;
import com.ch.personmis.entity.StaffByCon;

import java.math.BigDecimal;
import java.util.Map;

public interface StaffService {
    String addStaff(Staff staff );
    Map<String, Object> selectStaffByPage(StaffByCon staffByCon);
    Map<String, Object> selectStaffByCon(StaffByCon staffByCon);
    Staff getStaffDetail(int id);
    String updateStaff(Staff staff );
    Map<String, Object> selectPeriodsByPage(PeriodByCon periodByCon);
    Map<String, Object> selectPeriodByCon(PeriodByCon periodByCon);
    String periodOp(int id, String status);
    String deleteStaff(int id);
    int getStaffCount();
    BigDecimal getAverageTenure();
}
