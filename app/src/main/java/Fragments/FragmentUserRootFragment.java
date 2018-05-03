package Fragments;

import android.app.Fragment;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;

import com.example.mokuryuu.login.R;


public class FragmentUserRootFragment extends Fragment  {

    private static String mfrom;
    public static Fragment shareInstance(String from){
        FragmentUserRootFragment fragment = new FragmentUserRootFragment();
        mfrom = from;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_root, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

}
