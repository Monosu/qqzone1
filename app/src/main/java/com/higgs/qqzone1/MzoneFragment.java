package com.higgs.qqzone1;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by linxi on 2016/12/8.
 */

public class MzoneFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mzoneLayout=inflater.inflate(R.layout.mzone_layout,container,false);
        return mzoneLayout;
    }
}
