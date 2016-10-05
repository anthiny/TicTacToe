package com.example.anthonykim.kim;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.SystemClock;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.example.anthonykim.kim.R.drawable.o;

public class MainActivity extends AppCompatActivity {
    GridView gv;
    Vibrator vibrator;
    int status[]={
            0,0,0,0,0,0,0,0,0
    };
    List<Integer> edges = new ArrayList<>();
    Boolean aiWin = false;
    Boolean userWin = false;
    int userTurn =  0;
    int totalTurn = 9;
    private long mLastClickTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);
        ChooseFirst();
        edges.add(0);
        edges.add(2);
        edges.add(6);
        edges.add(8);

        int img[]={
         R.drawable.blank, o, R.drawable.x
        };

        MyAdapter adapter = new MyAdapter (
                getApplicationContext(),
                R.layout.items,
                status,
                img
        );

        gv = (GridView)findViewById(R.id.gridView1);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        gv.setAdapter(adapter);

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(SystemClock.elapsedRealtime() - mLastClickTime < 500){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();

                if (status[position]==0){
                    Log.d("Human","Human Index: "+position);
                    ImageView iv = (ImageView)view.findViewById(R.id.imageView1);
                    iv.setImageResource(o);
                    status[position]=1;
                    userTurn++;
                    totalTurn--;

                    for(int i=0;i<edges.size();i++){
                        if(edges.get(i)==position){
                            edges.remove(i);
                            break;
                        }
                    }

                    if (userTurn > 2){
                        if(FindWinPoint(1) == 99){
                            userWin = true;
                            WinPopUp("Human");
                        }
                    }

                    if(totalTurn!=0 && !userWin){
                        Ai();
                    }
                    else if(totalTurn==0 && !userWin){
                        WinPopUp("Draw");
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "Already Clicked !", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void Ai(){
        int winPoint = FindWinPoint(2);
        int userWinPoint = FindWinPoint(1);

        Log.d("user Win","User Win Point: "+ userWinPoint);

        if (winPoint != -1) {
            status[winPoint] = 2;
            DisplayForAi(winPoint);
            aiWin = true;
        }
        else if (userWinPoint != -1){
            status[userWinPoint] = 2;
            if(userWinPoint==0 || userWinPoint == 2 || userWinPoint==6 || userWinPoint ==8){
                DeleteEdges(userWinPoint);
            }
            DisplayForAi(userWinPoint);
        }
        else{
            int temp = FindEmptyEdge();
            if (temp != -1){
                status[temp] = 2;
                DeleteEdges(temp);
                DisplayForAi(temp);
                Log.d("deleteEdge", "DeletedEdge is "+temp+"////" +edges.size());
            }
            else if (IsCoreEmpty()){
                status[4] = 2;
                DisplayForAi(4);
            }
            else
            {
                temp = FindEmptyRandomIndex();
                if(temp==0 || temp == 2 || temp==6 || temp ==8){
                    DeleteEdges(temp);
                }
                status[temp] = 2;
                DisplayForAi(temp);
            }
        }
        totalTurn--;
        if (aiWin){
            WinPopUp("AI");
        }
        else if(!aiWin && totalTurn==0){
            WinPopUp("Draw");
        }
    }

    public void DeleteEdges(int edgeIndex){
        for(int i=0;i<edges.size();i++){
            if(edges.get(i)==edgeIndex){
                edges.remove(i);
                break;
            }
        }
    }

    public int FindWinPoint(int x){
        int cnt = 0;
        int tempEmptyIndex = -1;
        int index = 0;

        for(int i =0;i<3;i++){
            for(int j=0;j<3;j++){
                index = i*3+j;
                if(status[index] == x) {
                    cnt++;
                }
                else if(status[index] == 0){
                    tempEmptyIndex = index;
                }
                else{
                    cnt--;
                }
            }
            if (cnt == 2){
                return tempEmptyIndex;
            }
            else if(cnt == 3){
                return 99;
            }
            else{
                cnt = 0;
            }
        }

        for(int i =0;i<3;i++){
            for(int j=0;j<3;j++){
                index = i+j*3;
                if(status[index]==x){
                    cnt++;
                }
                else if(status[index] == 0){
                    tempEmptyIndex = index;
                }
                else{
                    cnt--;
                }
            }
            if (cnt == 2){
                return tempEmptyIndex;
            }
            else if(cnt == 3){
                return 99;
            }
            else{
                cnt = 0;
            }
        }

        int weight = 4;
        for(int i =0;i<2;i++){
            for(int j=0;j<3;j++){

                if (i == 1)
                    weight = 2;

                index = i*2+j*weight;

                if(status[index]==x){
                    cnt++;
                }
                else if(status[index] == 0){
                    tempEmptyIndex = index;
                }
                else{
                    cnt--;
                }
            }
            if (cnt == 2){
                return  tempEmptyIndex;
            }
            else if(cnt == 3){
                return 99;
            }
            else{
                cnt = 0;
            }
        }

        return -1;
    }

    public boolean IsCoreEmpty(){
        if (status[4] != 0)
            return false;

        return true;
    }

    public int  FindEmptyRandomIndex(){
        Random rand= new Random();
        int randIndex = 0;
        List<Integer> emptyIndexs= new ArrayList<>();
        for (int i=0;i<status.length;i++){
            if (status[i] == 0){
                emptyIndexs.add(i);
            }
        }
        randIndex = rand.nextInt(emptyIndexs.size());
        Log.d("FindRand","FindRand Index: "+emptyIndexs.get(randIndex));
        return emptyIndexs.get(randIndex);
    }

    public int  FindEmptyEdge(){
        Random rand= new Random();
        int randIndex = 0;
        if(edges.size() == 0)
            return -1;
        randIndex =  rand.nextInt(edges.size());
        return edges.get(randIndex);
    }

    public void DisplayForAi(int x){
        ViewGroup gridChild = (ViewGroup)gv.getChildAt(x);
        Log.d("check","AI index "+ x);
        ImageView iv = (ImageView)gridChild.findViewById(R.id.imageView1);
        iv.setImageResource(R.drawable.x);
    }

    public void OnResetButtonClick(View view){
        ResetGameTable();
        ChooseFirst();
    }

    public void ResetGameTable(){
        for(int i=0;i<gv.getChildCount();i++){
            ViewGroup gridChild = (ViewGroup)gv.getChildAt(i);
            ImageView iv = (ImageView)gridChild.findViewById(R.id.imageView1);
            iv.setImageResource(R.drawable.blank);
            status[i] = 0;
        }
        edges.clear();
        edges.add(0);
        edges.add(2);
        edges.add(6);
        edges.add(8);

        aiWin = false;
        userWin = false;
        userTurn = 0;
        totalTurn = 9;
    }

    public void ChooseFirst(){
        AlertDialog.Builder builder  = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("Who is First?");
        builder.setMessage("Choose First or Second...");
        builder.setPositiveButton("Second", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               Ai();
            }
        });
        builder.setNegativeButton("First", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    public void WinPopUp(String who){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        vibrator.vibrate(2000);
        if(who == "Draw"){
            builder.setTitle("DRAW!!!!");
        }
        else{
            builder.setTitle("Winner is "+who);
        }
        builder.setMessage("Continue or End ...?");
        builder.setPositiveButton("End", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
                System.exit(0);
            }
        });
        builder.setNegativeButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ResetGameTable();
                ChooseFirst();
            }
        });
        builder.show();
    }
}

class MyAdapter extends BaseAdapter {
    Context context;
    int layout;
    int img[];
    int index[];
    LayoutInflater inf;

    public MyAdapter(Context context, int layout, int[] index, int[] img){
        this.context = context;
        this.layout = layout;
        this.index = index;
        this.img = img;
        inf = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return index.length;
    }

    @Override
    public Object getItem(int position) {
        return index[position];
    }

    @Override
    public  long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null)
            convertView = inf.inflate(layout, null);
        ImageView iv = (ImageView)convertView.findViewById(R.id.imageView1);
        iv.setImageResource(img[0]);
        return convertView;
    }

}
