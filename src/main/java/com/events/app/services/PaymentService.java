package com.events.app.services;

import com.stripe.exception.APIConnectionException;
import com.stripe.exception.APIException;
import com.stripe.exception.AuthenticationException;
import com.stripe.exception.CardException;
import com.stripe.exception.InvalidRequestException;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;

public interface PaymentService {
	public Charge chargeNewCard(String token, double amount) throws AuthenticationException, InvalidRequestException, APIConnectionException, CardException, APIException;
}
