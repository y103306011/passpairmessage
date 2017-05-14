package edu.nccu.mis.passpair;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FriendMail extends AppCompatActivity {

    private ListView lisf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_friend_mail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        /**
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        final String UiD = bundle.getString("UIDFri");
        **/
        // 取得介面元件
        lisf = (ListView) findViewById(R.id.lisf);

        // 建立 ArrayAdapter
        final ArrayAdapter<String> adapterBalls = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, android.R.id.text1);

        // 設定 ListView 的資料來源
        lisf.setAdapter(adapterBalls);


        // 設定 lstPrefer 元件 ItemClick 事件的 listener為 lstPreferListener
        lisf.setOnItemClickListener(lstPreferListener);

        //讀取資料
        DatabaseReference reference_contacts = FirebaseDatabase.getInstance().getReference("User");
        reference_contacts.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                adapterBalls.clear();

                //adapterBalls.add(dataSnapshot.child("TSRG4uPmjDhmFDHoVZVOVyU1AOM2").child("好友清單").child("交友邀請").child("1").getValue(String.class));

                adapterBalls.add("1");

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                // Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }
        private ListView.OnItemClickListener lstPreferListener=
                new ListView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        // 顯示 ListView 的選項內容

                        final String nam = parent.getItemAtPosition(position).toString();

                        new AlertDialog.Builder(FriendMail.this)
                                .setTitle("確認視窗")
                                .setIcon(R.mipmap.ic_launcher)
                                .setMessage("要確認交友邀請嗎？")
                                .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialoginterface, int i)
                                    {
                                        String Ref ="User";
                                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                                        DatabaseReference myRef = database.getReference(Ref);

                                        myRef.child("TSRG4uPmjDhmFDHoVZVOVyU1AOM2").child("好友清單").child(nam).setValue(nam);
                                        myRef.child("TSRG4uPmjDhmFDHoVZVOVyU1AOM2").child("好友清單").child("交友邀請").child("1").removeValue();

                                        finish();
                                    }
                                })
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialoginterface, int i)
                                    {

                                    }
                                })
                                .show();
                    }








                };
    }


