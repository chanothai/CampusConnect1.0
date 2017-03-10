package profilemof.zicure.company.com.profilemof.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import profilemof.zicure.company.com.profilemof.R;

/**
 * Created by 4GRYZ52 on 3/4/2017.
 */

public abstract class UserDetailAdapter extends RecyclerView.Adapter<UserDetailAdapter.UserDetailHolder> {
    private List<String> listTopic = null;
    private List<String> listContent = null;

    public UserDetailAdapter(ArrayList<String> listTopic, ArrayList<String> listContent){
        this.listTopic = listTopic;
        this.listContent = listContent;
    }

    @Override
    public UserDetailHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_detail_user, null);

        UserDetailHolder userDetailHolder = new UserDetailHolder(view);
        return userDetailHolder;
    }

    public String getTopic(int position){
        return listTopic.get(position);
    }

    public String getContent(int position){
        return listContent.get(position);
    }

    @Override
    public int getItemCount() {
        return listTopic.size();
    }

    public class UserDetailHolder extends RecyclerView.ViewHolder{
        public TextView topic, content;

        public UserDetailHolder(View itemView) {
            super(itemView);
            topic = (TextView) itemView.findViewById(R.id.topic_detail);
            content = (TextView) itemView.findViewById(R.id.content_detail);
        }
    }
}
