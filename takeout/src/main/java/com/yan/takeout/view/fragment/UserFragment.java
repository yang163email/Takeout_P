package com.yan.takeout.view.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yan.takeout.R;

/**
 * Created by 楠GG on 2017/6/5.
 */

public class UserFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_, null);
        TextView textView = (TextView) rootView.findViewById(R.id.tv);
        textView.setText("个人");

        return rootView;
    }
}
