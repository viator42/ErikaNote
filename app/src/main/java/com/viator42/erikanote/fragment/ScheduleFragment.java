package com.viator42.erikanote.fragment;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.viator42.erikanote.AppContext;
import com.viator42.erikanote.MainActivity;
import com.viator42.erikanote.R;
import com.viator42.erikanote.action.ScheduleAction;
import com.viator42.erikanote.activity.InsertScheduleActivity;
import com.viator42.erikanote.adapter.ScheduleAdapter;
import com.viator42.erikanote.model.Schedule;
import com.viator42.erikanote.receiver.ScheduleReceiver;
import com.viator42.erikanote.utils.StaticValues;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ScheduleFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ScheduleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScheduleFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private ViewGroup warningLayout;
    private ListView listView;
    private FloatingActionButton floatingActionButton;
    private ArrayList<Schedule> scheduleList;
    private AppContext appContext;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ScheduleAdapter scheduleAdapter;
    private List<Map<String, Object>> listData;

    public ScheduleFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ScheduleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ScheduleFragment newInstance(String param1, String param2) {
        ScheduleFragment fragment = new ScheduleFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
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
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);
        warningLayout = (ViewGroup) view.findViewById(R.id.warning);
        warningLayout.setVisibility(View.GONE);
        listView = (ListView) view.findViewById(R.id.list);

        registerForContextMenu(listView);
        listView.setOnCreateContextMenuListener(new ListView.OnCreateContextMenuListener(){
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v,
                                            ContextMenu.ContextMenuInfo menuInfo) {
                // TODO Auto-generated method stub

//                Toast.makeText(getActivity(), ""+menuInfo, Toast.LENGTH_LONG).show();;
                //添加菜单项
                menu.add(Menu.NONE, StaticValues.CONTEXT_MENU_ITEM_EDIT ,1 , "修改");
                menu.add(Menu.NONE, StaticValues.CONTEXT_MENU_ITEM_REMOVE ,2 , "删除");

            }

        });

        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                Intent intent = new Intent(getActivity(), InsertScheduleActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("actionType", StaticValues.ACTION_INSERT);
                intent.putExtras(bundle);
                startActivityForResult(intent, StaticValues.REQUEST_CODE_CREATE_SCHEDULE);

            }
        });

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                reload();

            }
        });

        MainActivity mainActivity = (MainActivity) getActivity();
        Toolbar toolbar = mainActivity.getToolbar();
        toolbar.setTitle(getResources().getString(R.string.nav_schedule));

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        reload();
    }

    public boolean onContextItemSelected(MenuItem item){
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final Schedule schedule = scheduleList.get(menuInfo.position);
        switch (item.getItemId())
        {
            case StaticValues.CONTEXT_MENU_ITEM_EDIT:
                Intent intent = new Intent(getActivity(), InsertScheduleActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("actionType", StaticValues.ACTION_UPDATE);
                bundle.putParcelable("schedule", schedule);
                intent.putExtras(bundle);
                startActivityForResult(intent, StaticValues.REQUEST_CODE_UPDATE_SCHEDULE);

                break;
            case StaticValues.CONTEXT_MENU_ITEM_REMOVE:
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("确认删除该提醒?");
                builder.setTitle("提示");
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();

                        if(new ScheduleAction().remove(appContext.eDbHelper, schedule.id))
                        {
                            Intent intent =  new Intent(getActivity(), ScheduleReceiver.class);
                            PendingIntent pi = PendingIntent.getBroadcast(getActivity(), (int) schedule.id, intent, PendingIntent.FLAG_ONE_SHOT);
                            AlarmManager am = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
                            am.cancel(pi);

                            reload();
                            Toast.makeText(getActivity(), "删除成功", Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toast.makeText(getActivity(), "删除失败", Toast.LENGTH_LONG).show();
                        }

                    }
                });
                builder.setNegativeButton("取消", null);
                builder.create().show();


                break;
        }
        return super.onContextItemSelected(item);

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
        if(listData != null) {
            listData.clear();
        }
        load();
    }

    private void load()
    {
        warningLayout.setVisibility(View.GONE);

        scheduleList = new ScheduleAction().list(appContext.eDbHelper);
        swipeRefreshLayout.setRefreshing(false);
        if(!scheduleList.isEmpty())
        {
            warningLayout.setVisibility(View.GONE);
        }
        else
        {
            warningLayout.setVisibility(View.VISIBLE);
        }
        if(listData == null)
        {
            listData  = new ArrayList<Map<String, Object>>();
        }
        for (Schedule schedule: scheduleList)
        {
            Map line = new HashMap();
            line.put("id", schedule.id);
            line.put("schedule", schedule);

            listData.add(line);
        }

        if(scheduleAdapter == null)
        {
            scheduleAdapter = new ScheduleAdapter(listData, getActivity());
            listView.setAdapter(scheduleAdapter);
        }
        else
        {
            scheduleAdapter.notifyDataSetChanged();
        }

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == StaticValues.REQUEST_CODE_CREATE_SCHEDULE) {
            reload();
        }
    }

}
