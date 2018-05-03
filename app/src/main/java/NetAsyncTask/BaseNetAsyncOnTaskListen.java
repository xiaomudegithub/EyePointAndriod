package NetAsyncTask;

import android.content.Context;

public interface BaseNetAsyncOnTaskListen {
    void onSuccess(Context context, Object result);
    void onFailure(Context context, Object msg);
}
