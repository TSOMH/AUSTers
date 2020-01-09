package swle.xyz.austers.fragment;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import swle.xyz.austers.R;

public class BannerFirstViewFragment extends Fragment {

    private BannerFirstViewViewModel mViewModel;
    private BottomNavigationView bnv;
    private View view;


    public static BannerFirstViewFragment newInstance() {
        return new BannerFirstViewFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.banner_first_view_fragment, container, false);
        bnv=view.findViewById(R.id.bottomNavigationView);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(BannerFirstViewViewModel.class);
        // TODO: Use the ViewModel
    }

}
