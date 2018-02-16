package non.app.noon.academy.base;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

/**
 * Created by bharatbhusan on 14/2/18.
 */

public class GetTable<T> extends AsyncTask {

    private Context context;
    private Pair pair;
    private TableCallBack callBack;
    Class className;

    public GetTable(Context context, Pair pair, TableCallBack callBack, Class className) {
        this.context = context;
        this.pair = pair;
        this.callBack = callBack;
        this.className = className;
    }

    @Override
    protected Object doInBackground(Object[] params) {
        return DBUtil.getDBUtil(context, className).fetch(pair, null);
    }


    @Override
    protected void onPostExecute(Object o) {
        if (callBack != null) {
            callBack.onSuccess((List<T>) o);
        }
    }

    public void execute() {
        executeOnExecutor(THREAD_POOL_EXECUTOR, null);
    }
}