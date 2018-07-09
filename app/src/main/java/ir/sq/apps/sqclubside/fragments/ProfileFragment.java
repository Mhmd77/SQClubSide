package ir.sq.apps.sqclubside.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import ir.sq.apps.sqclubside.R;
import ir.sq.apps.sqclubside.activities.MapsActivity;
import ir.sq.apps.sqclubside.controllers.UrlHandler;
import ir.sq.apps.sqclubside.controllers.UserHandler;
import ir.sq.apps.sqclubside.models.Club;
import ir.sq.apps.sqclubside.uiControllers.ClubImageLoadingService;
import ir.sq.apps.sqclubside.uiControllers.TagsListAdapter;
import ir.sq.apps.sqclubside.uiControllers.TypeFaceHandler;
import ss.com.bannerslider.Slider;
import ss.com.bannerslider.adapters.SliderAdapter;
import ss.com.bannerslider.viewholder.ImageSlideViewHolder;

public class ProfileFragment extends Fragment {
    @BindView(R.id.imagesBannerSlider)
    Slider imagesBannerSlider;
    @BindView(R.id.imgClub)
    ImageView imgClub;
    @BindView(R.id.latoutStars)
    LinearLayout latoutStars;
    @BindView(R.id.txtClubPrice)
    TextView txtClubPrice;
    @BindView(R.id.txtClubName)
    TextView txtClubName;
    @BindView(R.id.txtClubAdress)
    TextView txtClubAdress;
    @BindView(R.id.club_tags_recyclerview)
    RecyclerView clubTagsRecyclerview;
    @BindView(R.id.btnReserve)
    Button btnReserve;
    Unbinder unbinder;
    private Club club;

    private OnFragmentInteractionListener mListener;

    public ProfileFragment() {
    }

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_club_profile, container, false);
        unbinder = ButterKnife.bind(this, view);
        club = UserHandler.getInstance().getmClub();
        setFonts();
        setFields(club);
        setSlider();
        return view;
    }

    private void setSlider() {
        Slider.init(new ClubImageLoadingService(getActivity()));
        imagesBannerSlider.setAdapter(new MySliderAdapter());
    }

    private void setFonts() {
        txtClubAdress.setTypeface(TypeFaceHandler.getInstance(getActivity()).getFa_light());
        txtClubName.setTypeface(TypeFaceHandler.getInstance(getActivity()).getFa_light());
        txtClubPrice.setTypeface(TypeFaceHandler.getInstance(getActivity()).getFa_light());
        btnReserve.setTypeface(TypeFaceHandler.getInstance(getActivity()).getFa_light());
    }

    public void setFields(Club club) {
        if (club.getImages().size() > 0) {
            imgClub.setImageBitmap(club.getImages().get(0));
        }
        txtClubName.setText(club.getName());
        txtClubAdress.setText(club.getAddress());
        txtClubPrice.setText("5000 تومان");
        setStars(4.5);
        setTags();
    }


    public void setTags() {
        Log.i("TAGS", club.getTags().size() + "");
        TagsListAdapter adapter = new TagsListAdapter(getActivity(), club.getTags(), new TagsListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }
        });
        clubTagsRecyclerview.setAdapter(adapter);
        clubTagsRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
    }

    public void setStars(double rate) {
        int t = (int) rate;
        for (int i = 0; i < t; i++) {
            ImageView img = new ImageView(getActivity());
            img.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            img.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_star_yellow_600_18dp));
            latoutStars.addView(img);
        }
        if (rate - t != 0) {
            ImageView img = new ImageView(getActivity());
            img.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            img.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_star_half_yellow_600_18dp));
            latoutStars.addView(img);
            t++;
        }
        for (int i = 0; i < 5 - t; i++) {
            ImageView img = new ImageView(getActivity());
            img.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            img.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_star_border_yellow_600_18dp));
            latoutStars.addView(img);
        }
    }

        @Override
        public void onAttach (Context context){
            super.onAttach(context);
            if (context instanceof OnFragmentInteractionListener) {
                mListener = (OnFragmentInteractionListener) context;
            } else {
                throw new RuntimeException(context.toString()
                        + " must implement OnFragmentInteractionListener");
            }
        }

        @Override
        public void onDetach () {
            super.onDetach();
            mListener = null;
        }

        public interface OnFragmentInteractionListener {
            void onFragmentInteraction(Uri uri);
        }
        @OnClick(R.id.btnLocation)
        public void onViewClicked() {
            startActivity(new Intent(getActivity(),MapsActivity.class));
        }

        private class MySliderAdapter extends SliderAdapter {

            @Override
            public int getItemCount() {
                return club.getNameImages().size();
            }

            @Override
            public void onBindImageSlide(int position, ImageSlideViewHolder viewHolder) {
                viewHolder.bindImageSlide(UrlHandler.getImageClubURL.getUrl() + club.getImageName(position));
            }
        }
    }
