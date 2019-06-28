package hoangviet.ndhv.nfirebasekhoapham;

import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
DatabaseReference mdata;
TextView textView;
Button btnco,btnkhong;
ListView listViewitem;
ArrayList<String>arrayList;
ArrayAdapter adapter;
private FirebaseAuth mAuth;
Button btndangki,btnxnhandangki,btnHuy  ;
    EditText edtuser,edtpass,edtrepass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // ánh xạ
        textView = (TextView)findViewById(R.id.textviewthoi);
        btnco = (Button)findViewById(R.id.buttonyes);
        btnkhong = (Button)findViewById(R.id.buttonno);
        listViewitem = (ListView)findViewById(R.id.listitem);
        arrayList = new ArrayList<String>();
        btndangki = (Button)findViewById(R.id.buttondangki);
        adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,arrayList);
        listViewitem.setAdapter(adapter);
        // authenticate
        mAuth = FirebaseAuth.getInstance();
        btndangki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogLogin();
            }
        });





        mdata = FirebaseDatabase.getInstance().getReference();
        mdata.child("folderHinhAnh").child("HinhAnh2").setValue("HoangTinh");
        SinhVien sinhVien = new SinhVien("Hoang Viet","T140");
        mdata.child("trường học").setValue(sinhVien);
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("Hoàng Tình",56);
        mdata.child("trường học1").setValue(map);
//        mdata.child("demo").push().setValue(sinhVien, new DatabaseReference.CompletionListener() {
//            @Override
//            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
//                if (databaseError == null){
//                    Toast.makeText(MainActivity.this, "Thành công rồi bạn ơi", Toast.LENGTH_SHORT).show();
//                }else {
//                    Toast.makeText(MainActivity.this, "lỗi rồi nhé", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
        mdata.child("android").setValue("developer it's me");
        mdata.child("android").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String ten = dataSnapshot.getValue().toString();
                textView.setText(ten);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mdata.child("android").setValue("cố gắn sẽ thành công nhé Việt nhé");
            }
        });
        btnkhong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mdata.child("android").setValue("cố gắn một trăm phần trăm sức lực, cố gắn lên ");
            }
        });
        // lưu dữ và lấy dữ liệu khi push lên firebase
        mdata.child("demofirebase").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String tenone = dataSnapshot.getValue().toString();
                arrayList.add(tenone);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    // function tạo tài khoản
    private void uploadtaikhoan(){
        String email = edtuser.getText().toString() ;
        String password = edtpass.getText().toString();
        String re_password = edtrepass.getText().toString();
        if (password.equals(re_password)){
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "tạo tài khoản thành công", Toast.LENGTH_SHORT).show();
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(MainActivity.this, "Lỗi đăng kí!", Toast.LENGTH_SHORT).show();
                            }


                        }
                    });
        }else {
            Toast.makeText(this, "xác nhận mật khẩu không đúng, xin vui òng nhập lại!!!", Toast.LENGTH_SHORT).show();
        }
    }
    private void dialogLogin(){
        final Dialog  dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialogcustom);
        dialog.setCanceledOnTouchOutside(false);
        // ánh xạ trong dialog
        edtuser   = (EditText)dialog.findViewById(R.id.editTextuser);
        edtpass   = (EditText)dialog.findViewById(R.id.editTextpass);
        edtrepass = (EditText)dialog.findViewById(R.id.editTextpassxacnhan);
        btnxnhandangki         = (Button)dialog.findViewById(R.id.buttonxacnhandk);
        btnHuy = (Button)dialog.findViewById(R.id.buttonhuy);
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnxnhandangki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadtaikhoan();
            }
        });


        dialog.show();
    }
}
