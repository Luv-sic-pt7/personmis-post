package com.ch.personmis.service;

import com.ch.personmis.entity.Depart;
import com.ch.personmis.entity.DepartByCon;
import com.ch.personmis.repository.DepartRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DepartServiceImpl implements DepartService {
    @Resource
    private DepartRepository departRepository;
    @Override
    public List<Depart> selectDepart() {
        return departRepository.selectDepart(null);
    }
    @Override
    public String addDepartment(Depart depart) {
        if(departRepository.addDepart(depart) > 0)
            return "ok";
        return "no";
    }

    @Override
    public Map<String, Object> selectDepartByPage(DepartByCon departByCon) {
        Map<String, Object> map = new HashMap<String, Object>();
        departByCon.setAct("byPage");
        List<Depart> departs = departRepository.selectDepart(departByCon);
        map.put("departs", departs);
        departByCon.setAct("byNoPage");
        map.put("total", departRepository.selectDepart(departByCon).size());
        return map;
    }

    @Override
    public Map<String, Object> selectDepartmentsByCon(DepartByCon departByCon) {
        Map<String, Object> map = new HashMap<String, Object>();
        departByCon.setAct("byPage");
        List<Depart> departs = departRepository.selectDepartmentsByCon(departByCon);
        map.put("departs", departs);
        departByCon.setAct("byNoPage");
        map.put("total", departRepository.selectDepartmentsByCon(departByCon).size());
        return map;
    }

    @Override
    public Map<String, Object> getDepartmentDetail(int id) {
        Map<String, Object> map = new HashMap<String, Object>();
        List<Depart> departs = departRepository.selectDepart(null);
        map.put("departs", departs);
        Depart aDepart = departRepository.getDepartmentDetail(id);
        map.put("aDepart", aDepart);
        return map;
    }

    @Override
    public String updateDepartment(Depart depart) {
        if(departRepository.updateDepartment(depart) > 0)
            return "ok";
        return "no";
    }

    @Override
    public String deleteDepartment(int id) {
        //先查询是否有关联数据
        List<Map<String, Object>>  listMap =departRepository.selectAssociateDepart(id);
        if (listMap.size() <= 0) {
            if (departRepository.deleteDepart(id) > 0)
                return "ok";
        }
        return "no";
    }
}
