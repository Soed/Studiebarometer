package nl.soco.imtpmd.studiebarometer;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Soed on 15-04-16.
 */
public class LoginRequest extends StringRequest{
    private static final String LOGIN_REQUEST_URL = "http://192.168.1.74/imtpmd/Login.php";
    private Map<String, String> params;

    public LoginRequest(String email, String password, Response.Listener<String> listener){
        super(Request.Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("user_email", email);
        params.put("user_password", password);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}

