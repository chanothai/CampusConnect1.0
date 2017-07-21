package profilemof.zicure.company.com.profilemof.networks;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import gallery.zicure.company.com.modellibrary.models.BaseResponse;
import gallery.zicure.company.com.modellibrary.models.DataModel;
import gallery.zicure.company.com.modellibrary.utilize.EventBusCart;
import gallery.zicure.company.com.modellibrary.utilize.VariableConnect;
import profilemof.zicure.company.com.profilemof.interfaces.LogApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by BallOmO on 10/13/2016 AD.
 */
public class ClientHttp {
    private Context context = null;
    private static ClientHttp me;

    private Retrofit retrofit = null;
    private LogApi service = null;
    private Gson gson = null;

    public ClientHttp(Context context){
        this.context = context;
        String urlIdentityServer = "http://api.psp.pakgon.com/";
        retrofit = RetrofitAPI.newInstance(urlIdentityServer).getRetrofit();
        service = retrofit.create(LogApi.class);
        gson = new GsonBuilder().disableHtmlEscaping().create();
    }

    public static ClientHttp getInstance(Context context){
        if (me == null){
            me = new ClientHttp(context);
        }
        return me;
    }


    public void approveDevice(DataModel dataModel){
        Log.d("Model", gson.toJson(dataModel));
        Call<BaseResponse> approve = service.approveDevice(dataModel);
        approve.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                try{
                    EventBusCart.getInstance().getEventBus().post(response.body());
                }catch (NullPointerException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
