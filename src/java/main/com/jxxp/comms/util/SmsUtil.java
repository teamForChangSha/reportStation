package com.jxxp.comms.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Random;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SmsUtil {
	private static final Logger logger = LoggerFactory.getLogger(SmsUtil.class);
	private static final String send_url = "http://125.208.1.165/appserver/sms/smsmt/send.php";
	private static final String pid = "124155";
	private static final String number = "54097";
	private static final String password = "csww123";

	/**
	 * @Title: sendSms
	 * @Description: 发送短信
	 * @param content
	 *            内容
	 * @param phone_num
	 *            号码 void
	 * @exception
	 */
	public static void sendSms(String content, String phone_num) {
		try {
			String msg = java.net.URLEncoder.encode(content + "【车随我往】", "gbk");
			getMethod(send_url + "?pid=" + pid + "&number=" + number + "&mobile=" + phone_num + "&message=" + msg + "&password=" + password);
			logger.debug("发送短信验证码,  " + content);
		} catch (UnsupportedEncodingException e) {
			logger.error("发送短信验证码异常！", e);
		}
	}

	public static void getMethod(String url) {
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpGet httpget = new HttpGet(url);
			logger.debug("executing request " + httpget.getURI());
			httpget.setHeader("ACCEPT", "application/json");
			httpget.setHeader("Content-Type", "application/json;charset=UTF-8");
			HttpResponse response = httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();
			logger.debug("----------------------------------------");
			logger.debug("" + response.getStatusLine());
			if (entity != null) {
				logger.debug("Response content length: " + entity.getContentLength());
				InputStream is = entity.getContent();
				InputStreamReader isr = new InputStreamReader(is, "UTF-8");
				BufferedReader br = new BufferedReader(isr);
				String str;
				StringBuffer sb = new StringBuffer();
				while ((str = br.readLine()) != null) {
					sb.append(str);
				}
				logger.debug(sb.toString());
				is.close();
				isr.close();
				br.close();
			}
			logger.debug("----------------------------------------");
			httpget.abort();
			httpclient.getConnectionManager().shutdown();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getRandomCode() {
		return "" + (new Random().nextInt(899999) + 100000);
	}
	
	public static void main(String[] args) {
		new SmsUtil().sendSms("本次短信验证码为：" + getRandomCode(), "15364060309");

	}
}
