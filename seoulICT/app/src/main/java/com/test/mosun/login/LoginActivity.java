package com.test.mosun.login;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.callback.UnLinkResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.usermgmt.response.model.Profile;
import com.kakao.usermgmt.response.model.UserAccount;
import com.kakao.util.OptionalBoolean;
import com.kakao.util.exception.KakaoException;
import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;
import com.nhn.android.naverlogin.ui.view.OAuthLoginButton;
import com.test.mosun.AppManager;
import com.test.mosun.MainActivity;
import com.test.mosun.R;
import com.test.mosun.data.LoginData;
import com.test.mosun.data.LoginResponse;
import com.test.mosun.network.RetrofitClient;
import com.test.mosun.network.ServiceApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/** 로그인 액티비티
 *  네이버 및 카카오 로그인
 *
 *  [해야 하는 것]
 *  로딩액티비티 만들고 로그인 되어있으면 바로 로그인 버튼 없이 mainActivity 로 가게 만들기
 *  네이버나 카카오 계정이 없는 경우
 *  동의 항목을 선택 해제 하는 경우 > 다시 동의 받는 부분
 *  핸드폰 번호 얻는 부분
 */
public class LoginActivity extends Activity  {

    private static final String TAG = "OAuthSampleActivity";

    /**
     * client 정보를 넣어준다.
     */
    private static String OAUTH_CLIENT_ID = "Y15miqwrgiKQChGv8TQh";
    private static String OAUTH_CLIENT_SECRET = "ym_wFloTHQ";
    private static String OAUTH_CLIENT_NAME = "네이버 아이디로 로그인";

    private static OAuthLogin mOAuthLoginInstance;
    private static Context mContext;


    /**
     * UI 요소들
     */
    private TextView mApiResultText;
    private static TextView mOauthAT;
    private static TextView mOauthRT;
    private static TextView mOauthExpires;

    private static TextView mOauthTokenType;
    private static TextView mOAuthState;

    /** 로그인 요소 **/
    private OAuthLoginButton mOAuthLoginButton;
    private ServiceApi service;
    //private SessionCallback sessionCallback = new SessionCallback();
    Session session;



    /** 애니메이션 요소 **/
    Animation translateUp;


    /**
     * startOAuthLoginActivity() 호출시 인자로 넘기거나, OAuthLoginButton 에 등록해주면 인증이 종료되는 걸 알 수 있다.
     */
    @SuppressLint("HandlerLeak")
    private OAuthLoginHandler mOAuthLoginHandler = new OAuthLoginHandler() {
        @Override
        public void run(boolean success) {
            if (success) {
                String accessToken = mOAuthLoginInstance.getAccessToken(mContext);
                String refreshToken = mOAuthLoginInstance.getRefreshToken(mContext);
                long expiresAt = mOAuthLoginInstance.getExpiresAt(mContext);
                String tokenType = mOAuthLoginInstance.getTokenType(mContext);
                new RequestApiTask().execute();

            } else {
                String errorCode = mOAuthLoginInstance.getLastErrorCode(mContext).getCode();
                String errorDesc = mOAuthLoginInstance.getLastErrorDesc(mContext);
                Toast.makeText(mContext, "errorCode:" + errorCode + ", errorDesc:" + errorDesc, Toast.LENGTH_SHORT).show();
            }
        }

    };



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setStatusBar();// 상태바 색상 설정

        String getlogoutData = getIntent().getStringExtra("logout");
        Log.i("모은 ","getlogoutData :"+ getlogoutData);

        if(getlogoutData!=null && getlogoutData.equals("naver"))
        {
            naverLogout();
        }
        else if(getlogoutData!=null && getlogoutData.equals("kakao"))
        {
            kakaoLogOut();
        }

        String getwithdrawalData = getIntent().getStringExtra("withdrawal");
        Log.i("모은 ","getwithdrawalData :"+ getwithdrawalData);

        if(getwithdrawalData!=null && getwithdrawalData.equals("naver"))
        {
            Log.i("모은 ","DeleteTokenTask :"+ getwithdrawalData);

            new DeleteTokenTask().execute();
        }
        else if(getwithdrawalData!=null && getwithdrawalData.equals("kakao"))
        {
            SessionClose();
        }


            //카카오 로그인 데이터
            session = Session.getCurrentSession();
            session.addCallback(sessionCallback);


            //session.checkAndImplicitOpen();//로그인 세션을 열린채로 유지

            //네이버 로그인 데이터 & 뷰 초기화
            mContext = this;
            initData();
            initView();

            // 인공지능 데이터 가져오기


            //데이타 로딩 > 나중에 로딩 액티비티로 옮기기
            //onSaveAreaData();
            // onSaveTourListData();

            //애니메이션 설정
            setAnimation();

            // 버튼 이벤트 설정
            //findViewById(R.id.nextButton).setOnClickListener(btnClickListener);
            findViewById(R.id.buttonOAuthLoginImg).setOnClickListener(btnClickListener);
            findViewById(R.id.btn_kakao_login).setOnClickListener(btnClickListener);

    }




    /***** 카카오 로그인 *****/
    private ISessionCallback sessionCallback = new ISessionCallback() {
        @Override
        public void onSessionOpened() {
            Log.i("KAKAO_SESSION", "로그인 성공");
            requestMe();
        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            Log.e("KAKAO_SESSION", "로그인 실패", exception);
        }
        public void requestMe() {
            UserManagement.getInstance()
                    .me(new MeV2ResponseCallback() {
                        @Override
                        public void onSessionClosed(ErrorResult errorResult) {
                            Log.e("KAKAO_API", "세션이 닫혀 있음: " + errorResult);
                        }

                        @Override
                        public void onFailure(ErrorResult errorResult) {
                            Log.e("KAKAO_API", "사용자 정보 요청 실패: " + errorResult);
                        }

                        @Override
                        public void onSuccess(MeV2Response result) {
                            UserAccount kakaoAccount = result.getKakaoAccount();
                            Profile profile = kakaoAccount.getProfile();

                            String user_id;
                            String user_email = "null";
                            String user_age = "null";
                            String user_gender = "null";
                            String user_birthday = "null";
                            String user_name = "null";

                            user_id = Long.toString(result.getId());
                            Log.i("KAKAO_API", "사용자 아이디: " + result.getId());



                            //if-else 문으로 선택 처리
                            if (kakaoAccount.hasEmail() == OptionalBoolean.TRUE) {
                                Log.i("KAKAO_API", "사용자 이메일: " + result.getKakaoAccount().getEmail());
                                user_email = kakaoAccount.getEmail();
                            }

                            if (result.getKakaoAccount().hasAgeRange() == OptionalBoolean.TRUE) {
                                Log.i("KAKAO_API", "사용자 나이대: " + result.getKakaoAccount().getAgeRange().getValue());
                                user_age = kakaoAccount.getAgeRange().getValue();

                            }

                            if (result.getKakaoAccount().hasGender() == OptionalBoolean.TRUE) {
                                Log.i("KAKAO_API", "사용자 성별: " + result.getKakaoAccount().getGender().getValue());
                                user_gender = kakaoAccount.getGender().getValue();

                            }


                            if (result.getKakaoAccount().hasBirthday() == OptionalBoolean.TRUE) {
                                Log.i("KAKAO_API", "사용자 생일: " + result.getKakaoAccount().getBirthday());
                                user_birthday = kakaoAccount.getBirthday();

                            }


                            Log.i("KAKAO_API", "사용자 닉네임: " + result.getKakaoAccount().getProfile().getNickname());
                            user_name = kakaoAccount.getProfile().getNickname();


                            startLogin(new LoginData(user_id, user_age, user_gender, user_email, user_name, user_birthday,"kakao"));


                        }
                    });
        }


    };

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // 세션 콜백 삭제
        Session.getCurrentSession().removeCallback(sessionCallback);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // 카카오톡|스토리 간편로그인 실행 결과를 받아서 SDK로 전달
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {

            return;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public void kakaoLogOut() {
        UserManagement.getInstance()
                .requestLogout(new LogoutResponseCallback() {
                    @Override
                    public void onCompleteLogout() {
                        Log.i("KAKAO_API", "로그아웃 완료");
                    }
                });
    }

    protected void SessionClose() {
        UserManagement.getInstance()
                .requestUnlink(new UnLinkResponseCallback() {
                    @Override
                    public void onSessionClosed(ErrorResult errorResult) {
                        Log.e("KAKAO_API", "세션이 닫혀 있음: " + errorResult);
                    }

                    @Override
                    public void onFailure(ErrorResult errorResult) {
                        Log.e("KAKAO_API", "연결 끊기 실패: " + errorResult);

                    }

                    @Override
                    public void onSuccess(Long result) {
                        Log.i("KAKAO_API", "연결 끊기 성공. id: " + result);
                    }
                });
    }




    /***** 네이버 로그인 *****/
    private void initData() {
        mOAuthLoginInstance = OAuthLogin.getInstance();

        mOAuthLoginInstance.showDevelopersLog(true);
        mOAuthLoginInstance.init(mContext, OAUTH_CLIENT_ID, OAUTH_CLIENT_SECRET, OAUTH_CLIENT_NAME);

        /*
         * 2015년 8월 이전에 등록하고 앱 정보 갱신을 안한 경우 기존에 설정해준 callback intent url 을 넣어줘야 로그인하는데 문제가 안생긴다.
         * 2015년 8월 이후에 등록했거나 그 뒤에 앱 정보 갱신을 하면서 package name 을 넣어준 경우 callback intent url 을 생략해도 된다.
         */
        //mOAuthLoginInstance.init(mContext, OAUTH_CLIENT_ID, OAUTH_CLIENT_SECRET, OAUTH_CLIENT_NAME, OAUTH_callback_intent_url);
    }

    private void initView() {

        service = RetrofitClient.getClient().create(ServiceApi.class);
        mOAuthLoginButton = (OAuthLoginButton) findViewById(R.id.buttonOAuthLoginImg);
        mOAuthLoginButton.setOAuthLoginHandler(mOAuthLoginHandler);


    }

    public void naverLogout()
    {
        mOAuthLoginInstance.logout(mContext);
    }


    @Override
    protected void onResume() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        super.onResume();

    }





    private Button.OnClickListener btnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch(v.getId()) {


                case R.id.buttonOAuthLoginImg:


                   mOAuthLoginInstance.startOauthLoginActivity(LoginActivity.this, mOAuthLoginHandler);
                   Log.i("모은", " btnClickListener 네이버 로그인 버튼 누름");
//                   new RequestApiTask().execute();


                    break;


            }
        }
    };



    private class DeleteTokenTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            boolean isSuccessDeleteToken = mOAuthLoginInstance.logoutAndDeleteToken(mContext);

            if (!isSuccessDeleteToken) {
                // 서버에서 token 삭제에 실패했어도 클라이언트에 있는 token 은 삭제되어 로그아웃된 상태이다
                // 실패했어도 클라이언트 상에 token 정보가 없기 때문에 추가적으로 해줄 수 있는 것은 없음
                Log.d(TAG, "errorCode:" + mOAuthLoginInstance.getLastErrorCode(mContext));
                Log.d(TAG, "errorDesc:" + mOAuthLoginInstance.getLastErrorDesc(mContext));
            }

            return null;
        }

//        protected void onPostExecute(Void v) {
//            updateView();
//        }
    }

    private class RequestApiTask extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {
            String url = "https://openapi.naver.com/v1/nid/me";
            String at =  mOAuthLoginInstance.getAccessToken(mContext);;
            String data = mOAuthLoginInstance.requestApi(mContext, at, url);
            try {
                JSONObject result = new JSONObject(data);
                String id = result.getJSONObject("response").getString("id");
                String age = result.getJSONObject("response").getString("age");
                String gender = result.getJSONObject("response").getString("gender");
                String email = result.getJSONObject("response").getString("email");
                String name = result.getJSONObject("response").getString("name");
                String birthday = result.getJSONObject("response").getString("birthday");

                Log.i("모은", "requestApi");
                Log.i("id", id);
                Log.i("age", age);
                Log.i("gender", gender);
                Log.i("email", email);
                Log.i("name", name);
                Log.i("birthday", birthday);

                AppManager.getInstance().setUserName(name);

                startLogin(new LoginData(id, age, gender, email, name, birthday,"naver"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return mOAuthLoginInstance.requestApi(mContext, at, url);
        }

        protected void onPostExecute(String content) {
            super.onPostExecute(content);

        }
        @Override
        protected void onCancelled() {
            super.onCancelled();
        }


    }

    private class RefreshTokenTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            return mOAuthLoginInstance.refreshAccessToken(mContext);
        }

//        protected void onPostExecute(String res) {
//            updateView();
//        }
    }


    /***** 액티비티 꾸미기용 *****/
    private void setAnimation()
    {
        translateUp = AnimationUtils.loadAnimation(this, R.anim.slide_up_activity);
        LinearLayout mainLayout = (LinearLayout) findViewById(R.id.main_layout);
        mainLayout.startAnimation(translateUp);
        mainLayout.setVisibility(View.VISIBLE);
    }

    private void setStatusBar()
    {
        //상태 바 색 바꿔줌
        View view = getWindow().getDecorView();
        view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(Color.parseColor("#add8e6"));
        AppManager.getInstance().setLoginActivity(this);
    }


    /****** 서버에 데이터 보내기 *****/
    protected void startLogin(LoginData data) {

        service.userLogin(data).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse result = response.body();
                Toast.makeText(LoginActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();

                Log.i("모은", "startLogin 들어옴");
                AppManager.getInstance().setUserID(Integer.toString(result.getUserId()));

                ExecutorService es = Executors.newSingleThreadExecutor();
                es.submit(() -> onSaveLoginData(data.getUserSns()));
                es.submit(() -> goToNextActivity());
                es.shutdown();
//                onSaveLoginData();
//                goToNextActivity();
            }


            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                //Toast.makeText(LoginActivity.this, "로그인 에러 발생", Toast.LENGTH_SHORT).show();
                Log.e("모은 ", " 로그인 에러 발생"+t.getMessage());

            }
        });
    }


    private void onSaveLoginData(String userSns)
    {
        Log.i("모은","onSaveLoginData");
        SharedPreferences auto = getSharedPreferences("NPT",Activity.MODE_PRIVATE);
        //user.getString("user_id",null);
        SharedPreferences.Editor autoLogin = auto.edit();
        autoLogin.putString("user_id", AppManager.getInstance().getUserId());
        autoLogin.commit();
        autoLogin.putString("userSns",userSns);
        autoLogin.commit();
        AppManager.getInstance().setuserSns(auto.getString("userSns", ""));
        Log.i("모은","userSns(main) "+AppManager.getInstance().getuserSns());
    }

    public void goToNextActivity() {
        Log.i("모은","goToNextActivity");
        finish();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }






}
