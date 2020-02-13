package swle.xyz.austers.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import swle.xyz.austers.R;
import swle.xyz.austers.viewmodel.SystemMessageViewModel;

public class SystemMessageFragment extends Fragment {

    private SystemMessageViewModel mViewModel;

    public static SystemMessageFragment newInstance() {
        return new SystemMessageFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.system_message_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(SystemMessageViewModel.class);
        // TODO: Use the ViewModel
    }

}
