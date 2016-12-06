package com.example.anthonykim.kim.View;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.anthonykim.kim.ApplicationClass;
import com.example.anthonykim.kim.R;
import com.example.anthonykim.kim.TicTacToeContract;

import butterknife.BindView;
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

    @BindView(R.id.time_progress)
    ProgressBar progressBar;

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
    public void chooseFirst() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setTitle(getResources().getString(R.string.setting_dialog_title));
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.setting_dialog);

        final Spinner s = (Spinner)dialog.findViewById(R.id.limitTimeTable);
        final RadioGroup radioGroup = (RadioGroup)dialog.findViewById(R.id.choose);

        final Button button = (Button)dialog.findViewById(R.id.startButton);
        button.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                dialog.dismiss();
                switch (radioGroup.getCheckedRadioButtonId()){
                    case R.id.optionFirst:
                        forwardInteraction.saveSetting(Integer.parseInt(s.getSelectedItem().toString()), true);
                        break;

                    case R.id.optionSecond:
                        forwardInteraction.saveSetting(Integer.parseInt(s.getSelectedItem().toString()), false);
                        break;
                }

            }
        });
        dialog.show();
    }

    @Override
    public void setProgressMax(int maxValue) {
        progressBar.setMax(maxValue);
        progressBar.setProgress(maxValue);
    }

    @Override
    public void changingProgressValue(int value) {
        progressBar.setProgress(value);
    }

    @Override
    public void finishApp() {
        getActivity().finish();
        System.exit(0);
    }

}
