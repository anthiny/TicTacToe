package com.AnA.anthonykim.kim.View;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioGroup;

import com.AnA.anthonykim.kim.ApplicationClass;
import com.AnA.anthonykim.kim.Model.GameTableModel;
import com.AnA.anthonykim.kim.R;
import com.AnA.anthonykim.kim.TicTacToeContract;


import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by anthony on 2016. 11. 29..
 */

public class StatusFragment extends Fragment implements TicTacToeContract.PublishToStatus
{
    private TicTacToeContract.ForwardStatusInteractionToPresenter forwardInteraction;
    private ApplicationClass applicationClass = new ApplicationClass();

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
    public void selectGameMode() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.game_mode_dialog);

        final Button singlePlayButton = (Button)dialog.findViewById(R.id.singlePlay);
        singlePlayButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                forwardInteraction.setSingleMode(true);
                forwardInteraction.saveSetting();
                dialog.dismiss();

            }
        });
        final Button twoPlayButton = (Button)dialog.findViewById(R.id.twoPlay);
        twoPlayButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                forwardInteraction.setSingleMode(false);
                forwardInteraction.saveSetting();
                dialog.dismiss();

            }
        });
        dialog.show();
    }

    @Override
    public void finishApp() {
        getActivity().finish();
        System.exit(0);
    }

}
