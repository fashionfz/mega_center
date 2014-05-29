package com.dubbo;

import com.dubbo.MessageService;

public class MessageServiceImpl implements MessageService{

	@Override
	public void send(String arg0, String arg1) {
		System.out.println("发送短信");
	}

}
