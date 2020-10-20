package com.lmac.simpbot.debug;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Debug {

	private static Debug instance;

	private boolean logErrors = true;
	private boolean logMessages = true;

	public static Debug GetInstance() {

		if (instance == null)
			instance = new Debug();

		return instance;
	}

	public void console(String msg) {
		if (!logMessages)
			return;
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
		Date date = new Date(System.currentTimeMillis());
		System.out.println("LOG: [" + formatter.format(date) + "]   " + msg);
	}

	public void error(String msg) {
		if (!logErrors)
			return;
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
		Date date = new Date(System.currentTimeMillis());
		System.out.println("ERROR: [" + formatter.format(date) + "]   " + msg);
	}

	public void setLogErrors(boolean val) {
		this.logErrors = val;
	}

	public void setLogMessages(boolean val) {
		this.logMessages = val;
	}

}
