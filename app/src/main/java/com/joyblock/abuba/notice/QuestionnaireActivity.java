package com.joyblock.abuba.notice;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.joyblock.abuba.BaseActivity;
import com.joyblock.abuba.util.ImageFileProcessor;
import com.joyblock.abuba.R;
import com.joyblock.abuba.util.TimeConverter;
import com.joyblock.abuba.api_message.R18_InsertSurvey;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okio.Buffer;

public class QuestionnaireActivity extends BaseActivity {

    EditText titleText, inText;
    Boolean imageChange = true, touchImage = true; // imageChange1 = true;
    //    private String currentPhotoPath;//실제 사진 파일 경로
    private ListView mListView11 = null;
    private QuestionnaireListViewAdapter mAdapter11 = null;
    ImageView questTimeSettingImageView, timeImage, questionnaireImage;
    TextView deadLineText;
    CalendarCustomDialogActivity mCustomDialog;

    String seq_user,seq_kindergarden,seq_kindergarden_class;
    Integer year=0, month=0, day=0;
    private final int CAMERA_CODE = 1111, GALLERY_CODE = 1112;
//    private Uri photoUri;
//    byte[] image;

    ImageFileProcessor image_file_processor=new ImageFileProcessor();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire1);

        titleText = (EditText) findViewById(R.id.titleText);
        inText = (EditText) findViewById(R.id.inText);

        ImageView pictureRegister = (ImageView) findViewById(R.id.pictureRegisterimageView);
        questionnaireImage = (ImageView) findViewById(R.id.editorimageView);

        pictureRegister.setVisibility(View.VISIBLE);

        pictureRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image_file_processor.selectGallery(QuestionnaireActivity.this,100);
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
                mCustomDialog = new CalendarCustomDialogActivity(QuestionnaireActivity.this);
                mCustomDialog.show();
            }
        });

        actionbarCustom();

        mListView11 = (ListView) findViewById(R.id.questListview);
        mAdapter11 = new QuestionnaireListViewAdapter(this);
        mListView11.setAdapter(mAdapter11);
        mAdapter11.addItem(getResources().getDrawable(R.drawable.pho), "");
        mAdapter11.addItem(getResources().getDrawable(R.drawable.pho), "");
        mAdapter11.addItem(getResources().getDrawable(R.drawable.pho), "");

        setListViewHeightBasedOnChildren(mListView11);

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
                setListViewHeightBasedOnChildren(mListView11);
            }
        });

        seq_user = pref.getString("seq_user", "");
        seq_kindergarden = pref.getString("seq_kindergarden", "");
        seq_kindergarden_class = pref.getString("seq_kindergarden_class", "");

        Log.d("seq_", seq_user + " " + seq_kindergarden + " " + seq_kindergarden_class);


    }

    public void datepush(View v) {
//        mCustomDialog.qa.day = mCustomDialog.datePicker.getDayOfMonth();
        day = mCustomDialog.datePicker.getDayOfMonth();
        month = mCustomDialog.datePicker.getMonth() + 1;
        year = mCustomDialog.datePicker.getYear();
        mCustomDialog.imageView.setImageResource(R.drawable.ch_on);
        System.out.println(mCustomDialog.qa.day + "" + mCustomDialog.qa.month + "" + mCustomDialog.qa.year);
        String days = null, months = null;
        if (day < 10) {
            days = "0" + day;
        } else {
            days = String.valueOf(day);
        }
        if (month < 10) {
            months = "0" + month;
        } else {
            months = String.valueOf(month);
        }
        deadLineText.setText(year + "년 " + months + "월 " + days + "일 " + " 24:00");
        mCustomDialog.dismiss();
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
                AlertDialog.Builder nd = new AlertDialog.Builder(QuestionnaireActivity.this);
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
                AlertDialog.Builder nd = new AlertDialog.Builder(QuestionnaireActivity.this);
                nd.setMessage("등록하시겠습니까")
                        .setNegativeButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(mAdapter11.getSize()<2)
                                    Toast.makeText(QuestionnaireActivity.this,"최소 2개 이상의 항목을 설정하세요.",Toast.LENGTH_LONG).show();
                                else if(year*month*day==0)
                                    Toast.makeText(QuestionnaireActivity.this,"날짜를 설정하세요.",Toast.LENGTH_LONG).show();
                                else{
                                    String survey_title = titleText.getText().toString(), survey_content = inText.getText().toString();

                                    new InsertSurvey(seq_user, seq_kindergarden, seq_kindergarden_class,
                                            survey_title, survey_content, year + "", month + "", day + "", mAdapter11.data_list).execute();

//                                Intent intent = new Intent(QuestionnaireActivity.this, NoticeActivity.class);
//                                intent.putExtra("fragment_num",2);
//                                QuestionnaireActivity.this.startActivity(intent);
                                    finish();
                                }
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



    /*
    선택한 사진 데이터 처리
    카메라로 사진을 찍거나 갤러리에서 사진 선택에 대한 사용자가 응답을 할경우
     'onActivityResult'가 실행되는데 사진을 선택했을 경우 resultCode값은
     'RESULT_OK' 가 취소 했을때는 'RESULT_CANCEL'
    사진 데이터는 intent 타입으로 반환.
    */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            Log.d("requestCode :",requestCode+"");
            if(requestCode==100)
                image_file_processor.sendPicture(data.getData(),questionnaireImage,QuestionnaireActivity.this); //갤러리에서 가져오기
            else {
                Object[] drawable_byteArray=image_file_processor.getDrawableAndByteArray(data.getData(), QuestionnaireActivity.this); //갤러리에서 가져오기
                mAdapter11.setIcon(requestCode,(Drawable) drawable_byteArray[0],(byte[])drawable_byteArray[1]);
                mListView11.setAdapter(mAdapter11);



            }
        }
    }


    //설문지 항목 리스트가 추가 될때마다 스크롤이 되는데 이때 스크롤을 없애고 공간을 늘리는 메소드
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            //listItem.measure(0, 0);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();

        params.height = totalHeight;
        listView.setLayoutParams(params);

        listView.requestLayout();
    }


    R18_InsertSurvey detail;

    class InsertSurvey extends AsyncTask<Void, Void, String> {
        OkHttpClient client;
        okhttp3.Request request;
        RequestBody formBody;
        String api_string="insertSurvey";
        String message_key="";
        String url = "http://58.229.208.246/Ububa/"+api_string+".do";
        MultipartBody.Builder builder;

        //설문지 작성
        public InsertSurvey(String seq_user, String seq_kindergarden, String seq_kindergarden_class,
                            String title, String content, String year, String month, String day,
                            ArrayList<QuestionnaireListViewAdapter.Data> list) {
            client = new OkHttpClient();
            builder=new MultipartBody.Builder().setType(MultipartBody.FORM);
//            formBody = new MultipartBody.Builder().setType(MultipartBody.FORM)//new FormBody.Builder()
            int size=list.size();
            builder.addFormDataPart("seq_user", seq_user)
                    .addFormDataPart("seq_kindergarden", seq_kindergarden)
                    .addFormDataPart("seq_kindergarden_class", ""/*seq_kindergarden_class*/)
                    .addFormDataPart("title", title)
                    .addFormDataPart("content", content)
                    .addFormDataPart("year", year)
                    .addFormDataPart("month", month)
                    .addFormDataPart("day", day)
                    .addFormDataPart("c_survey_vote", size+"");
//                    .build();

            for(int i=0;i<size;i++) {
                QuestionnaireListViewAdapter.Data data=list.get(i);
                if(data.image_byte_array!=null)
                    builder.addFormDataPart("files[" + i + "]", TimeConverter.getFileTime() + ".png", RequestBody.create(MultipartBody.FORM, data.image_byte_array));
                builder.addFormDataPart("vote_item["+i+"]",data.mTitle );
            }
//
//            for(int i=0;i<vote_item.length;i++)


            formBody=builder.build();

            request = new okhttp3.Request.Builder()
                    .url(url)
                    .post(formBody)
                    .build();

//            Log.d("API18 request : ", request.body().toString());

            printRequest("API18");
        }

        void printRequest(String tag) {
            try {
                final Buffer buffer = new Buffer();
                formBody.writeTo(buffer);
                Log.d(tag+" request", buffer.readUtf8());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                okhttp3.Response response = client.newCall(request).execute();

                if (!response.isSuccessful()) {
                    return null;
                }
                return response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String json) {
            super.onPostExecute(json);
            Log.d("API18 response : ", json);
            try {
                JSONObject jsonResponse = new JSONObject(json);
                Integer ss = Integer.parseInt(jsonResponse.getString("resultCode"));
//                detail=new GsonBuilder().create().fromJson(jsonResponse.getString("notice"),R14_SelectNoticeOne.class);
//                Log.d("detail" , String.valueOf(detail));
//                Picasso.with(getApplicationContext()).load(detail.file_path).into(detailImage);
//                detailImage.setVisibility(View.VISIBLE);
//
//                setNotice(detail.seq_kindergarden_class,detail.title,getResources().getDrawable(R.mipmap.ic_document),detail.name, TimeConverter.convert(detail.reg_date),detail.content,detail.equals("y"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
