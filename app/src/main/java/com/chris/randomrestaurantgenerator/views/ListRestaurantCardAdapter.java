package com.chris.randomrestaurantgenerator.views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chris.randomrestaurantgenerator.R;
import com.chris.randomrestaurantgenerator.models.Restaurant;
import com.chris.randomrestaurantgenerator.utils.SavedListHolder;
import com.squareup.picasso.Picasso;

/**
 * A RecyclerView Adapter for the savedList.
 */
public class ListRestaurantCardAdapter extends RecyclerView.Adapter<ListRestaurantCardAdapter.RestaurantViewHolder> {

    Context context;
    SavedListHolder savedListHolder;

    public ListRestaurantCardAdapter(Context con) {
        this.context = con;
        this.savedListHolder = SavedListHolder.getInstance();
    }

    @Override
    public RestaurantViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_list_restaurant_card, parent, false);

        return new RestaurantViewHolder(view);
    }

    public void remove(int index) {
        savedListHolder.getSavedList().get(index).setSaved(false);
        savedListHolder.getSavedList().remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public void onBindViewHolder(RestaurantViewHolder holder, int position) {

        Restaurant restaurant = savedListHolder.getSavedList().get(position);

        Picasso.with(context).load(restaurant.getThumbnailURL()).into(holder.thumbnail);
        Picasso.with(context).load(restaurant.getRatingImageURL()).into(holder.ratingImage);
        holder.nameOfRestaurant.setText(restaurant.getName());
        holder.categories.setText(restaurant.getCategories().toString()
                .replace("[", "").replace("]", "").trim());

        if (restaurant.getDeal().length() != 0) {
            holder.deals.setVisibility(View.VISIBLE);
            holder.deals.setText(restaurant.getDeal());
        } else
            holder.deals.setVisibility(View.GONE);

        holder.distanceAndReviewCount.setText(String.format("%d reviews | %.2f mi away",
                restaurant.getReviewCount(), restaurant.getDistance()));
    }

    @Override
    public int getItemCount() {
        return savedListHolder.getSavedList().size();
    }

    public class RestaurantViewHolder extends RecyclerView.ViewHolder {

        TextView nameOfRestaurant;
        ImageView ratingImage;
        ImageView thumbnail;
        TextView categories;
        TextView deals;
        TextView distanceAndReviewCount;

        public RestaurantViewHolder(View itemView) {
            super(itemView);

            nameOfRestaurant = (TextView) itemView.findViewById(R.id.listName);
            ratingImage = (ImageView) itemView.findViewById(R.id.listRating);
            thumbnail = (ImageView) itemView.findViewById(R.id.listThumbnail);
            categories = (TextView) itemView.findViewById(R.id.listCategories);
            deals = (TextView) itemView.findViewById(R.id.listDeals);
            distanceAndReviewCount = (TextView) itemView.findViewById(R.id.listDistanceAndReviewCount);
        }
    }
}
