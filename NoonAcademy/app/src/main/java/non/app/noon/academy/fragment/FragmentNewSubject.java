package non.app.noon.academy.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.CursorLoader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;

import app.noon.academy.BR;
import app.noon.academy.R;
import non.app.noon.academy.activity.UserInformationCallBack;
import non.app.noon.academy.base.AddSubject;
import non.app.noon.academy.base.BaseFragment;
import non.app.noon.academy.base.Constants;
import non.app.noon.academy.base.SubjectHandler;
import app.noon.academy.databinding.FragmentAddSubjectBinding;
import non.app.noon.academy.model.SubjectModel;

/**
 * Created by bharat on 13/2/18.
 */

public class FragmentNewSubject extends BaseFragment implements AddSubject {
    SubjectModel subjectModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentAddSubjectBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_subject, container, false);
        binding.setSubjectModel(subjectModel = new SubjectModel());
        if (getActivity() instanceof UserInformationCallBack) {
            subjectModel.setEmail(((UserInformationCallBack) getActivity()).getEmail());

        }
        binding.setBaseFragment(this);
        binding.setSubjectHandler(SubjectHandler.getInstance());
        return binding.getRoot();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.PICK_IMAGE_REQUEST & resultCode == Activity.RESULT_OK) {
            if (data != null) {
                subjectModel.setImageUrl(getRealPathFromURI(getActivity(), data.getData()));
            }
        }
    }


    public String getRealPathFromURI(Context context, Uri contentUri) {
        String filePath = "";
        String wholeID = DocumentsContract.getDocumentId(contentUri);

        // Split at colon, use second item in the array
        String id = wholeID.split(":")[1];

        String[] column = {MediaStore.Images.Media.DATA};

        // where id is equal to
        String sel = MediaStore.Images.Media._ID + "=?";

        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                column, sel, new String[]{id}, null);

        int columnIndex = cursor.getColumnIndex(column[0]);

        if (cursor.moveToFirst()) {
            filePath = cursor.getString(columnIndex);
        }
        cursor.close();
        return filePath;
    }

    @Override
    public void subjectAdded() {
        subjectModel.setImageUrl(null);
        subjectModel.setDescription(null);
        subjectModel.setId(0);
        subjectModel.setTitle(null);
        subjectModel.notifyPropertyChanged(BR._all);
    }
}
