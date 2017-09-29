package vfpimenta.dungeonmasterstudio.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import vfpimenta.dungeonmasterstudio.R;

public class EncounterCalculatorFragment extends Fragment implements View.OnClickListener {

    public static final String ARG_PAGE = "ARG_PAGE";

    private int mPage;
    private View mView;

    public static EncounterCalculatorFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        EncounterCalculatorFragment fragment = new EncounterCalculatorFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
    }

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_encounter_calculator, container, false);

        LinearLayout playerContainer = view.findViewById(R.id.player_container);
        View playerView = inflater.inflate(R.layout.view_player_layout, null);
        playerContainer.addView(playerView);
        LinearLayout enemyContainer = view.findViewById(R.id.enemy_container);
        View enemyView = inflater.inflate(R.layout.view_enemy_layout, null);
        enemyContainer.addView(enemyView);

        ImageButton addPlayer = view.findViewById(R.id.add_player);
        addPlayer.setOnClickListener(this);
        ImageButton addEnemy = view.findViewById(R.id.add_enemy);
        addEnemy.setOnClickListener(this);

        setView(view);
        return view;
    }

    private void setView(View v){
        this.mView = v;
    }

    public void onClick(View v){
        switch (v.getId()) {
            case  R.id.add_player: {
                LinearLayout playerContainer = mView.findViewById(R.id.player_container);
                View playerView = LayoutInflater.from(mView.getContext()).inflate(R.layout.view_player_layout, null);
                playerContainer.addView(playerView);
                break;
            }

            case R.id.add_enemy: {
                LinearLayout enemyContainer = mView.findViewById(R.id.enemy_container);
                View enemyView = LayoutInflater.from(mView.getContext()).inflate(R.layout.view_enemy_layout, null);
                enemyContainer.addView(enemyView);
                break;
            }
        }
    }
}