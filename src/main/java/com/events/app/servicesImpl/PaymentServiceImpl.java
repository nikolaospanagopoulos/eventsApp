package com.events.app.servicesImpl;

import java.util.HashMap;
import java.util.Map;

import com.events.app.services.PaymentService;
import com.stripe.exception.APIConnectionException;
import com.stripe.exception.APIException;
import com.stripe.exception.AuthenticationException;
import com.stripe.exception.CardException;
import com.stripe.exception.InvalidRequestException;
import com.stripe.model.Charge;

public class PaymentServiceImpl implements PaymentService{

	@Override
	public Charge chargeNewCard(String token, double amount) throws AuthenticationException, InvalidRequestException, APIConnectionException, CardException, APIException {
		Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("amount", (int)(amount * 100)); // Amount in cents
        chargeParams.put("currency", "USD");
        chargeParams.put("source", token); // Obtain the Stripe token from the front-end
        chargeParams.put("description", "Charge for a ticket");

        return Charge.create(chargeParams);
	}

}
