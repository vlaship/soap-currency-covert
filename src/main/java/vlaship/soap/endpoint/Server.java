package vlaship.soap.endpoint;

import vlaship.soap.client.Client;
import vlaship.soap.exception.WrongCurrencyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import org.tempuri.GetLocalRequest;
import org.tempuri.GetLocalResponse;

@Endpoint
public class Server {
    private static final String NAMESPACE_URI = "http://tempuri.org/";

    private final Client client;

    @Value("${ws.currencies}")
    private String currencies;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getLocalRequest")
    @ResponsePayload
    public GetLocalResponse getCurrency(@RequestPayload final GetLocalRequest request) throws Exception {
        if (!currencies.contains(request.getCurrencyFrom().toLowerCase())
                || !currencies.contains(request.getCurrencyTo().toLowerCase())) {
            throw new WrongCurrencyException();
        }

        final var rate = client.getRate(request.getCurrencyFrom(), request.getCurrencyTo());

        final var response = new GetLocalResponse();
        response.setCurrency(request.getCurrencyTo().toUpperCase());
        response.setAmount(request.getAmount().multiply(rate));
        return response;
    }

    @Autowired
    public Server(final Client client) {
        this.client = client;
    }
}
