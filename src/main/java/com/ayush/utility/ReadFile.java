package com.ayush.utility;

import java.io.BufferedReader;
import java.io.FileReader;

public class ReadFile {

	public static String readMailBody(String fullName, String pwd, String fileName, String url) {
		String mailBody = null;
		try {
			FileReader fr = new FileReader(fileName);
			BufferedReader br = new BufferedReader(fr);

			StringBuffer buffer = new StringBuffer();

			String line = br.readLine();
			while (line != null) {
				buffer.append(line);
				line = br.readLine();
			}
			br.close();
			mailBody = buffer.toString();
			mailBody = mailBody.replace("{FULLNAME}", fullName);
			mailBody = mailBody.replace("{TEMPPWD}", pwd);
			mailBody = mailBody.replace("{PWD}", pwd);
			mailBody = mailBody.replace("{URL}", url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mailBody;
	}

}
