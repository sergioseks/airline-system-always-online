package com.sergioseks.asao.ms.payment.services.implement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sergioseks.asao.ms.payment.dto.PaymentIntentDTO;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;

@Service
public class PaymentIntentService {

	@Value("${stripe.keys.secret}")
	private String secretKey;

	public PaymentIntent paymentIntent(PaymentIntentDTO paymentIntentDTO) {

		Stripe.apiKey = secretKey;

		Map<String, Object> params = new HashMap<>();

		params.put("amount", paymentIntentDTO.getAmount());
		params.put("currency", paymentIntentDTO.getCurrency());
		params.put("description", paymentIntentDTO.getDescription());

		ArrayList<String> paymentMethodTypes = new ArrayList<>();

		paymentMethodTypes.add("card");

		params.put("payment_method_types", paymentMethodTypes);

		try {

			return PaymentIntent.create(params);

		} catch (StripeException e) {

			e.printStackTrace();

			return null;
		}
	}

	public PaymentIntent confirm(String id) {

		Stripe.apiKey = secretKey;

		PaymentIntent paymentIntent = null;

		try {

			paymentIntent = PaymentIntent.retrieve(id);

		} catch (StripeException e) {

			e.printStackTrace();

		}

		Map<String, Object> params = new HashMap<>();

		params.put("payment_method", "pm_card_visa");

		try {

			return paymentIntent.confirm(params);

		} catch (StripeException e) {

			e.printStackTrace();

		}

		return null;
	}

	public PaymentIntent cancel(String id) {

		Stripe.apiKey = secretKey;

		PaymentIntent paymentIntent = null;

		try {

			paymentIntent = PaymentIntent.retrieve(id);

		} catch (StripeException e) {

			e.printStackTrace();

		}

		try {

			return paymentIntent.cancel();

		} catch (StripeException e) {

			e.printStackTrace();

		}

		return null;
	}
}
