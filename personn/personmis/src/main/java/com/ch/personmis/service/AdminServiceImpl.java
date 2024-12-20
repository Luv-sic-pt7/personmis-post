package com.ch.personmis.service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import com.ch.personmis.entity.UserEntity;
import com.ch.personmis.repository.AdminRepository;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService{
	@Resource
	private  AdminRepository adminRepository;
	@Override
	public String login(UserEntity userEntity, HttpSession session) {
		UserEntity user = adminRepository.login(userEntity);
		if(user != null) {
			session.setAttribute("auser", user);
			return "ok";
		}else {
			return "no";
		}
	}
}
