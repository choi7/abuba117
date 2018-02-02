package com.joyblock.abuba.calendar;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.joyblock.abuba.BaseActivity;
import com.joyblock.abuba.R;
import com.joyblock.abuba.TextDialog;
import com.joyblock.abuba.notice.CalendarCustomDialogActivity;
import com.joyblock.abuba.notice.QuestionnaireListViewAdapter;
import com.joyblock.abuba.util.ImageFileProcessor;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

public class C_3_1_CalendarEditor extends BaseActivity {

    EditText titleText, inText;
    Boolean imageChange = true, touchImage = true; // imageChange1 = true;
    //    private String currentPhotoPath;//실제 사진 파일 경로
    private ListView mListView11 = null;
    private QuestionnaireListViewAdapter mAdapter11 = null;
    ImageView questTimeSettingImageView, timeImage, questionnaireImage;
    TextView deadLineText, startTime, endTime;
    //    CalendarCustomDialogActivity mCustomDialog;
    TextDialog mCustomDialog;

    String seq_user, seq_kindergarden, seq_kindergarden_class, is_reply = "y", starthour, endhour;
    Integer year, month, day, push_flag;
    private final int CAMERA_CODE = 1111, GALLERY_CODE = 1112;
    //    private Uri photoUri;
//    byte[] image;
    ConstraintLayout constraintLayout8, constraintLayout9, constraintLayout10;

    ImageFileProcessor image_file_processor = new ImageFileProcessor();
    byte[] files;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_c_3_1_calendar_editor);

        titleText = (EditText) findViewById(R.id.titleText);
        inText = (EditText) findViewById(R.id.inText);
        startTime = (TextView) findViewById(R.id.textView137);
        endTime = (TextView) findViewById(R.id.textView138);

        ImageView pictureRegister = (ImageView) findViewById(R.id.pictureRegisterimageView);
        questionnaireImage = (ImageView) findViewById(R.id.editorimageView);
        constraintLayout8 = (ConstraintLayout) findViewById(R.id.constraintLayout8);
        constraintLayout8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(C_3_1_CalendarEditor.this, C_3_1_CalendarEditor_TimeSetting.class);
                C_3_1_CalendarEditor.this.startActivity(intent);
            }
        });


        constraintLayout9 = (ConstraintLayout) findViewById(R.id.constraintLayout9);
        constraintLayout9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(C_3_1_CalendarEditor.this, C_3_1_CalendarEditor_TimeSetting.class);
                C_3_1_CalendarEditor.this.startActivity(intent);
            }
        });
        constraintLayout10 = (ConstraintLayout) findViewById(R.id.constraintLayout10);

        constraintLayout10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(C_3_1_CalendarEditor.this, C_3_1_CalendarEditor_PlaceSetting.class);
                C_3_1_CalendarEditor.this.startActivity(intent);
            }
        });

        pictureRegister.setVisibility(View.VISIBLE);

        pictureRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                image_file_processor.selectGallery(C_3_1_CalendarEditor.this, 100);

                files = image_file_processor.byteArray;
            }
        });
        deadLineText = (TextView) findViewById(R.id.deadLineTimeText);

        //댓글허용여부
        TextView txt = (TextView) findViewById(R.id.comenttextView);
        txt.setVisibility(View.GONE);
        final ImageView replyCheck = (ImageView) findViewById(R.id.replyCheckImage);
//        replyCheck.setVisibility(View.VISIBLE);
        questTimeSettingImageView = (ImageView) findViewById(R.id.questionTimeSettingImageView);


        actionbarCustom();

        mListView11 = (ListView) findViewById(R.id.questListview);
        mAdapter11 = new QuestionnaireListViewAdapter(this);

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

//        seq_user = pref.getString("seq_user", "");
//        seq_kindergarden = pref.getString("seq_kindergarden", "");
//        seq_kindergarden_class = pref.getString("seq_kindergarden_class", "");

        Log.d("seq_", seq_user + " " + seq_kindergarden + " " + seq_kindergarden_class);


    }

    public void actionbarCustom() {
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xffff9900));
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
                push_flag = 2;

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
                push_flag = 1;
                mCustomDialog = new TextDialog(C_3_1_CalendarEditor.this, R.layout.dialog_call);
                mCustomDialog.setTexts(new String[]{"등록하시겠습니까?", "취소", "확인"});
                mCustomDialog.show();

            }
        });


        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(Color.parseColor("#ff9900"));
        }
        if (Build.VERSION.SDK_INT >= 23) {
            getWindow().setStatusBarColor(Color.parseColor("#FFFFFF"));
        }

        //댓글허용여부 옆 이미지 체크
        final ImageView replyCheck = (ImageView) findViewById(R.id.replyCheckImage);
        replyCheck.setVisibility(View.GONE);
        replyCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageChange) {
                    replyCheck.setImageDrawable(getResources().getDrawable(R.drawable.del));
                    is_reply = "n";
                    imageChange = false;
                } else {
                    replyCheck.setImageDrawable(getResources().getDrawable(R.drawable.ch_off));
                    is_reply = "y";
                    imageChange = true;
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case GALLERY_CODE:
                    image_file_processor.sendPicture(data.getData(), questionnaireImage, C_3_1_CalendarEditor.this); //갤러리에서 가져오기
                    questionnaireImage.setVisibility(View.VISIBLE);
                    break;
                case CAMERA_CODE:
//                    getPictureForPhoto(); //카메라에서 가져오기
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (app.calendarIntent.on) {
            startTime = (TextView) findViewById(R.id.textView137);
            endTime = (TextView) findViewById(R.id.textView138);
            String a, datecount;
            Integer starthours = Integer.valueOf(app.calendarIntent.starthour);
            Integer endhours = Integer.valueOf(app.calendarIntent.endhour);
            if (starthours < 13) {
                datecount = "오전";
                starthour = String.valueOf(starthours);
            } else {
                datecount = "오후";
                starthour = String.valueOf(starthours - 12);
            }

            if (endhours < 13) {
                datecount = "오전";
                endhour = String.valueOf(endhours);
            } else {
                datecount = "오후";
                endhour = String.valueOf(endhours - 12);
            }

            startTime.setText(app.calendarIntent.startmonth + "월 " + app.calendarIntent.startday + "일 " + "(" + app.calendarIntent.start_day_of_week + ")    " + datecount + " " + starthour + ":" + app.calendarIntent.startminute);
            endTime.setText(app.calendarIntent.endmonth + "월 " + app.calendarIntent.endday + "일 " + "(" + app.calendarIntent.end_day_of_week + ")    " + datecount + " " + endhour + ":" + app.calendarIntent.endminute);

        }
    }

    public void clickTextView2(View v) {
        mCustomDialog.dismiss();

    }

    public void clickTextView3(View v) {
        mCustomDialog.dismiss();
        String seq_kindergarden = pref.getString("seq_kindergarden","");


        switch (push_flag) {
            case 1:
                String survey_title = titleText.getText().toString(), survey_content = inText.getText().toString();
                if (!survey_title.equals("") && !survey_content.equals("")) {
//                                    api.API_41(seq_user,seq_kindergarden,);
                    Integer startmonths = Integer.valueOf(app.calendarIntent.startmonth);
                    Integer endmonths = Integer.valueOf(app.calendarIntent.endmonth);
                    String startmonth, endmonth;
                    if(startmonths < 10) {
                        startmonth = "0"+startmonths;
                    } else {
                        startmonth = String.valueOf(startmonths);
                    }
                    if(endmonths < 10) {
                        endmonth = "0"+endmonths;
                    } else {
                        endmonth = String.valueOf(endmonths);
                    }

                    api.API_41(app.seq_user, seq_kindergarden, app.calendarIntent.startyear, startmonth, app.calendarIntent.startday, app.calendarIntent.endyear,
                            endmonth, app.calendarIntent.endday, app.calendarIntent.starthour, app.calendarIntent.startminute, app.calendarIntent.endhour,
                            app.calendarIntent.endminute, titleText.getText().toString(), inText.getText().toString(), "", "", "", files);
                    String ss = api.getMessage();
                }
                break;
            case 2:
                break;
            default:
                break;
        }

        finish();

    }

    public void clickTextViewOne(View v) {
        mCustomDialog.dismiss();
    }


}
