package cn.charlin.gcgs.ui.slideshow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import cn.charlin.gcgs.R;

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        final TextView textView = root.findViewById(R.id.text_slideshow);
        slideshowViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText("这个区域日后再来探索吧！");
            }
        });
        final ImageView img=root.findViewById(R.id.imageView4);
        Random r = new Random();
        int random = r.nextInt(12);
        switch (random){
            case 0:
                img.setImageResource(R.drawable.icon_01);
                break;
            case 2:
                img.setImageResource(R.drawable.icon_02);
                break;
            case 3:
                img.setImageResource(R.drawable.icon_03);
                break;
            case 4:
                img.setImageResource(R.drawable.icon_04);
                break;
            case 5:
                img.setImageResource(R.drawable.icon_05);
                break;
            case 6:
                img.setImageResource(R.drawable.icon_06);
                break;
            case 7:
                img.setImageResource(R.drawable.icon_07);
                break;
            case 8:
                img.setImageResource(R.drawable.icon_08);
                break;
            case 9:
                img.setImageResource(R.drawable.icon_09);
                break;
            case 10:
                img.setImageResource(R.drawable.icon_10);
                break;
            case 11:
                img.setImageResource(R.drawable.icon_11);
                break;
        }

        return root;
    }
}