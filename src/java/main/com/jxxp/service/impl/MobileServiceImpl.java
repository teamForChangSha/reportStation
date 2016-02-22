package com.jxxp.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.jxxp.service.MobileService;

@Service("mobileService")
public class MobileServiceImpl implements MobileService {
	private static final Logger log = LoggerFactory.getLogger(CaseServiceImpl.class);

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
	
	public String sms(String postData,String postUrl) throws IOException {
		URL url = new URL(postUrl);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		conn.setRequestProperty("Connection", "Keep-Alive");
		conn.setUseCaches(false);
		conn.setDoOutput(true);
		
		conn.setRequestProperty("Content-Length", "" + postData.length());
		OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(),"UTF-8");
		out.write(postData);
		out.flush();
		out.close();
		
		if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
			log.debug("connect failed!");
			return "";
		}
		
		String line, result = "";
		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
		while((line = in.readLine()) != null) {
			result += line + "\n";
		}
		in.close();
		return result;
	}

}
