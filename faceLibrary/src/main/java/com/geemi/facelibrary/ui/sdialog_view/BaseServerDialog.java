package com.geemi.facelibrary.ui.sdialog_view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.geemi.facelibrary.R;
import com.geemi.facelibrary.utils.shared.ShareStorage;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Thisfeng on 2017/3/9 0009 20:44
 */

public abstract class BaseServerDialog implements View.OnClickListener {


    private static ShareStorage storage;
    private static final String serverSP = "serverSP";
    private static final String SERVER_LIST = "serverList";

    /**
     * 创建一个SharedPreferences存储 服务器地址
     */
    private static ShareStorage getStorage(Context context) {
        if (storage == null) {
            storage = new ShareStorage(context, serverSP);
        }
        return storage;
    }

    private Context context;
    private AlertDialog dialog;
    private AlertDialog.Builder builder;
    private LinearLayout layout;
    private TextInputLayout inputLayout;
    private RecyclerView recyclerView;
    private List<String> serverList;
    private Gson gson = new Gson();
    public BaseServerDialog(final Context context) {
        this.context = context;
        builder = new AlertDialog.Builder(context);
        layout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.dialog_base_server, null);
        inputLayout = (TextInputLayout) layout.findViewById(R.id.tl);
        inputLayout.getEditText().setText(getServerUrl());//手动设置的地址

        recyclerView = (RecyclerView) layout.findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        serverList = new ArrayList<>();
        String list = getStorage(context).getString(SERVER_LIST);//取得SP中的服务器list数据
        //如果list为空就添加,不为空则将sp中的list数据解析成Array用于给adapter设置数据
        if (list.isEmpty()) {
            serverList.addAll(defaultServerUrlList());
        } else {
            //这里我在项目中用的是fastJson为了简单直接用它转换为list
            serverList = gson.fromJson(getStorage(context).getString(SERVER_LIST), new TypeToken<List<String>>() {}.getType());
//            serverList = JSONArray.parseArray(getStorage(context).getString(SERVER_LIST), String.class);
        }
        ServerDialogAdapter adapter = new ServerDialogAdapter(serverList, (view, position, url) -> {
            setServerUrl(url);
            dismiss();
            Toast.makeText(context, "设置更换地址成功,请结束应用重新开启", Toast.LENGTH_SHORT).show();
        });
        recyclerView.setAdapter(adapter);


        Button btnNext = layout.findViewById(R.id.btn);
        btnNext.setOnClickListener(this);

    }

    /**
     * 手动输入时将此输入地址 添加进本地SP存储
     */
    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.btn) {
            String string = inputLayout.getEditText().getText().toString().trim();
            if (!string.isEmpty()) {
                String url = inputLayout.getEditText().getText().toString().trim();
                //如果不包含添加进来list首位
                if (!serverList.contains(url)) {
                    serverList.add(0, url);
                    //这里我用的是fastJson将array转换成string存
                    getStorage(context).put(SERVER_LIST, gson.toJson(serverList));
                }
                setServerUrl(url);//设置输入的服务器地址
                Toast.makeText(context, "设置更换地址成功,请结束应用重新开启", Toast.LENGTH_SHORT).show();
            }
            dismiss();

        }
    }

    /**
     * use this method to show dialog, do not use getBuilder to show
     */
    public void show() {
        if (dialog == null) {
            dialog = builder.create();
            dialog.setCanceledOnTouchOutside(false);//点击外部是否消失
        }
        dialog.show();
        dialog.getWindow().setContentView(layout);
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    public void dismiss() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    protected abstract void setServerUrl(String selectedUrl);

    protected abstract String getServerUrl();

    protected abstract List<String> defaultServerUrlList();
}
