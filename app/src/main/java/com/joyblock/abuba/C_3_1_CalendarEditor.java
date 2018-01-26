package com.joyblock.abuba;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.joyblock.abuba.notice.CalendarCustomDialogActivity;
import com.joyblock.abuba.notice.NoticeActivity;
import com.joyblock.abuba.notice.QuestionnaireActivity;
import com.joyblock.abuba.notice.QuestionnaireListViewAdapter;
import com.joyblock.abuba.util.ImageFileProcessor;

public class C_3_1_CalendarEditor extends AppCompatActivity {

    EditText titleText, inText;
    Boolean imageChange = true, touchImage = true; // imageChange1 = true;
    //    private String currentPhotoPath;//실제 사진 파일 경로
    private ListView mListView11 = null;
    private QuestionnaireListViewAdapter mAdapter11 = null;
    ImageView questTimeSettingImageView, timeImage, questionnaireImage;
    TextView deadLineText;
    CalendarCustomDialogActivity mCustomDialog;

    String seq_user,seq_kindergarden,seq_kindergarden_class;
    Integer year, month, day;
    private final int CAMERA_CODE = 1111, GALLERY_CODE = 1112;
//    private Uri photoUri;
//    byte[] image;

    ImageFileProcessor image_file_processor=new ImageFileProcessor();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_c_3_1_calendar_editor);

        titleText = (EditText) findViewById(R.id.titleText);
        inText = (EditText) findViewById(R.id.inText);

        ImageView pictureRegister = (ImageView) findViewById(R.id.pictureRegisterimageView);
        questionnaireImage = (ImageView) findViewById(R.id.editorimageView);

        pictureRegister.setVisibility(View.VISIBLE);

        pictureRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                image_file_processor.selectGallery(QuestionnaireActivity.this,100);
            }
        });
        deadLineText = (TextView) findViewById(R.id.deadLineTimeText);
        TextView txt = (TextView) findViewById(R.id.comenttextView);
        txt.setVisibility(View.VISIBLE);
        final ImageView replyCheck = (ImageView) findViewById(R.id.replyCheckImage);
//        replyCheck.setVisibility(View.VISIBLE);
        questTimeSettingImageView = (ImageView) findViewById(R.id.questionTimeSettingImageView);

        timeImage = (ImageView) findViewById(R.id.timeImageView);
        timeImage.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
//                mCustomDialog = new CalendarCustomDialogActivity(QuestionnaireActivity.this);
//                mCustomDialog.show();
            }
        });

        actionbarCustom();

        mListView11 = (ListView) findViewById(R.id.questListview);
        mAdapter11 = new QuestionnaireListViewAdapter(this);
        mListView11.setAdapter(mAdapter11);
        mAdapter11.addItem(getResources().getDrawable(R.drawable.pho), "");
        mAdapter11.addItem(getResources().getDrawable(R.drawable.pho), "");
        mAdapter11.addItem(getResources().getDrawable(R.drawable.pho), "");

//        setListViewHeightBasedOnChildren(mListView11);

//        mListView11.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                QuestionnaireListViewAdapter.Data item= mAdapter11.data_list.get(position);
//
//
//
////                seq_kindergarden_class=position==0?"0":classList[position-1].seq_kindergarden_class;
////                banListDialogInterface.dismiss();
////                title.setText(item.getName());
////                Toast.makeText(getApplicationContext(), position==0?"전체":item.getName(),Toast.LENGTH_LONG).show();
//            }
//        });


        ImageView imageView = (ImageView) findViewById(R.id.questionListViewInsertImageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter11.addItem(getResources().getDrawable(R.drawable.pho), "");
                Log.d("투표항목수 : ", String.valueOf(mAdapter11.getCount()));
                mListView11.setAdapter(mAdapter11);
//                setListViewHeightBasedOnChildren(mListView11);
            }
        });

//        seq_user = pref.getString("seq_user", "");
//        seq_kindergarden = pref.getString("seq_kindergarden", "");
//        seq_kindergarden_class = pref.getString("seq_kindergarden_class", "");

        Log.d("seq_", seq_user + " " + seq_kindergarden + " " + seq_kindergarden_class);


    }

    public void actionbarCustom() {
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xff0099ff));
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbarcustom);
        TextView title = (TextView) findViewById(R.id.titleName);
        title.setText("전체");
        title.setVisibility(View.VISIBLE);

        TextView backText = (TextView) findViewById(R.id.editorTextLeft);
        backText.setVisibility(View.VISIBLE);
        backText.setText("취소");
        backText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder nd = new AlertDialog.Builder(C_3_1_CalendarEditor.this);
                nd.setMessage("작성을 취소하시겠습니까")
                        .setNegativeButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
//                                Intent intent = new Intent(QuestionnaireActivity.this, NoticeActivity.class);
//                                QuestionnaireActivity.this.startActivity(intent);

                                finish();
                            }
                        })
                        .setPositiveButton("취소", null)
                        .create()
                        .show();
            }
        });

        ImageView backImage = (ImageView) findViewById(R.id.backImage);
        backImage.setVisibility(View.GONE);

        TextView textView = (TextView) findViewById(R.id.editorText);
        textView.setVisibility(View.VISIBLE);
        textView.setText("등록");
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder nd = new AlertDialog.Builder(C_3_1_CalendarEditor.this);
                nd.setMessage("등록하시겠습니까")
                        .setNegativeButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String survey_title=titleText.getText().toString()
                                        ,survey_content=inText.getText().toString();

                                finish();
                            }
                        })
                        .setPositiveButton("취소", null)
                        .create()
                        .show();
            }
        });


        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(Color.parseColor("#0099FF"));
        }
        if (Build.VERSION.SDK_INT >= 23) {
            getWindow().setStatusBarColor(Color.parseColor("#FFFFFF"));
        }
    }

}
