package non.app.noon.academy.base;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import non.app.noon.academy.model.SubjectModel;

import static android.content.ContentValues.TAG;

/**
 * Created by bharat on 14/2/18.
 */

public class SubjectHandler {
    private static SubjectHandler subjectHandler;

    private SubjectHandler() {
    }

    public static SubjectHandler getInstance() {
        if (subjectHandler == null) {
            synchronized (SubjectHandler.class) {
                if (subjectHandler == null) {
                    subjectHandler = new SubjectHandler();

                }
            }
        }
        return subjectHandler;
    }

    public void saveSubject(final BaseFragment baseFragment, SubjectModel subjectModel) {
        if (!subjectModel.isValidSubject()) {
            Toast.makeText(baseFragment.getActivity(), "please enter title and description", Toast.LENGTH_SHORT).show();
            return;
        }
        new SaveTable<SubjectModel>(baseFragment.getActivity(), subjectModel, new SaveTableCallBack() {
            @Override
            public void onSuccess() {
                Toast.makeText(baseFragment.getActivity(), "Data saved successfully", Toast.LENGTH_SHORT).show();
       if(baseFragment instanceof AddSubject)
       {
           ((AddSubject) baseFragment).subjectAdded();
       }
            }
        }).execute();
    }

    public void chooseImage(BaseFragment baseFragment) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkPermission(baseFragment.getActivity())) {
                // Code for above or equal 23 API Oriented Device
                // Your Permission granted already .Do next code
            } else {
                requestPermission(baseFragment.getActivity()); // Code for permission
                return;
            }
        }
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        baseFragment.startActivityForResult(Intent.createChooser(intent, "Select Picture"), Constants.PICK_IMAGE_REQUEST);
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
