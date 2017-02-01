package com.veryworks.android.runtimepermission;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * 리사이클러 뷰 구현 순서
 * 1. 홀더에 사용하는 위젯을 모두 정의한다.
 * 2. getItemCount 에 데이터 개수 전달
 * 3. onCreateViewHolder 에서 뷰 아이템 생성
 * 4. onBindViewHolder 를 통해 로직을 구현
 *
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.Holder> {

    ArrayList<Contact> datas;
    Context context;

    public RecyclerAdapter(ArrayList<Contact> datas, Context context) {
        this.datas = datas;
        this.context = context;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.contact_item, parent, false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        // 1. 데이터를 행 단위로 꺼낸다
        final Contact contact = datas.get(position);
        // 2. 홀더에 데이터를 세팅한다
        holder.txtId.setText(contact.getId() + "");
        holder.txtName.setText(contact.getName());
        holder.txtTel.setText(contact.getTelOne());

        // 3. 액션을 정의합니다. 리스너 종류를 세팅한다.
        holder.btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (context.checkSelfPermission(Manifest.permission.CALL_PHONE)
                            == PackageManager.PERMISSION_GRANTED) {
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + contact.getTelOne()));
                        context.startActivity(intent);
                    }
                } else {
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + contact.getTelOne()));
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView txtId,txtName,txtTel;
        ImageButton btnCall;

        public Holder(View itemView) {
            super(itemView);

            cardView = (CardView) itemView.findViewById(R.id.cardView);
            txtId = (TextView) itemView.findViewById(R.id.txtId);
            txtName = (TextView) itemView.findViewById(R.id.txtName);
            txtTel = (TextView) itemView.findViewById(R.id.txtTel);
            btnCall = (ImageButton) itemView.findViewById(R.id.btnCall);
        }
    }
}
