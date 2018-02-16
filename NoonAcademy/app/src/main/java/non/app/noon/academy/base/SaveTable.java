package non.app.noon.academy.base;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

/**
 * Created by bharatbhusan on 14/2/18.
 */

public class SaveTable<T> extends AsyncTask {
    private T saveData;
    private Context context;
    private SaveTableCallBack callBack;

    public SaveTable(Context context, T saveData, SaveTableCallBack callBack) {
        this.saveData = saveData;
        this.context = context;
        this.callBack = callBack;
    }

    @Override
    protected Object doInBackground(Object[] params) {
            DBUtil.getDBUtil(context, saveData.getClass()).add(saveData);
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        if (callBack != null) {
            callBack.onSuccess();
        }
    }

    public void execute() {
        executeOnExecutor(THREAD_POOL_EXECUTOR, null);
    }
}
