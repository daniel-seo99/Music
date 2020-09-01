package com.hjk.music_3.ui.activity;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Window;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.google.gson.Gson;
import com.hjk.music_3.R;
import com.hjk.music_3.data.local.model.User;
import com.hjk.music_3.data.remote.api.RetrofitService;
import com.hjk.music_3.data.remote.api.UserService;
import com.hjk.music_3.databinding.ActivityPasschBinding;
import com.hjk.music_3.ui.viewmodel.UserViewModel;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PassChangeActivity extends AppCompatActivity {

    UserService userService;
    ActivityPasschBinding binding;
    static UserViewModel userViewModel;
    User user;
    Gson gson=new Gson();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_passch);
        userService= RetrofitService.getRetro().create(UserService.class);
        binding.setActivity(this);
    }

    public void update(){
        if(!(UserViewModel.getUser().getValue().getPwd().trim().equals(binding.currentpass.getText().toString().trim()))){
            System.out.println(UserViewModel.getUser().getValue().getPwd());
            System.out.println(binding.currentpass.getText().toString());
            Toast.makeText(getApplicationContext(),"현재 비밀번호가 틀립니다.",Toast.LENGTH_SHORT).show();;
        }
        else{
            user=new User();
            user.setId(UserViewModel.getUser().getValue().getId());
            user.setPwd(binding.chPass.getText().toString());
            String objJson=gson.toJson(user);
            System.out.println(objJson);
            Call<ResponseBody> update=userService.pass_change(objJson);
            update.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try{
                        Toast.makeText(getApplicationContext(),"비밀번호가 변경 되었습니다",Toast.LENGTH_SHORT).show();

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(getApplicationContext(),"변경 실패",Toast.LENGTH_SHORT).show();
                }
            });
        }
     }
}
