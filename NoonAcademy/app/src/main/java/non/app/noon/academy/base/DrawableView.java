package non.app.noon.academy.base;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.net.Uri;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by bharat on 14/2/18.
 */

public class DrawableView extends View implements View.OnTouchListener {
    private Canvas mCanvas;
    private Path mPath;
    private Paint mPaint;
    private ArrayList<PathColor> paths = new ArrayList<PathColor>();
    private ArrayList<PathColor> undoPaths = new ArrayList<PathColor>();

    private int currentColor;

    public DrawableView(Context context) {
        super(context);
        init();
    }

    public DrawableView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DrawableView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        setFocusable(true);
        setFocusableInTouchMode(true);
        this.setOnTouchListener(this);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(0xFF000000);
        currentColor = mPaint.getColor();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(5);
        mCanvas = new Canvas();
        mPath = new Path();
        paths.add(new PathColor(mPath, mPaint.getColor()));
    }

    public void setColor(int color) {
        currentColor = color;
        paths.get(paths.size() - 1).setColor(color);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (PathColor p : paths) {
            mPaint.setColor(p.getColor());
            canvas.drawPath(p.getPath(), mPaint);
        }
    }

    private float mX, mY;
    private static final float TOUCH_TOLERANCE = 4;

    private void touch_start(float x, float y) {
        mPath.reset();
        mPath.moveTo(x, y);
        mX = x;
        mY = y;
    }

    private void touch_move(float x, float y) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
            mX = x;
            mY = y;
        }
    }

    private void touch_up() {
        mPath.lineTo(mX, mY);
        mCanvas.drawPath(mPath, mPaint);
        mPath = new Path();
        paths.add(new PathColor(mPath, currentColor));
        undoPaths.clear();
    }

    public void undo() {
        if (paths.size() > 1) {
            undoPaths.add(paths.get(paths.size() - 2));
            paths.remove(paths.size() - 2);
            invalidate();
        }
    }

    public void redo() {
        if (undoPaths.size() > 0) {
            paths.add(paths.size() - 1, undoPaths.get(undoPaths.size() - 1));
            undoPaths.remove(undoPaths.size() - 1);
            invalidate();
        }
    }

    @Override
    public boolean onTouch(View arg0, MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touch_start(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                touch_move(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                touch_up();
                invalidate();
                break;
        }
        return true;
    }


    public void save() {
        if (getContext() instanceof Activity) {
            if (!checkPermission((Activity) getContext())) {
                requestPermission((Activity) getContext());
                return;
            }
        } else {
            return;
        }

        Bitmap mBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(mBitmap);
        draw(canvas);
        FileOutputStream out = null;
        try {
            File folder = new File(Environment.getExternalStorageDirectory() +
                    File.separator + "noon");
            if (!folder.exists()) {
                folder.mkdirs();
            }
            File f = new File(Environment.getExternalStorageDirectory()
                    + File.separator + "noon" + File.separator + "noon_" + System.currentTimeMillis() + ".png");
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                out = new FileOutputStream(f);
                mBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        undoPaths.clear();
        paths.clear();
        invalidate();
        mPath = new Path();
        paths.add(new PathColor(mPath, mPaint.getColor()));
    }

    private boolean checkPermission(Activity activity) {
        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private void requestPermission(Activity activity) {

        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(activity, "Write External Storage permission allows us to do store images. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
            intent.setData(uri);
            activity.startActivityForResult(intent, 5);
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }
    }
}
