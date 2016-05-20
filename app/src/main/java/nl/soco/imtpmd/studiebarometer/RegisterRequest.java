package nl.soco.imtpmd.studiebarometer;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Soed on 14-04-16.
 */
public class RegisterRequest extends StringRequest{

    private static final String REGISTER_REQUEST_URL = "http://collinwoerde.nl/imtpmd/Register.php";
    private Map<String, String> params;

    public RegisterRequest(String name, String password, String email, Response.Listener<String> listener){
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("user_name", name);
        params.put("user_password", password);
        params.put("user_email", email);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
