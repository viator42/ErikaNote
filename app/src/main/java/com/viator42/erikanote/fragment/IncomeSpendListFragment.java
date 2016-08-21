package com.viator42.erikanote.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.viator42.erikanote.AppContext;
import com.viator42.erikanote.R;
import com.viator42.erikanote.action.IncomeSpendAction;
import com.viator42.erikanote.adapter.IncomeSpendAdapter;
import com.viator42.erikanote.model.IncomeSpend;
import com.viator42.erikanote.utils.CommonUtils;
import com.viator42.erikanote.utils.StaticValues;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link IncomeSpendListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link IncomeSpendListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IncomeSpendListFragment extends Fragment {
    private OnFragmentInteractionListener mListener;
    private RecyclerView listView;
    private LinearLayoutManager layoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;
    private IncomeSpendAdapter incomeSpendAdapter;
    private AppContext appContext;
    private ArrayList<IncomeSpend> incomeSpends;
    private ArrayList<Map<String,Object>> listData = null;
    private int type;
    private int currentCount = 0;
    private AdapterView.AdapterContextMenuInfo menuInfo;
    private ViewGroup warningLayout;

    public IncomeSpendListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     */
    // TODO: Rename and change types and number of parameters
    public static IncomeSpendListFragment newInstance(int param1) {
        IncomeSpendListFragment fragment = new IncomeSpendListFragment();
        Bundle args = new Bundle();
        args.putInt("type", param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appContext = (AppContext) getActivity().getApplicationContext();
        if (getArguments() != null) {
            type = getArguments().getInt("type");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_income, container, false);
        listView = (RecyclerView) view.findViewById(R.id.list_view);
        layoutManager = new LinearLayoutManager(getActivity());
        listView.setLayoutManager(layoutManager);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                reload();
            }
        });
        warningLayout = (ViewGroup) view.findViewById(R.id.warning);
        warningLayout.setVisibility(View.GONE);

        reload();

        return view;
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

    private void reload()
    {
        listView.removeAllViewsInLayout();
        listData = null;

        load();
    }

    public void load()
    {
        IncomeSpend params = new IncomeSpend();
        params.incomeSpend = type;
        params.min = currentCount;
        params.max = StaticValues.PAGE_COUNT;

        incomeSpends = new IncomeSpendAction().list(appContext.eDbHelper, params);
        swipeRefreshLayout.setRefreshing(false);
        if(listData == null)
        {
            listData = new ArrayList<Map<String,Object>>();
        }
        if(!incomeSpends.isEmpty())
        {
            warningLayout.setVisibility(View.GONE);
        }
        else
        {
            warningLayout.setVisibility(View.VISIBLE);
        }

        for (IncomeSpend incomeSpend: incomeSpends)
        {
            Map line = new HashMap();

            line.put("id", incomeSpend.id);
            line.put("obj", incomeSpend);
//            line.put("name", incomeSpend.name);
//            line.put("type", incomeSpend.type);
//            line.put("incomeSpend", incomeSpend.getIncomeSpendName());
//            line.put("money", incomeSpend.money);
//            line.put("createTime", CommonUtils.timestampToDatetime(incomeSpend.createTime));
//            line.put("comment", incomeSpend.comment);

            listData.add(line);
        }
        if(incomeSpendAdapter == null)
        {
            incomeSpendAdapter = new IncomeSpendAdapter(getActivity(), listData);
            listView.setAdapter(incomeSpendAdapter);

        }
        else
        {
            incomeSpendAdapter.notifyDataSetChanged();
        }

    }

}
