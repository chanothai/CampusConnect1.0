package profilemof.zicure.company.com.profilemof.interfaces;

import java.util.Map;

import gallery.zicure.company.com.modellibrary.models.BaseResponse;
import gallery.zicure.company.com.modellibrary.models.DataModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * Created by BallOmO on 10/11/2016 AD.
 */
public interface LogApi {
    @POST("Api/secure/approveDevice.json")
    Call<BaseResponse> approveDevice(@Body DataModel dataModel);
}
