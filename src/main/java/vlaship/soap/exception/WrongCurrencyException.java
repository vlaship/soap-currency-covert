package vlaship.soap.exception;

import org.springframework.ws.soap.server.endpoint.annotation.FaultCode;
import org.springframework.ws.soap.server.endpoint.annotation.SoapFault;

@SoapFault(faultCode = FaultCode.CLIENT)
public class WrongCurrencyException extends RuntimeException {
    public WrongCurrencyException() {
        super("Wrong currency");
    }
}
