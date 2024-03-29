package com.example.duan1_baove.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_baove.R;
import com.example.duan1_baove.database.DuAn1DataBase;
import com.example.duan1_baove.model.CuaHang;
import com.example.duan1_baove.model.ThietBi;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class CuaHangAdapter extends RecyclerView.Adapter<CuaHangAdapter.ViewHolder> implements Filterable {
    private Context context;
    private List<CuaHang> list;
    private List<CuaHang> listOld;

    NumberFormat numberFormat = new DecimalFormat("###,###,###");

    private EditText edt_id,edt_name,edt_gia,
            edt_tinhtrang,edt_soluong,
            edt_trongluong,edt_hangsanxuat;
    private Button btn_chonanh,btn_add,btn_huy;
    private ImageView img_cuahang;

    public CuaHangAdapter(Context context) {
        this.context = context;
    }
    public void setData(List<CuaHang> list){
        this.list = list;
        this.listOld = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cuahang,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CuaHang cuaHang = list.get(position);
        if (cuaHang !=null){
            holder.tv_id.setText("Mã món hàng: "+cuaHang.getId());
            holder.tv_name.setText("Tên món hàng: "+cuaHang.getName());
            holder.tv_gia.setText("Giá: "+numberFormat.format(cuaHang.getGia())+" vnđ");
            holder.tv_soluong.setText("Số lượng: "+cuaHang.getSoLuong());
            holder.tv_trongluong.setText("Trọng lượng: "+cuaHang.getTrongLuong()+ " kg");
            holder.tv_hangsanxuat.setText("Hãng sản xuất: "+cuaHang.getHangSanXuat());
            holder.tv_tinhtrang.setText("Tình trạng: "+cuaHang.getTinhTrang());
            if (cuaHang.getImg()==null){
                holder.img_cuahang.setImageResource(R.drawable.ic_account);
            }else {
                String linkimg = cuaHang.getImg();
                Log.d("adapter",linkimg+" link");
                holder.img_cuahang.setImageDrawable(Drawable.createFromPath(linkimg));
            }

            holder.layout_update.setOnLongClickListener(v -> {
                new AlertDialog.Builder(context).setTitle("Xoá món hàng ?")
                        .setMessage("Bạn có chắc chắn muốn xoá món hàng ?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DuAn1DataBase.getInstance(context).cuaHangDAO().delete(cuaHang);
                                list.remove(cuaHang);
                                notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("No",null)
                        .show();
                return true;
            });

            holder.layout_update.setOnClickListener(v -> {
                Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_addcuahang);
                dialog.show();
                Window window = dialog.getWindow();
                if (window == null){
                    return;
                }
                window.setBackgroundDrawable(null);
                edt_id = dialog.findViewById(R.id.edt_id_dialogcuahang);
                edt_name = dialog.findViewById(R.id.edt_ten_dialogcuahang);
                edt_gia = dialog.findViewById(R.id.edt_gia_dialogcuahang);
                edt_tinhtrang = dialog.findViewById(R.id.edt_tinhtrang_dialogcuahang);
                edt_soluong = dialog.findViewById(R.id.edt_soLuong_dialogcuahang);
                edt_trongluong = dialog.findViewById(R.id.edt_trongluong_dialogcuahang);
                edt_hangsanxuat = dialog.findViewById(R.id.edt_hangsanxuat_dialogcuahang);
                btn_chonanh = dialog.findViewById(R.id.btn_selectimage_dialogcuahang);
                btn_add = dialog.findViewById(R.id.btn_luu_dialogcuahang);
                btn_huy = dialog.findViewById(R.id.btn_huy_dialogcuahang);
                img_cuahang = dialog.findViewById(R.id.avt_dialogcuahang);

                edt_id.setText(cuaHang.getId()+"");
                edt_name.setText(cuaHang.getName());
                edt_gia.setText(cuaHang.getGia()+"");
                edt_soluong.setText(cuaHang.getSoLuong()+"");
                edt_trongluong.setText(cuaHang.getTrongLuong()+"");
                edt_hangsanxuat.setText(cuaHang.getHangSanXuat());
                edt_tinhtrang.setText(cuaHang.getTinhTrang());
                if (cuaHang.getImg()==null){
                    img_cuahang.setImageResource(R.drawable.ic_account);
                }else {
                    String linkimg = cuaHang.getImg();
                    Log.d("adapter",linkimg+" link");
                    img_cuahang.setImageDrawable(Drawable.createFromPath(linkimg));
                }

                btn_huy.setOnClickListener(v1 -> {
                    dialog.cancel();
                });
                btn_add.setOnClickListener(v1 -> {
                    if (validate()){
                        cuaHang.setName(edt_name.getText().toString().trim());
                        cuaHang.setGia(Integer.parseInt(edt_gia.getText().toString().trim()));
                        cuaHang.setSoLuong(Integer.parseInt(edt_soluong.getText().toString().trim()));
                        cuaHang.setTrongLuong(Integer.parseInt(edt_trongluong.getText().toString().trim()));
                        cuaHang.setHangSanXuat(edt_hangsanxuat.getText().toString().trim());
                        if (Integer.parseInt(edt_soluong.getText().toString().trim())>0){
                            cuaHang.setTinhTrang("Còn hàng");
                        }else {
                            cuaHang.setTinhTrang("Hết hàng");
                        }
                        DuAn1DataBase.getInstance(context).cuaHangDAO().insert(cuaHang);
                        Toast.makeText(context, "Update món thành công", Toast.LENGTH_SHORT).show();
                        notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });
            });
        }
    }

    @Override
    public int getItemCount() {
        if (list != null){
            return list.size();
        }
        return 0;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strSearch = constraint.toString();
                if (strSearch.isEmpty()){
                    list = listOld;
                }else {
                    List<CuaHang> listnew = new ArrayList<>();
                    for (CuaHang cuaHang:listOld){
                        if (cuaHang.getName().toLowerCase().contains(strSearch.toLowerCase())){
                            listnew.add(cuaHang);
                        }
                    }
                    list = listnew;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = list;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                list = (List<CuaHang>) results.values;
                notifyDataSetChanged();
            }
        };
    }
    private boolean validate(){
        if (edt_gia.getText().toString().trim().isEmpty() ||edt_trongluong.getText().toString().trim().isEmpty() || edt_name.getText().toString().trim().isEmpty() || edt_soluong.getText().toString().trim().isEmpty() || edt_hangsanxuat.getText().toString().trim().isEmpty()){
            Toast.makeText(context, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return false;
        }else {
            try {
                Integer.parseInt(edt_soluong.getText().toString().trim());
                Integer.parseInt(edt_gia.getText().toString().trim());
                Integer.parseInt(edt_trongluong.getText().toString().trim());
                return true;
            }catch (Exception e){
                Toast.makeText(context, "Định dạng không hợp lệ", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_id,tv_name,tv_gia,tv_soluong,tv_trongluong,tv_hangsanxuat,tv_tinhtrang;
        private ImageView img_cuahang;
        private RelativeLayout layout_update;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_id = itemView.findViewById(R.id.tv_id_itemcuahang);
            tv_name = itemView.findViewById(R.id.tv_name_itemcuahang);
            tv_gia = itemView.findViewById(R.id.tv_gia_itemcuahang);
            tv_soluong = itemView.findViewById(R.id.tv_soluong_itemcuahang);
            tv_trongluong = itemView.findViewById(R.id.tv_trongluong_itemcuahang);
            tv_hangsanxuat = itemView.findViewById(R.id.tv_hangsanxuat_itemcuahang);
            tv_tinhtrang = itemView.findViewById(R.id.tv_tinhtrang_itemcuahang);
            img_cuahang = itemView.findViewById(R.id.avt_itemcuahang);
            layout_update = itemView.findViewById(R.id.layout_update_itemcuahang);

        }
    }
}
