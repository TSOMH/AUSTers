package swle.xyz.austers.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import swle.xyz.austers.R;
import swle.xyz.austers.activity.MyDynamicActivity;
import swle.xyz.austers.activity.MyReleseActivity;
import swle.xyz.austers.activity.MyTripActivity;
import swle.xyz.austers.activity.SettingsActivity;
import swle.xyz.austers.viewmodel.MineViewModel;

public class MineFragment extends Fragment {

    private MineViewModel mViewModel;
    private View view;
    private Button button_my_trip;
    private Button button_my_release;
    private Button button_my_dynamic;
    private Button button_setting;



    public static MineFragment newInstance() {
        return new MineFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.mine_fragment, container, false);
        initView(view);
        initEvent();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MineViewModel.class);

        // TODO: Use the ViewModel
    }

    private void initEvent(){

        button_my_trip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyTripActivity.class);
                startActivity(intent);
            }
        });

        button_my_release.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyReleseActivity.class);
                startActivity(intent);
            }
        });

        button_my_dynamic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyDynamicActivity.class);
                startActivity(intent);
            }
        });
        button_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SettingsActivity.class);
                startActivity(intent);
            }
        });

    }

    private void initView(View view){
        button_my_trip = view.findViewById(R.id.button_my_trip);
        button_my_release = view.findViewById(R.id.button_my_release);
        button_my_dynamic = view.findViewById(R.id.button_my_dynamic);
        button_setting = view.findViewById(R.id.button_setting);



    }

}
