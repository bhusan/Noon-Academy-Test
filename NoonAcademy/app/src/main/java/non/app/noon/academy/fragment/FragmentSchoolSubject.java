package non.app.noon.academy.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import app.noon.academy.R;
import non.app.noon.academy.activity.UserInformationCallBack;
import non.app.noon.academy.adapter.SubjectAdapter;
import non.app.noon.academy.base.BaseFragment;
import non.app.noon.academy.base.DBUtil;
import non.app.noon.academy.base.GetTable;
import non.app.noon.academy.base.Pair;
import non.app.noon.academy.base.TableCallBack;
import non.app.noon.academy.model.SubjectModel;

/**
 * Created by bharat on 13/2/18.
 */

public class FragmentSchoolSubject extends BaseFragment implements DeleteImageCallBack {
    SubjectAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_school_subject, null);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final RecyclerView recyclerView = view.findViewById(R.id.rvSchoolSubject);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        String email = null;
        if (getContext() instanceof UserInformationCallBack) {
            email = ((UserInformationCallBack) getContext()).getEmail();
        }
        new GetTable<SubjectModel>(getActivity(), new Pair<String, String>("email", email), new TableCallBack<SubjectModel>() {
            @Override
            public void onSuccess(List<SubjectModel> list) {
                adapter = new SubjectAdapter(list, FragmentSchoolSubject.this);
                recyclerView.setAdapter(adapter);
            }
        }, SubjectModel.class).execute();

    }

    @Override
    public void onDeletePressed(SubjectModel subjectModel) {
        DBUtil.getDBUtil(getActivity(), SubjectModel.class).remove(subjectModel);
        adapter.getSubjectModels().remove(subjectModel);
        adapter.notifyDataSetChanged();

    }
}
