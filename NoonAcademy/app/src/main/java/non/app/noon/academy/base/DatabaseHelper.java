package non.app.noon.academy.base;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import app.noon.academy.R;
import non.app.noon.academy.model.SubjectModel;

/**
 * Created by bharat on 13/2/18.
 */

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    public DatabaseHelper(Context context) {
        super(context, "NOON", null, context.getResources().getInteger(R.integer.DATABASE_VERSION));

    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTableIfNotExists(connectionSource, SubjectModel.class);
        } catch (SQLException e) {
            e.printStackTrace();
            Log.i(SubjectModel.class.getName(), "not created");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }


    public Dao getDaoFromClass(Class cl) {
            try {
                return getDao(cl);
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
    }
}
