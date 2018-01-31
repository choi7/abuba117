package com.joyblock.abuba.register;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.joyblock.abuba.BaseActivity;
import com.joyblock.abuba.CircleTransform;
import com.joyblock.abuba.R;
import com.joyblock.abuba.TextDialog;
import com.joyblock.abuba.util.ImageFileProcessor;
import com.joyblock.abuba.util.TimeConverter;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import okhttp3.FormBody;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class ChildrenRegisterActivity extends BaseActivity {
    //AppCompatActivity
    Spinner nickSpinner;
    EditText nameEditText1, birthdayEditText, birthMonthEdithText, birthYearEditText, childrenNameEditText2;
    TextView childrenBirthdayText;
    String id;
    String nick;
    String kids_name;
    String birth;
    String birth_year;
    String birth_month;
    String birth_day;
    String seq_user;
    String files;
    String sex = "m", name_title = "엄마";
    String inserKidsUrl = "http://58.229.208.246/Ububa/insertKids.do";
    Button nextButton;
    ImageView childrenPictureImageView, imageView1, imageView2, imageView3, imageView4;
    ImageFileProcessor imageFileProcessor = new ImageFileProcessor();
    ConstraintLayout constraintLayout1, constraintLayout2, constraintLayout3, constraintLayout4;
    boolean a1 = true, a2 = true;
    SharedPreferences.Editor editor;
    TextDialog mCustomDialog;
    byte[] bytes;

    //protected
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_children_register);

        if (Build.VERSION.SDK_INT >= 21) {
            // 21 버전 이상일 때
            getWindow().setStatusBarColor(Color.parseColor("#33CCCC"));
        }
        if (Build.VERSION.SDK_INT >= 23) {
            getWindow().setStatusBarColor(Color.parseColor("#FFFFFF"));
        }

        childrenPictureImageView = (ImageView) findViewById(R.id.childrenPictureImageView);
        childrenPictureImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageFileProcessor.selectGallery(ChildrenRegisterActivity.this, 100);
            }
        });

        nameEditText1 = (EditText) findViewById(R.id.childrenNameEditText1);
        childrenBirthdayText = (TextView) findViewById(R.id.childrenBirthdayText);
        childrenBirthdayText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                Date date;
                DatePickerDialog datePickerDialog = new DatePickerDialog(ChildrenRegisterActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        try {
                            Date d = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                            birth = year + "년 " + (monthOfYear + 1) + "월 " + dayOfMonth + "일";
                            childrenBirthdayText.setText(birth);
                            birth_year = String.valueOf(year);
                            if(monthOfYear < 10) {
                                birth_month = "0" + String.valueOf(monthOfYear + 1);
                            } else {
                                birth_month = String.valueOf(monthOfYear + 1);
                            }
                            if(dayOfMonth < 10) {
                                birth_day = "0"+String.valueOf(dayOfMonth);
                            } else {
                                birth_day = String.valueOf(dayOfMonth);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setCalendarViewShown(false);
                datePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                datePickerDialog.show();
            }
        });

        nextButton = (Button) findViewById(R.id.childrenPushButton);
        constraintLayout1 = (ConstraintLayout) findViewById(R.id.constraintLayout20);
        constraintLayout2 = (ConstraintLayout) findViewById(R.id.constraintLayout21);
        constraintLayout3 = (ConstraintLayout) findViewById(R.id.constraintLayout22);
        constraintLayout4 = (ConstraintLayout) findViewById(R.id.constraintLayout23);
        imageView1 = (ImageView) findViewById(R.id.imageView11);
        imageView2 = (ImageView) findViewById(R.id.imageView10);
        imageView3 = (ImageView) findViewById(R.id.imageView30);
        imageView4 = (ImageView) findViewById(R.id.imageView31);
        childrenNameEditText2 = (EditText) findViewById(R.id.childrenNameEditText2);


        constraintLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView1.setImageDrawable(getResources().getDrawable(R.drawable.check_on));
                imageView2.setImageDrawable(getResources().getDrawable(R.drawable.check_off));
                sex = "m";
            }
        });

        constraintLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView1.setImageDrawable(getResources().getDrawable(R.drawable.check_off));
                imageView2.setImageDrawable(getResources().getDrawable(R.drawable.check_on));
                sex = "f";
            }
        });
        constraintLayout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (a1) {
                    imageView3.setImageDrawable(getResources().getDrawable(R.drawable.check_on));
                    imageView4.setImageDrawable(getResources().getDrawable(R.drawable.check_off));
                    name_title = "엄마";
                    a1 = false;
                } else {
                    imageView3.setImageDrawable(getResources().getDrawable(R.drawable.check_off));
                    a1 = true;
                }

            }
        });
        constraintLayout4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(a2) {
                    imageView3.setImageDrawable(getResources().getDrawable(R.drawable.check_off));
                    imageView4.setImageDrawable(getResources().getDrawable(R.drawable.check_on));
                    name_title = "아빠";
                    a2 = false;
                } else {
                    imageView4.setImageDrawable(getResources().getDrawable(R.drawable.check_off));
                    a2 = true;
                }

            }
        });

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbarcustom);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xff33cccc));
        TextView title = (TextView) findViewById(R.id.titleName);
        title.setText("자녀등록하기");
        title.setVisibility(View.VISIBLE);

        final Intent intent = getIntent();
        id = intent.getStringExtra("id");

//        pref = getSharedPreferences(id,MODE_PRIVATE);
        seq_user = pref.getString("seq_user", "없음");


        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String kids_name = nameEditText1.getText().toString();
                if((childrenNameEditText2.equals("")))
                    name_title = childrenNameEditText2.getText().toString();


                Log.d("equal", kids_name + " " + sex +" "+birth_year +" " + birth_month + " " + birth_day + " " + name_title);
                if (!kids_name.equals("") && !kids_name.equals(null) && !sex.equals("")
                        && !sex.equals(null) && !birth_year.equals("") && !birth_year.equals(null) && !name_title.equals("")
                        && !name_title.equals(null)) {
                    new InsertKids(seq_user, kids_name, sex, birth_year, birth_month, birth_day, name_title).execute();
                    Intent intent = new Intent(ChildrenRegisterActivity.this, SchoolRegister_1Activity.class);
                    intent.putExtra("kids_name", kids_name);
                    intent.putExtra("sex", sex);
                    intent.putExtra("birth_year", birth_year);
                    intent.putExtra("birth_month", birth_month);
                    intent.putExtra("birth_day", birth_day);
                    intent.putExtra("files", files);
                    intent.putExtra("name_title", name_title);
                    intent.putExtra("activity","ChildrenRegisterActivity");
                    ChildrenRegisterActivity.this.startActivity(intent);
                } else {
                    mCustomDialog = new TextDialog(ChildrenRegisterActivity.this, R.layout.dialog_one_check);
                    mCustomDialog.setTexts(new String[]{"정보를 입력해주세요", "확인"});
                    mCustomDialog.show();
                }

            }
        });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Log.d("requestCode :", requestCode + "");
            if (requestCode == 100) {
                imageFileProcessor.sendPicture(data.getData(), childrenPictureImageView, ChildrenRegisterActivity.this); //갤러리에서 가져오기
                Object[] drawable_byteArray = imageFileProcessor.getDrawableAndByteArray(data.getData(), ChildrenRegisterActivity.this);
                bytes = (byte[]) drawable_byteArray[1];
                Picasso.with(this).load(data.getData()).transform(new CircleTransform()).into(childrenPictureImageView);
//                Picasso.with(this).load((BitmapDrawable) drawable_byteArray[0]).into(profileImage);
//                Picasso.with(this).load
            } else {
                Object[] drawable_byteArray = imageFileProcessor.getDrawableAndByteArray(data.getData(), ChildrenRegisterActivity.this); //갤러리에서 가져오기
//                mAdapter11.setIcon(requestCode,(Drawable) drawable_byteArray[0],(byte[])drawable_byteArray[1]);
//                mListView11.setAdapter(mAdapter11);


            }
        }
    }


    public class InsertKids extends AsyncTask<Void, Void, String> {
        OkHttpClient client;
        okhttp3.Request request;
        RequestBody formBody;
        MultipartBody.Builder builder;

//        public InsertKids(String seq_user, String kids_name, String sex, String birth_year, String birth_month, String birth_day, String name_title) {
//            client = new OkHttpClient();
//            formBody = new FormBody.Builder()
//                    .add("seq_user", seq_user)
//                    .add("kids_name", kids_name)
//                    .add("sex", sex)
//                    .add("birth_year", birth_year)
//                    .add("birth_month", birth_month)
//                    .add("birth_day", birth_day)
//                    .add("name_title", name_title)
//                    .build();
//            request = new okhttp3.Request.Builder()
//                    .url(inserKidsUrl)
//                    .post(formBody)
//                    .build();
//        }
        public InsertKids(String seq_user, String kids_name, String sex, String birth_year, String birth_month, String birth_day, String name_title) {
            client = new OkHttpClient();
            builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
            builder.addFormDataPart("seq_user", seq_user)
                    .addFormDataPart("kids_name", kids_name)
                    .addFormDataPart("sex", sex)
                    .addFormDataPart("birth_year", birth_year)
                    .addFormDataPart("birth_month", birth_month)
                    .addFormDataPart("birth_day", birth_day)
                    .addFormDataPart("name_title", name_title);
            if(bytes != null) {
                builder.addFormDataPart("files", TimeConverter.getFileTime()+".png", RequestBody.create(MultipartBody.FORM,bytes));
            }
            formBody = builder.build();
            request = new okhttp3.Request.Builder()
                    .url(inserKidsUrl)
                    .post(formBody)
                    .build();
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
            Log.d("TAG", json);
            try {
                JSONObject jsonResponse = new JSONObject(json);

                Integer ss = Integer.parseInt(jsonResponse.getString("resultCode"));
                System.out.println(ss);
                JSONObject jsonObject = jsonResponse.getJSONObject("retMap");


                String seq_kids = jsonObject.getString("seq_kids");

                SharedPreferences.Editor editor;
                editor = pref.edit();
                editor.putString("seq_kids", seq_kids);
                editor.commit();
                Log.d("seq_kids", String.valueOf(seq_kids));


                if (ss == 200) {
                    Intent intent = new Intent(ChildrenRegisterActivity.this, SchoolRegister_1Activity.class);
                    ChildrenRegisterActivity.this.startActivity(intent);
                    finish();
                    /*
                    AlertDialog.Builder builder = new AlertDialog.Builder(ChildrenRegisterActivity.this);
                    builder.setMessage("자녀 등록에 성공했습니다.")
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(ChildrenRegisterActivity.this, SchoolRegister_1Activity.class);
                                    ChildrenRegisterActivity.this.startActivity(intent);
                                    finish();
                                }
                            })
                            .create()
                            .show();
                            */


                } else if (ss == 102 | ss == 101) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ChildrenRegisterActivity.this);
                    builder.setMessage("자녀 등록에 실패했습니다.")
                            .setNegativeButton("다시 시도", null)
                            .create()
                            .show();
                } else {
                    System.out.println("Error");
                    System.out.println("Error");
                    System.out.println("Error");
                    System.out.println("Error");
                    System.out.println("Error");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }



        }
    }



    public void clickTextViewOne(View v) {
        mCustomDialog.dismiss();
    }


}
