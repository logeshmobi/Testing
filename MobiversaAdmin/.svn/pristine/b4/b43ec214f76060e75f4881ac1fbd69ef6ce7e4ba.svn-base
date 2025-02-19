// The MIT License
//
// Copyright (c) 2010 Jared Holdcroft
//
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
//
// The above copyright notice and this permission notice shall be included in
// all copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
// THE SOFTWARE.

package com.postmark.java;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Postmark for Java
 * <p/>
 * This library can be used to leverage the postmarkapp.com functionality from a
 * Java client
 * <p/>
 * http://github.com/jaredholdcroft/postmark-java
 */

public class TestClient {

	public static void main(String[] args) throws IOException {
		List<NameValuePair> headers = new ArrayList<NameValuePair>();

		headers.add(new NameValuePair("HEADER", "test"));

		String fromAddress = "Shrevene@mobiversa.com";
		String apiKey = "0a55a4fc-e2a0-4d76-ba16-c67fb3bc7eca";

		String toAddress = "k.s.shrevene@gmail.com";
		String subject = "test subject";
		TempletFields tm = new TempletFields();
		/*tm.setTID("XXXXXX84");
		tm.setMID("0000001803002227");
		tm.setApp("MASTERCARD");
		tm.setAID("A0000000041010");
		tm.setApprCode("R83115");
		tm.setBatchNo("000146");
		tm.setCardNo("**** **** **** 4016");
		tm.setDate("FEB 11, 2015");
		tm.setHolder("MARLINDER");
		tm.setRefNo("504207000664");
		tm.setTC("6E4F1620CEAA2B72");
		tm.setTime("15:03:53");
		tm.setTraceNo("000368");
		tm.setTotal("RM 2576.00");*/

		String emailBody = EmailTemplet1.sentTempletContent(tm);
		// String emailBody = "this is the sample email body";
		PostmarkMessage message = new PostmarkMessage(fromAddress, toAddress,
				fromAddress, null, subject, emailBody, true, "test-email");

		PostmarkClient client = new PostmarkClient(apiKey);
		EmailTemplet templt = new EmailTemplet();

		try {
			client.sendMessage(message);
		} catch (PostmarkException pe) {
			System.out.println("An error has occurred : " + pe.getMessage());
		}
	}
}
