package non.app.noon.academy.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.text.TextUtils;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import app.noon.academy.BR;

/**
 * Created by bharat on 13/2/18.
 */
@DatabaseTable
public class SubjectModel extends BaseObservable {
    @DatabaseField(generatedId = true, allowGeneratedIdInsert = true)
    int id;

    @DatabaseField
    private String email;

    @DatabaseField(canBeNull = false)
    private String title;

    @DatabaseField(canBeNull = false)
    private String description;

    @DatabaseField
    private String imageUrl;

    public String getTitle() {
        return title;
    }

    public String getEmail() {
        return email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Bindable
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        notifyPropertyChanged(BR.imageUrl);
    }

    public boolean isValidSubject() {
        return !(TextUtils.isEmpty(title) || TextUtils.isEmpty(description));
    }
}
