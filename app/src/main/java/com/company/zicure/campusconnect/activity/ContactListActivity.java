package com.company.zicure.campusconnect.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.company.zicure.campusconnect.R;
import com.company.zicure.campusconnect.adapter.ListFriendAdapter;
import com.company.zicure.campusconnect.adapter.SwipeController;
import com.company.zicure.campusconnect.adapter.SwipeControllerActions;
import com.company.zicure.campusconnect.network.ClientHttp;
import com.company.zicure.campusconnect.utility.PermissionRequest;
import com.squareup.otto.Subscribe;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import gallery.zicure.company.com.modellibrary.common.BaseActivity;
import gallery.zicure.company.com.modellibrary.models.contact.RequestDeleteProfile;
import gallery.zicure.company.com.modellibrary.models.contact.ResponseContactList;
import gallery.zicure.company.com.modellibrary.models.contact.ResponseDeleteProfile;
import gallery.zicure.company.com.modellibrary.utilize.EventBusCart;
import gallery.zicure.company.com.modellibrary.utilize.ModelCart;
import gallery.zicure.company.com.modellibrary.utilize.ToolbarManager;
import gallery.zicure.company.com.modellibrary.utilize.VariableConnect;

import static android.support.v7.widget.helper.ItemTouchHelper.RIGHT;

public class ContactListActivity extends BaseActivity implements ListFriendAdapter.CallPhoneListener {

    /**Make: View **/
    @Bind(R.id.toolbar)
    Toolbar toolbarMenu;
    @Bind(R.id.contact_list)
    RecyclerView contactList;

    /** Make: properties **/
    private String token = null;
    private String mobileNo = null;
    private ListFriendAdapter adapter = null;
    public int positionContact = 0;
    private List<ResponseContactList.ResultContactList.ContactListData> contactListData = null;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_friend);
        ButterKnife.bind(this);
        EventBusCart.getInstance().getEventBus().register(this);
        setToolbar();

        contactList.setLayoutManager(new LinearLayoutManager(this));
        token = ModelCart.getInstance().getKeyModel().getToken();
    }

    private void loadData() {
        if (token != null) {
            showLoadingDialog();
            ClientHttp.getInstance(this).requestContactList(token);
            initSwipe();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    public void setToolbar() {
        if (Build.VERSION.SDK_INT >= 21) {
            ToolbarManager manager = new ToolbarManager(this);
            manager.setToolbar(toolbarMenu, null, getDrawable(R.drawable.back_screen), null);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBusCart.getInstance().getEventBus().unregister(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_friend, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_add_friend) {
            if (!new PermissionRequest(this).requestCamera()) {
                intentToAddFriend();
            }
        } else {
            finish();
            overridePendingTransition(R.anim.anim_scale_in, R.anim.anim_slide_out_right);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 112:
                intentToAddFriend();
                break;
            case 123:
                checkPermissionCallPhone();
                break;
            default:
                break;
        }
    }

    private void checkPermissionCallPhone(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mobileNo));
            startActivity(intent);
        }else{
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mobileNo));
            startActivity(intent);
        }
    }

    private void intentToAddFriend(){
        Bundle bundle = new Bundle();
        bundle.putString(VariableConnect.TITLE_CATEGORY, "Scan QR");
        bundle.putString(VariableConnect.PATH_BLOC, "");

        openActivity(BlocContentActivity.class, bundle);
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
    }

    /** Event **/
    @Subscribe
    public void onEventResponseContactList(ResponseContactList response){
        if (response.getResult().getSuccess().equalsIgnoreCase("OK")) {
            contactListData = response.getResult().getData();
            adapter = new ListFriendAdapter(this, contactListData, this);
            contactList.setAdapter(adapter);
            contactList.setItemAnimator(new DefaultItemAnimator());
        }

        dismissDialog();
    }

    @Subscribe
    public void onEventResponseDeleteContact(ResponseDeleteProfile response) {
        if (response.getResult().getSuccess().equalsIgnoreCase("OK")) {
            if (adapter != null) {
                adapter.contactList.remove(positionContact);
                adapter.notifyItemRemoved(positionContact);
                adapter.notifyItemRangeChanged(positionContact, adapter.getItemCount());
            }
        }

        dismissDialog();
    }
    /****/

    @Override
    public void getMobileNo(String mobile) {
        mobileNo = mobile;
        //Request Permission
        if (!new PermissionRequest(this).requestCallPhone()){
            checkPermissionCallPhone();
        }
    }

    private void initSwipe(){
        final SwipeController swipeController = new SwipeController(new SwipeControllerActions() {
            @Override
            public void onRightClicked(int position) {
                positionContact = position;

                RequestDeleteProfile request = new RequestDeleteProfile();
                RequestDeleteProfile.ContactDelete contact = new RequestDeleteProfile().new ContactDelete();
                contact.setContactID(Integer.toString(contactListData.get(position).getUserPersonal().getId()));
                contact.setToken(token);
                request.setContact(contact);

                showLoadingDialog();
                ClientHttp.getInstance(context).requestDeleteContact(request);
            }
        });

        ItemTouchHelper helper = new ItemTouchHelper(swipeController);
        helper.attachToRecyclerView(contactList);

        contactList.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                swipeController.onDraw(c);
            }
        });
    }
}
