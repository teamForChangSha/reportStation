package com.jxxp.service.impl;

import org.springframework.stereotype.Service;

import com.jxxp.comms.util.SmsUtil;
import com.jxxp.service.MobileService;

@Service("mobileService")
public class MobileServiceImpl implements MobileService {

	@Override
	public String sendVerifySMS(String mobile) {
		String verifyCode = SmsUtil.getRandomCode();
		SmsUtil.sendSms("本次短信验证码为：" + verifyCode, mobile);
		return verifyCode;
	}

	@Override
	public String sendTempPwd(String mobile) {
		String tempPwd = SmsUtil.getRandomCode();
		SmsUtil.sendSms("本次登录的临时密码为：" + tempPwd, mobile);
		return tempPwd;
	}

}
