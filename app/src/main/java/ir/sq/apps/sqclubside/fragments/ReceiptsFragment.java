package ir.sq.apps.sqclubside.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ir.sq.apps.sqclubside.R;
import ir.sq.apps.sqclubside.adapters.ReceiptAdapter;
import ir.sq.apps.sqclubside.controllers.UserHandler;
import ir.sq.apps.sqclubside.models.Receipt;
import ir.sq.apps.sqclubside.uiControllers.TypeFaceHandler;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ReceiptsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReceiptsFragment extends Fragment {
    Unbinder unbinder;
    @BindView(R.id.receipts_recycler_view)
    RecyclerView receiptsRecyclerView;
    @BindView(R.id.root_scene)
    FrameLayout rootScene;
    private ReceiptAdapter adapter;
    private OnFragmentInteractionListener mListener;
    private List<Receipt> items;
    private ReceiptAdapter.OnItemClickListener onClick;
    private Scene endScene;
    private Transition transition;
    private Receipt receipt;
    TextView txtClubName;
    TextView txtClubLoc;
    TextView txtClubDate;
    TextView txtClubTime;
    TextView txtClubPrice;

    public ReceiptsFragment() {
    }

    public static ReceiptsFragment newInstance() {
        return new ReceiptsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reciepts, container, false);
        unbinder = ButterKnife.bind(this, view);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            endScene = Scene.getSceneForLayout(rootScene, R.layout.layout_receipt, getContext());
            transition = TransitionInflater.from(getActivity()).inflateTransition(R.transition.custom_transition);
            endScene.setEnterAction(new Runnable() {
                @Override
                public void run() {
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                        View view = endScene.getSceneRoot();
                        findView(view);
                        setFonts();
                        setFields();
                    }
                }
            });
        }
        setItems();
        setOnClick();
        adapter = new ReceiptAdapter(getActivity(), items, onClick);
        receiptsRecyclerView.setAdapter(adapter);
        receiptsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        return view;
    }

    private void setOnClick() {
        onClick = new ReceiptAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                receipt = items.get(position);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    TransitionManager.go(endScene, transition);
                }
            }
        };
    }

    private void setFields() {
        txtClubName.setText(receipt.getClubName());
        txtClubPrice.setText(receipt.getPrice() + " " + getResources().getString(R.string.currency_string));
        txtClubLoc.setText(receipt.getClubAdress());
        txtClubDate.setText(receipt.getDate());
        txtClubTime.setText(receipt.getTime());
    }

    private void findView(View view) {
        txtClubName = view.findViewById(R.id.txtClubName);
        txtClubLoc = view.findViewById(R.id.txtClubLoc);
        txtClubDate = view.findViewById(R.id.txtClubDate);
        txtClubTime = view.findViewById(R.id.txtClubTime);
        txtClubPrice = view.findViewById(R.id.txtClubPrice);
    }

    private void setFonts() {
        txtClubName.setTypeface(TypeFaceHandler.getInstance(getActivity()).getFa_light());
        txtClubLoc.setTypeface(TypeFaceHandler.getInstance(getActivity()).getFa_light());
        txtClubDate.setTypeface(TypeFaceHandler.getInstance(getActivity()).getFa_light());
        txtClubTime.setTypeface(TypeFaceHandler.getInstance(getActivity()).getFa_light());
        txtClubPrice.setTypeface(TypeFaceHandler.getInstance(getActivity()).getFa_light());
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    private void setItems() {
        items = new ArrayList<>();
        Receipt r = new Receipt(50000, "1397/4/2", "از 15 تا 18");
        r.setClubAdress("آدررس باشگاه 1");
        r.setClubName("باشگاه نمونه 1");
        items.add(r);
        r = new Receipt(3000, "1397/5/15", "از 8 تا 10");
        r.setClubAdress("آدررس باشگاه 2");
        r.setClubName("باشگاه نمونه 2");
        items.add(r);
        items.addAll(UserHandler.getInstance().getThisUser().getReceipts());
    }
}
