package swle.xyz.austers.fragment;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import swle.xyz.austers.viewmodel.DiscoveryViewModel;
import swle.xyz.austers.R;

public class DiscoveryFragment extends Fragment {

    private DiscoveryViewModel mViewModel;

    public static DiscoveryFragment newInstance() {
        return new DiscoveryFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.discovery_fragment, container, false);
        return view;

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(DiscoveryViewModel.class);
        // TODO: Use the ViewModel
    }

}
