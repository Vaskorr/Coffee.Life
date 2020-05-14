package com.example.coffeelife;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Registration extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText remail;                                   // ПОЛЕ ВВОДА EMAIL
    private EditText rpass;                                    // ПОЛЕ ВВОДА ПРАОЛЯ
    private EditText rpass2;                                   // ПОЛЕ ПОВТОРА ПАРОЛЯ
    private EditText name;                                     // ПОЛЕ ВВОДА ИМЕНИ
    private EditText lastName;                                 // ПОЛЕ ВВОДА ФАМИЛИИ
    private EditText thirdName;                                // ПОЛЕ ВВВОДА ОТЧЕСТВА
    private EditText birthday;                                 // ДЕНЬ РОЖДЕНИЯ
    private TextView errorlog;                                 // ОШИБКА ВВОДА
    private FirebaseUser user;                                 // ПОЛЬЗОВАТЕЛЬ
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.register_layout);
    }
    public void Register(View view){
        mAuth = FirebaseAuth.getInstance();
        remail = findViewById(R.id.remail);
        rpass = findViewById(R.id.rpass);
        rpass2 = findViewById(R.id.rpass2);
        name = findViewById(R.id.name);
        lastName = findViewById(R.id.lastname);
        thirdName = findViewById(R.id.thirdname);
        birthday = findViewById(R.id.birth);
        errorlog = findViewById(R.id.errorlog);
        errorlog.setText("Все поля дожны быть заполнены.");
        //if(remail.getText().toString().equals("") && rpass.getText().toString().equals("") &&
        //        rpass2.getText().toString().equals("") && name.getText().toString().equals("") &&
        //        lastName.getText().toString().equals("") && thirdName.getText().toString().equals("") &&
        //        birthday.getText().toString().equals("")){
        //
        //}else {
        //    errorlog.setText("Все поля дожны быть заполнены.");
        //}
        if(rpass.getText().toString().equals(rpass2.getText().toString())){
            errorlog.setText("");
            mAuth.createUserWithEmailAndPassword(remail.getText().toString(), rpass.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Toast.makeText(Registration.this, "Registration success!",
                                        Toast.LENGTH_SHORT).show();
                                user = mAuth.getCurrentUser();
                                ArrayList<String> list = new ArrayList<>();
                                list.add(lastName.getText().toString());
                                list.add(name.getText().toString());
                                list.add(thirdName.getText().toString());
                                list.add("0");
                                list.add(birthday.getText().toString());
                                SharedPreferences myPreferences
                                        = PreferenceManager.getDefaultSharedPreferences(Registration.this);
                                SharedPreferences.Editor myEditor = myPreferences.edit();
                                myEditor.putString("login", remail.getText().toString());
                                myEditor.putString("pass", rpass.getText().toString());
                                for (Integer i = 0; i < 5; i++){
                                    myEditor.putString(i.toString(), list.get(i));
                                    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference()
                                            .child(user.getUid()).child("UserData").child(i.toString());
                                    myRef.setValue(list.get(i));
                                }
                                myEditor.apply();
                                Finish();
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(Registration.this, "Registration failed.",
                                        Toast.LENGTH_SHORT).show();
                                errorlog.setText("Пароль может содержать только цифры и латинские буквы.");
                            }
                        }
                    });
            //this.finish();
        }
        else {
            errorlog.setText("Пароли не совпадают!");
        }
    }                       // РЕГИСТРАЦИЯ ПОЛЬЗОВАТЕЛЯ
    public void Finish(){
        this.finish();
    }                    // ЗАВЕРШЕНИЕ АКТИВНОСТИ
}
