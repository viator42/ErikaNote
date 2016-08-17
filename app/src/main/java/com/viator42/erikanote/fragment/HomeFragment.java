package com.viator42.erikanote.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.viator42.erikanote.AppContext;
import com.viator42.erikanote.R;
import com.viator42.erikanote.action.IncomeSpendAction;
import com.viator42.erikanote.activity.InsertIncomeSpendActivity;
import com.viator42.erikanote.model.Statistics;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    private OnFragmentInteractionListener mListener;
    private ViewGroup incomeStatisticsCotainer;
    private ViewGroup spendStatisticsCotainer;
    private TextView incomeTodayTextView;
    private TextView incomeWeeklyTextView;
    private TextView incomeMonthTextView;
    private TextView spendTodayTextView;
    private TextView spendWeeklyTextView;
    private TextView spendMonthTextView;
    private Button addBtn;
    private AppContext appContext;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appContext = (AppContext) getActivity().getApplicationContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        incomeStatisticsCotainer = (ViewGroup) view.findViewById(R.id.income_statistics);
        spendStatisticsCotainer = (ViewGroup) view.findViewById(R.id.spend_statistics);
        incomeTodayTextView = (TextView) view.findViewById(R.id.income_today);
        incomeWeeklyTextView = (TextView) view.findViewById(R.id.income_weekly);
        incomeMonthTextView = (TextView) view.findViewById(R.id.income_month);
        spendTodayTextView = (TextView) view.findViewById(R.id.spend_today);
        spendWeeklyTextView = (TextView) view.findViewById(R.id.spend_weekly);
        spendMonthTextView = (TextView) view.findViewById(R.id.spend_month);

        incomeStatisticsCotainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spendStatisticsCotainer.setVisibility(View.VISIBLE);
                incomeStatisticsCotainer.setVisibility(View.GONE);
            }
        });
        spendStatisticsCotainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incomeStatisticsCotainer.setVisibility(View.VISIBLE);
                spendStatisticsCotainer.setVisibility(View.GONE);
            }
        });

        addBtn = (Button) view.findViewById(R.id.add);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), InsertIncomeSpendActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        spendStatisticsCotainer.setVisibility(View.VISIBLE);
        incomeStatisticsCotainer.setVisibility(View.GONE);

        Statistics statistics = new IncomeSpendAction().statistics(appContext.eDbHelper);
        incomeTodayTextView.setText(String.valueOf(statistics.incomeToday));
        incomeWeeklyTextView.setText(String.valueOf(statistics.incomeWeekly));
        incomeMonthTextView.setText(String.valueOf(statistics.incomeMonthly));
        spendTodayTextView.setText(String.valueOf(statistics.spendToday));
        spendWeeklyTextView.setText(String.valueOf(statistics.spendWeekly));
        spendMonthTextView.setText(String.valueOf(statistics.spendMonthly));

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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
