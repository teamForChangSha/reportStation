package com.jxxp.service.impl;

import org.springframework.stereotype.Service;

import com.jxxp.service.MobileService;

@Service("mobileService")
public class MobileServiceImpl implements MobileService {

	@Override
	public String sendVerifySMS(String mobile) {
		// TODO Auto-generated method stub
		return "123";
	}

	@Override
	public String sendTempPwd(String mobile) {
		// TODO Auto-generated method stub
		return "123";
	}

}
