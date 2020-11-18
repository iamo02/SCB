package com.challenge.challengeSCB.service.impl;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.springframework.stereotype.Service;

import com.challenge.challengeSCB.bean.request.Request;
import com.challenge.challengeSCB.bean.response.ReturnObj;
import com.challenge.challengeSCB.exception.ValidationException;
import com.challenge.challengeSCB.service.ChallengeService;

@Service
public class ChallengeServiceImpl implements ChallengeService {

	@Override
	public ReturnObj work(Request request) {

		ReturnObj obj = new ReturnObj();
		String stringDecode = decode(request.getFunnyStr());

		if (checkNumber(stringDecode) || (!stringDecode.equals(stringDecode.toUpperCase()))) {
			throw new ValidationException("Please enter english capital letters only.");
		} else
		{
			String stringLowerCase = stringDecode.toLowerCase();
			String stringAddNumber = addNumber(stringLowerCase);
			String stringSwap = swap(stringAddNumber);
			String stringEncode = encode(stringSwap);
			obj.setEncodedString(stringEncode);

		} 
		return obj;
	}

	public String decode(String originalInput) {

		Base64.Decoder decoder = Base64.getDecoder();
		byte[] decodedByteArray = decoder.decode(originalInput);
		return new String(decodedByteArray);

	}

	public String encode(String stringAddNumber) {
//		stringAddNumber = "A HEN  HAS  MANY   CHICKS";
		Base64.Encoder encoder = Base64.getEncoder();
		String encodedString = encoder.encodeToString(stringAddNumber.getBytes(StandardCharsets.UTF_8));

		return encodedString;

	}

	public String addNumber(String stringLowerCase) {
		int count = 0;
		String stringAddNumber = "";

		for (int i = 0; i < stringLowerCase.length(); i++) {

			if (stringLowerCase.charAt(i) == ' ') {
				count++;
			} else {
				if (count == 0) {
					stringAddNumber = stringAddNumber + stringLowerCase.charAt(i);
				} else {
					stringAddNumber = stringAddNumber + count + stringLowerCase.charAt(i);
					count = 0;
				}
			}
		}
		return stringAddNumber;
	}

	public String swap(String stringLowerCase) {
		String stringSwap = "";
		int count = stringLowerCase.length();

		for (int i = (count - 1); i >= 0; i--) {
			stringSwap += stringLowerCase.charAt(i);
		}
		return stringSwap;
	}

	public boolean checkNumber(String str) {
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) >= '0' && str.charAt(i) <= '9') {
				return true;
			}
		}
		return false;
	}

}

//VEhFIFdPUkxE = THE WORLD
