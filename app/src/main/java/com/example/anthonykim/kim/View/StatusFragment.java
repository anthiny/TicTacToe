package com.example.anthonykim.kim.View;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.Toast;

import com.example.anthonykim.kim.R;
import com.example.anthonykim.kim.TicTacToeContract;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by anthony on 2016. 11. 29..
 */

public class StatusFragment extends Fragment implements TicTacToeContract.PublishToStatus {
    private TicTacToeContract.ForwardStatusInteractionToPresenter forwardInteraction;

    public void setPresenter(TicTacToeContract.ForwardStatusInteractionToPresenter forwardInteraction){
        this.forwardInteraction = forwardInteraction;
    }

    @OnClick(R.id.reset_button)
    public void onResetClick(){
        forwardInteraction.onResetButtonClick();
    }

    public StatusFragment(){

    }

    public static StatusFragment newInstance(){
        return new StatusFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.status_fragment, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void showTime(String time) {
        Toast.makeText(getActivity(),time, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void finishApp() {
        getActivity().finish();
        System.exit(0);
    }

}
