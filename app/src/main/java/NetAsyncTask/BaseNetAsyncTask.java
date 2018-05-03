package NetAsyncTask;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class BaseNetAsyncTask extends AsyncTask<Integer,Integer,Object> {

    public enum NetType {
        POST, GET
    }
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private HashMap<String, Object> params;
    private BaseNetAsyncOnTaskListen listen;
    private Context context;
    private NetType type;
    private String url;

    public BaseNetAsyncTask(Context context, NetType type, String url, HashMap<String, Object> params, BaseNetAsyncOnTaskListen listen){
        this.context = context;
        this.type = type;
        this.listen = listen;
        this.params = params;
        this.url = url;
    }

    @Override
    protected Object doInBackground(Integer... integers) {
        switch (type){
            case GET:
                return get(url, params);
            case POST:
                return post(url, params);
        }
        return post(url, params);
    }


    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        ResponseObject result = (ResponseObject) o;
        if (result.isSuccess()){
            this.listen.onSuccess(context, result.getContent());
        }else {
            this.listen.onFailure(context, result.getMsg());
        }
    }

    private  ResponseObject post(String url, HashMap<String, Object>params){
        OkHttpClient client = new OkHttpClient();
        JSONObject paramObject = new JSONObject(params);
        RequestBody body = RequestBody.create(JSON, paramObject.toString());
        Request request = new Request.Builder().url(url).post(body).build();
        try {
            Response response = client.newCall(request).execute();
            ResponseObject result = com.alibaba.fastjson.JSON.parseObject(response.body().string(), ResponseObject.class);
            return result;
        } catch (IOException e) {
            ResponseObject errorObject = new ResponseObject();
            errorObject.setSuccess(false);
            errorObject.setErrorCode(-100);
            errorObject.setMsg(e.toString());
            return errorObject;
        }
    }

    private ResponseObject get(String url, HashMap<String, Object>params){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        try {
            Response response = client.newCall(request).execute();
            ResponseObject result = com.alibaba.fastjson.JSON.parseObject(response.body().string(), ResponseObject.class);
            return result;
        } catch (IOException e) {
            ResponseObject errorObject = new ResponseObject();
            errorObject.setSuccess(false);
            errorObject.setErrorCode(-100);
            errorObject.setMsg(e.toString());
            return errorObject;
        }
    }

}
