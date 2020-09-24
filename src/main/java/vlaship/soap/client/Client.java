package vlaship.soap.client;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.stereotype.Component;
import org.tempuri.GetConversionRate;
import org.tempuri.GetConversionRateResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.client.core.SoapActionCallback;

@Component
public class Client {

    @Value("${ws.path}")
    private String path;

    private final WebServiceTemplate webServiceTemplate;

    public BigDecimal getRate(final String from, final String to) {
        final var request = new GetConversionRate();
        request.setCurrencyFrom(from);
        request.setCurrencyTo(to);
        request.setRateDate(LocalDate.now().toString());
        final var response = (GetConversionRateResponse) webServiceTemplate.marshalSendAndReceive(path, request, new SoapActionCallback("ACTION.CALLBACK"));

        return response.getGetConversionRateResult();
    }

    @Autowired
    public Client(final WebServiceTemplate webServiceTemplate) {
        this.webServiceTemplate = webServiceTemplate;
    }
}
