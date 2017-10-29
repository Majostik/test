package ru.majo.test.ui.users;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import ru.majo.test.R;
import ru.majo.test.api.model.User;

/**
 * Created by Majo on 28.10.2017.
 */

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {

    private ArrayList<User> mUsers;
    private Context mContext;

    private OnUserClickListener mOnUserClickListener;

    public interface OnUserClickListener{
        void onUserClick(User user);
    }

    public UsersAdapter(Context context, ArrayList<User> users) {
        mContext = context;
        mUsers = users;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_users, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(mContext, mUsers.get(position));
        holder.mUserLayout.setOnClickListener(v -> {
            if (mOnUserClickListener!=null){
                mOnUserClickListener.onUserClick(mUsers.get(position));
            }
        });
    }

    public void setOnUserClickListener(OnUserClickListener onUserClickListener) {
        mOnUserClickListener = onUserClickListener;
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public void setUsers(ArrayList<User> users){
        mUsers = users;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        @Bind(R.id.item_user_layout)
        LinearLayout mUserLayout;
        @Bind(R.id.item_user_firstname_tv)
        TextView mUserFirstNameTextView;
        @Bind(R.id.item_user_lastname_tv)
        TextView mUserLastNameTextView;
        @Bind(R.id.item_user_email_tv)
        TextView mUserEmailTextView;
        @Bind(R.id.item_user_avatar_iv)
        ImageView mUserAvatarImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void bind(Context context, User user){
            mUserFirstNameTextView.setText(user.getFirstName());
            mUserLastNameTextView.setText(user.getLastName());
            mUserEmailTextView.setText(user.getEmail());
            if (user.getAvatarUrl()!=null && !user.getAvatarUrl().isEmpty()) {
                Picasso.with(context).load(user.getAvatarUrl()).into(mUserAvatarImageView);
            } else {
                mUserAvatarImageView.setImageDrawable(null);
            }
        }
    }
}
