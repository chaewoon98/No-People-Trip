package com.test.mosun.stamp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.test.mosun.MainActivity;
import com.test.mosun.R;
import com.test.mosun.home.Fragment_Home;
import com.test.mosun.utility.OnSingleClickListener;

//tour api 사용해서 코스 정보 띄우기 or description
public class DetailPopUpActivity extends AppCompatActivity {

    TextView distance1;
    String predictionNumber;
    TextView predictionNumberText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_popup);

        Intent secondIntent = getIntent();

        String distance = String.valueOf(secondIntent.getStringExtra("distance"));
        predictionNumber = String.valueOf(secondIntent.getStringExtra("predictionNumber"));
        String todayNumber = String.valueOf(secondIntent.getStringExtra("todayNumber"));

        Log.d("string", String.valueOf(secondIntent.getStringExtra("key")));


        //뒤로가기 선택 시
        ImageButton back_btn = (ImageButton)findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {  //뒤로가기 버튼 누르면 마이페이지(이전 페이지)로 돌아간다.

            @Override
            public void onClick(View v) {
                finish();
            }
        });

        TextView title = findViewById(R.id.popupTitle);
        ImageView imageView = findViewById(R.id.popupImage);
        distance1 = findViewById(R.id.popupdistance);
        TextView exception = findViewById(R.id.popupException);
        TextView today = findViewById(R.id.popupToday);
        TextView description = findViewById(R.id.popupdescription);
        predictionNumberText = findViewById(R.id.popupPredictNumber);

        if(secondIntent.getStringExtra("key").equals("국립중앙박물관")){
            title.setText("국립중앙박물관");
            imageView.setImageResource(R.drawable.kgoung);
            exception.setText("오늘 예상 총 사람 수 : " + predictionNumber+"명");
            today.setText("오늘 약"+todayNumber+"명 다녀감");
            setDistance(Double.valueOf(distance));
            description.setText("경복궁 설명");
            setPredictNumber(Double.valueOf(predictionNumber));
        } else if(secondIntent.getStringExtra("key").equals("서울시립미술관")){
            title.setText("서울시립미술관");
            imageView.setImageResource(R.drawable.kgoung);
            exception.setText("오늘 예상 총 사람 수" + predictionNumber+"명");
            today.setText("오늘 약"+todayNumber+"명 다녀감");
            setDistance(Double.valueOf(distance));
            description.setText("경복궁 설명");
            setPredictNumber(Double.valueOf(predictionNumber));
        } else if(secondIntent.getStringExtra("key").equals("서울대공원")){
            title.setText("서울대공원");
            imageView.setImageResource(R.drawable.kgoung);
            exception.setText("오늘 예상 총 사람 수" + predictionNumber+"명");
            today.setText("오늘 약"+todayNumber+"명 다녀감");
            setDistance(Double.valueOf(distance));
            description.setText("서울대공원 설명");
            setPredictNumber(Double.valueOf(predictionNumber));
        } else if(secondIntent.getStringExtra("key").equals("서대문형무소역사관")){
            title.setText("서대문형무소역사관");
            imageView.setImageResource(R.drawable.kgoung);
            exception.setText("오늘 예상 총 사람 수" + predictionNumber+"명");
            today.setText("오늘 약"+todayNumber+"명 다녀감");
            setDistance(Double.valueOf(distance));
            description.setText("경복궁 설명");
            setPredictNumber(Double.valueOf(predictionNumber));
        } else if(secondIntent.getStringExtra("key").equals("아쿠아리움")){
            title.setText("아쿠아리움");
            imageView.setImageResource(R.drawable.kgoung);
            exception.setText("오늘 예상 총 사람 수" + predictionNumber+"명");
            today.setText("오늘 약"+todayNumber+"명 다녀감");
            setDistance(Double.valueOf(distance));
            setPredictNumber(Double.valueOf(predictionNumber));
        } else if(secondIntent.getStringExtra("key").equals("경복궁")){
            title.setText("경복궁");
            imageView.setImageResource(R.drawable.kgoung);
            exception.setText("오늘 예상 총 사람 수 " + "5,201.12 "+"명");
            today.setText("오늘 약 : 1,442 명 다녀감");
            setDistance(Double.valueOf(distance));
            description.setText("경복궁은 대한민국 서울 세종로에 있는 조선 왕조의 법궁이다. 근정전을 중심으로 하고 있다. '경복은 시경에 나오는 말로 왕과 그 자손, 온 백성들이 태평성대의 큰 복을 누리기를 축원한다는 의미다.");
            setPredictNumber(Double.valueOf(predictionNumber));

        } else if(secondIntent.getStringExtra("key").equals("서울스카이")){
            title.setText("서울스카이");
            imageView.setImageResource(R.drawable.kgoung);
            exception.setText("오늘 예상 총 사람 수" + predictionNumber+"명");
            today.setText("오늘 약"+todayNumber+"명 다녀감");
            setDistance(Double.valueOf(distance));
            description.setText("서울스카이 설명");
            setPredictNumber(Double.valueOf(predictionNumber));
        } else if(secondIntent.getStringExtra("key").equals("창덕궁")){
            title.setText("창덕궁");
            imageView.setImageResource(R.drawable.kgoung);
            exception.setText("오늘 예상 총 사람 수" + predictionNumber+"명");
            today.setText("오늘 약"+todayNumber+"명 다녀감");
            setDistance(Double.valueOf(distance));
            description.setText("창덕궁 설명");
            setPredictNumber(Double.valueOf(predictionNumber));
        } else if(secondIntent.getStringExtra("key").equals("종묘")){
            title.setText("종묘");
            imageView.setImageResource(R.drawable.kgoung);
            exception.setText("오늘 예상 총 사람 수" + predictionNumber+"명");
            today.setText("오늘 약"+todayNumber+"명 다녀감");
            setDistance(Double.valueOf(distance));
            description.setText("종묘 설명");
            setPredictNumber(Double.valueOf(predictionNumber));
        } else if(secondIntent.getStringExtra("key").equals("세종대왕기념관")){
            title.setText("세종대왕기념관");
            imageView.setImageResource(R.drawable.kgoung);
            exception.setText("오늘 예상 총 사람 수" + predictionNumber+"명");
            today.setText("오늘 약"+todayNumber+"명 다녀감");
            setDistance(Double.valueOf(distance));
            description.setText("세종대왕기념관 설명");
            setPredictNumber(Double.valueOf(predictionNumber));
        } else if(secondIntent.getStringExtra("key").equals("덕수궁")){
            title.setText("덕수궁");
            imageView.setImageResource(R.drawable.kgoung);
            exception.setText("오늘 예상 총 사람 수" + predictionNumber+"명");
            today.setText("오늘 약"+todayNumber+"명 다녀감");
            setDistance(Double.valueOf(distance));
            description.setText("덕수궁 설명");
            setPredictNumber(Double.valueOf(predictionNumber));
        } else if(secondIntent.getStringExtra("key").equals("창경궁")){
            title.setText("창경궁");
            imageView.setImageResource(R.drawable.kgoung);
            exception.setText("오늘 예상 총 사람 수" + predictionNumber+"명");
            today.setText("오늘 약"+todayNumber+"명 다녀감");
            setDistance(Double.valueOf(distance));
            description.setText("창경궁 설명");
            setPredictNumber(Double.valueOf(predictionNumber));
        } else if(secondIntent.getStringExtra("key").equals("남산골한옥마을")){
            title.setText("남산골한옥마을");
            imageView.setImageResource(R.drawable.kgoung);
            exception.setText("오늘 예상 총 사람 수" + predictionNumber+"명");
            today.setText("오늘 약"+todayNumber+"명 다녀감");
            setDistance(Double.valueOf(distance));
            description.setText("남산골한옥마을 설명");
            setPredictNumber(Double.valueOf(predictionNumber));
        } else if(secondIntent.getStringExtra("key").equals("선릉")){
            title.setText("선릉");
            imageView.setImageResource(R.drawable.kgoung);
            exception.setText("오늘 예상 총 사람 수" + predictionNumber+"명");
            today.setText("오늘 약"+todayNumber+"명 다녀감");
            setDistance(Double.valueOf(distance));
            description.setText("선릉 설명");
            setPredictNumber(Double.valueOf(predictionNumber));
        } else if(secondIntent.getStringExtra("key").equals("태릉")){
            title.setText("태릉");
            imageView.setImageResource(R.drawable.kgoung);
            exception.setText("오늘 예상 총 사람 수" + predictionNumber+"명");
            today.setText("오늘 약"+todayNumber+"명 다녀감");
            setDistance(Double.valueOf(distance));
            description.setText("태릉 설명");
            setPredictNumber(Double.valueOf(predictionNumber));
        } else if(secondIntent.getStringExtra("key").equals("정릉")){
            title.setText("정릉");
            imageView.setImageResource(R.drawable.kgoung);
            exception.setText("오늘 예상 총 사람 수" + predictionNumber+"명");
            today.setText("오늘 약"+todayNumber+"명 다녀감");
            setDistance(Double.valueOf(distance));
            description.setText("정릉 설명");
            setPredictNumber(Double.valueOf(predictionNumber));
        } else if(secondIntent.getStringExtra("key").equals("의릉")){
            title.setText("의릉");
            imageView.setImageResource(R.drawable.kgoung);
            exception.setText("오늘 예상 총 사람 수" + predictionNumber+"명");
            today.setText("오늘 약"+todayNumber+"명 다녀감");
            setDistance(Double.valueOf(distance));
            description.setText("의릉 설명");
            setPredictNumber(Double.valueOf(predictionNumber));
        } else if(secondIntent.getStringExtra("key").equals("헌릉")){
            title.setText("헌릉");
            imageView.setImageResource(R.drawable.kgoung);
            exception.setText("오늘 예상 총 사람 수" + predictionNumber+"명");
            today.setText("오늘 약"+todayNumber+"명 다녀감");
            setDistance(Double.valueOf(distance));
            description.setText("헌릉 설명");
            setPredictNumber(Double.valueOf(predictionNumber));
        }else if(secondIntent.getStringExtra("key").equals("영희원")){
            title.setText("영희원");
            imageView.setImageResource(R.drawable.kgoung);
            exception.setText("오늘 예상 총 사람 수" + predictionNumber+"명");
            today.setText("오늘 약"+todayNumber+"명 다녀감");
            setDistance(Double.valueOf(distance));
            description.setText("영희원 설명");
            setPredictNumber(Double.valueOf(predictionNumber));
        }
        else if(secondIntent.getStringExtra("key").equals("경기전")){
            title.setText("경기전");
            imageView.setImageResource(R.drawable.kgoung);
            exception.setText("오늘 예상 총 사람 수" + predictionNumber+"명");
            today.setText("오늘 약"+todayNumber+"명 다녀감");
            setDistance(Double.valueOf(distance));
            description.setText("경기전 설명");
            setPredictNumber(Double.valueOf(predictionNumber));
        } else if(secondIntent.getStringExtra("key").equals("국립전주박물관")){
            title.setText("국립전주박물관");
            imageView.setImageResource(R.drawable.kgoung);
            exception.setText("오늘 예상 총 사람 수" + predictionNumber+"명");
            today.setText("오늘 약"+todayNumber+"명 다녀감");
            setDistance(Double.valueOf(distance));
            description.setText("국립전주박물관 설명");
            setPredictNumber(Double.valueOf(predictionNumber));
        } else if(secondIntent.getStringExtra("key").equals("스파라쿠아")){
            title.setText("스파라쿠아");
            imageView.setImageResource(R.drawable.kgoung);
            exception.setText("오늘 예상 총 사람 수" + predictionNumber+"명");
            today.setText("오늘 약"+todayNumber+"명 다녀감");
            setDistance(Double.valueOf(distance));
            description.setText("스파라쿠아 설명");
            setPredictNumber(Double.valueOf(predictionNumber));
        } else if(secondIntent.getStringExtra("key").equals("전주 동물원")){
            title.setText("전주 동물원");
            imageView.setImageResource(R.drawable.kgoung);
            exception.setText("오늘 예상 총 사람 수" + predictionNumber+"명");
            today.setText("오늘 약"+todayNumber+"명 다녀감");
            setDistance(Double.valueOf(distance));
            description.setText("전주 동물원 설명");
            setPredictNumber(Double.valueOf(predictionNumber));
        } else if(secondIntent.getStringExtra("key").equals("한벽문화관")){
            title.setText("한벽문화관");
            imageView.setImageResource(R.drawable.kgoung);
            exception.setText("오늘 예상 총 사람 수" + predictionNumber+"명");
            today.setText("오늘 약"+todayNumber+"명 다녀감");
            setDistance(Double.valueOf(distance));
            description.setText("한벽문화관 설명");
            setPredictNumber(Double.valueOf(predictionNumber));
        } else if(secondIntent.getStringExtra("key").equals("한옥레일바이크")){
            title.setText("한옥레일바이크");
            imageView.setImageResource(R.drawable.kgoung);
            exception.setText("오늘 예상 총 사람 수" + predictionNumber+"명");
            today.setText("오늘 약"+todayNumber+"명 다녀감");
            setDistance(Double.valueOf(distance));
            setPredictNumber(Double.valueOf(predictionNumber));
            description.setText("한옥레일바이크 설명");
        } else if(secondIntent.getStringExtra("key").equals("한국도로공사수목원")){
            title.setText("한국도로공사수목원");
            imageView.setImageResource(R.drawable.kgoung);
            exception.setText("오늘 예상 총 사람 수" + predictionNumber+"명");
            today.setText("오늘 약"+todayNumber+"명 다녀감");
            setDistance(Double.valueOf(distance));
            setPredictNumber(Double.valueOf(predictionNumber));
            description.setText("한국도로공사수목원 설명");
        } else if(secondIntent.getStringExtra("key").equals("강천산")){
            title.setText("강천산");
            imageView.setImageResource(R.drawable.kgoung);
            exception.setText("오늘 예상 총 사람 수" + predictionNumber+"명");
            today.setText("오늘 약"+todayNumber+"명 다녀감");
            setDistance(Double.valueOf(distance));
            setPredictNumber(Double.valueOf(predictionNumber));
            description.setText("강천산 설명");
        } else if(secondIntent.getStringExtra("key").equals("황토열매마을")){
            title.setText("황토열매마을");
            imageView.setImageResource(R.drawable.kgoung);
            exception.setText("오늘 예상 총 사람 수" + predictionNumber+"명");
            today.setText("오늘 약"+todayNumber+"명 다녀감");
            setDistance(Double.valueOf(distance));
            setPredictNumber(Double.valueOf(predictionNumber));
            description.setText("황토열매마을 설명");
        } else if(secondIntent.getStringExtra("key").equals("고추장익는마을")){
            title.setText("고추장익는마을");
            imageView.setImageResource(R.drawable.kgoung);
            exception.setText("오늘 예상 총 사람 수" + predictionNumber+"명");
            today.setText("오늘 약"+todayNumber+"명 다녀감");
            setDistance(Double.valueOf(distance));
            description.setText("고추장익는마을 설명");
            setPredictNumber(Double.valueOf(predictionNumber));
        } else if(secondIntent.getStringExtra("key").equals("장류체험관")){
            title.setText("장류체험관");
            imageView.setImageResource(R.drawable.kgoung);
            exception.setText("오늘 예상 총 사람 수" + predictionNumber+"명");
            today.setText("오늘 약"+todayNumber+"명 다녀감");
            setDistance(Double.valueOf(distance));
            description.setText("장류체험관 설명");
            setPredictNumber(Double.valueOf(predictionNumber));
        } else if(secondIntent.getStringExtra("key").equals("산림박물관")){
            title.setText("산림박물관");
            imageView.setImageResource(R.drawable.kgoung);
            exception.setText("오늘 예상 총 사람 수" + predictionNumber+"명");
            today.setText("오늘 약 "+todayNumber+"명 다녀감");
            setDistance(Double.valueOf(distance));
            description.setText("산림박물관 설명");
            setPredictNumber(Double.valueOf(predictionNumber));
        } else if(secondIntent.getStringExtra("key").equals("발효소스토굴")){
            title.setText("발효소스토굴");
            imageView.setImageResource(R.drawable.kgoung);
            exception.setText("오늘 예상 총 사람 수 " + predictionNumber+"명");
            today.setText("오늘 약"+todayNumber+"명 다녀감");
            setDistance(Double.valueOf(distance));
            description.setText("발효소스토굴 설명");
            setPredictNumber(Double.valueOf(predictionNumber));
        }

    }

    public void setDistance(Double distance)
    {
        if(distance > 1000) {
            String distanceText = String.format("%.2f", distance/1000);
            distance1.setText("관광지까지의 거리 :"+distanceText + "km");
        }else{
            String distanceText = String.format("%.2f", distance);
            distance1.setText("관광지까지의 거리 :"+distanceText + "m");
        }
    }

    public void setPredictNumber(Double pridictionNumber){

        if(pridictionNumber >= 2000){
                predictionNumberText.setText("혼잡 예상");
            } else if(pridictionNumber <2000 && pridictionNumber > 500)
            {
                predictionNumberText.setText("보통 예상");
            }
        else {
            predictionNumberText.setText("원활 예상");
        }

    }

}