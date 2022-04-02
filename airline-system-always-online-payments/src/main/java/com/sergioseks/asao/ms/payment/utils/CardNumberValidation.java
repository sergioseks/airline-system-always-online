package com.sergioseks.asao.ms.payment.utils;
/**
 * @author Sergio.Espinal@ibm.com
 * @description This class contains algorithms to validate the card number
 * @name CardNumberValidation
 * @date 23-03-2020
 */
public class CardNumberValidation {

	/**
	 * @author sergioseks
	 * @description This function validate the card number using Luhn algorithm
	 * @name luhnAlgorithm
	 * @date 22-06-2020
	 * @param Full card number as String
	 * @return true if the card number is valid
	 */
	public boolean luhnAlgorithm(String number) {

		String reverseNumber = new StringBuilder(number).reverse().toString();

		int totalSum = 0;

		for (int i = 0; i < reverseNumber.length(); i++) {

			if ((i + 1) % 2 == 0) {

				int intNumber = Integer.parseInt("" + reverseNumber.charAt(i)) * 2;

				if (intNumber > 9) {

					String stringNumber = Integer.toString(intNumber);

					totalSum += (Integer.parseInt("" + stringNumber.charAt(0))
							+ Integer.parseInt("" + stringNumber.charAt(1)));

				} else
					totalSum += intNumber;
			} else
				totalSum += Integer.parseInt("" + reverseNumber.charAt(i));
		}

		return (totalSum % 10 == 0);
	}
}