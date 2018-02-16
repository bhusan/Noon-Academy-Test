package non.app.noon.academy.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import app.noon.academy.R;
import app.noon.academy.databinding.SchoolSubjectItemBinding;
import non.app.noon.academy.fragment.DeleteImageCallBack;
import non.app.noon.academy.model.SubjectModel;

/**
 * Created by bharat on 13/2/18.
 */

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.ViewHolder> {
    List<SubjectModel> subjectModels;
    DeleteImageCallBack deleteImageCallBack;

    public SubjectAdapter(List<SubjectModel> subjectModels, DeleteImageCallBack deleteImageCallBack) {
        this.subjectModels = subjectModels;
        this.deleteImageCallBack = deleteImageCallBack;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder((SchoolSubjectItemBinding) DataBindingUtil.inflate((LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE), R.layout.school_subject_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.subjectItemBinding.setSubjectModel(subjectModels.get(position));
        holder.delete.setTag(position);
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (deleteImageCallBack != null) {
                    Integer position = (Integer) v.getTag();
                    deleteImageCallBack.onDeletePressed(subjectModels.get(position));
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return subjectModels == null ? 0 : subjectModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        SchoolSubjectItemBinding subjectItemBinding;
        ImageView delete;

        public ViewHolder(SchoolSubjectItemBinding subjectItemBinding) {
            super(subjectItemBinding.getRoot());
            this.subjectItemBinding = subjectItemBinding;
            delete = subjectItemBinding.getRoot().findViewById(R.id.ivDelete);
        }
    }

    public List<SubjectModel> getSubjectModels() {
        return subjectModels;
    }

    public void setSubjectModels(List<SubjectModel> subjectModels) {
        this.subjectModels = subjectModels;
    }
}
