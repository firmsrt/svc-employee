package th.co.itv.svcemployee.model.Jwt;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class JwtResponse implements Serializable {
    private static final long serialVersionUID = 5169039279169103144L;
    private final String jwtToken;
}
