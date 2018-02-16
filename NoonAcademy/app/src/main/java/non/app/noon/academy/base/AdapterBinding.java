package non.app.noon.academy.base;

import android.databinding.BindingAdapter;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;

/**
 * Created by bharat on 13/2/18.
 */

public class AdapterBinding {
    @BindingAdapter({"imageUrl"})
    public static void loadImageUrl(final ImageView imageView, final String link) {
        if (TextUtils.isEmpty(link)) {
            imageView.setImageResource(android.R.drawable.ic_menu_camera);
            return;
        }
        File file = new File(link);
        Picasso.with(imageView.getContext()).setLoggingEnabled(true);
        Picasso.with(imageView.getContext()).load(file).into(imageView, new Callback() {
            @Override
            public void onSuccess() {
                Log.d("image", "Success");
            }

            @Override
            public void onError() {
                Log.d("image", "error");
            }
        });
    }
}
