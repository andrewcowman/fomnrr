package com.cowman.requests;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class MembershipRequest extends JsonRequest<String> {

    private Response.Listener<String> _listener;

    public MembershipRequest(String url, JSONObject object,
                             Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Method.POST, url, object.toString(), listener, errorListener);
        this._listener = listener;
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            return Response.success(json,
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException ex) {
            return Response.error(new ParseError(ex));
        }
    }

    @Override
    protected void deliverResponse(String response) {
        _listener.onResponse(response);
    }
}

