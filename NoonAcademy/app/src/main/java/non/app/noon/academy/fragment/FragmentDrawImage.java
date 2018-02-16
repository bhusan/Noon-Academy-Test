package non.app.noon.academy.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;

import app.noon.academy.R;
import non.app.noon.academy.base.BaseFragment;
import non.app.noon.academy.base.DrawableView;

/**
 * Created by bharat on 13/2/18.
 */

public class FragmentDrawImage extends BaseFragment {
    DrawableView drawableView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.draw_image, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        drawableView = view.findViewById(R.id.drawView);
        view.findViewById(R.id.btSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawableView.save();
            }
        });
        view.findViewById(R.id.btUndo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawableView.undo();
            }
        });
        view.findViewById(R.id.btRedo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawableView.redo();
            }
        });
        view.findViewById(R.id.btColor).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorPicker();
            }
        });
    }

    private void colorPicker() {
        ColorPickerDialogBuilder
                .with(getActivity())
                .setTitle("Choose color")
                .initialColor(getResources().getColor(R.color.colorAccent))
                .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                .density(12)
                .setOnColorSelectedListener(new OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(int selectedColor) {
//                        toast("onColorSelected: 0x" + Integer.toHexString(selectedColor));
                    }
                })
                .setPositiveButton("ok", new ColorPickerClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                        drawableView.setColor(selectedColor);
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .build()
                .show();
    }
}
