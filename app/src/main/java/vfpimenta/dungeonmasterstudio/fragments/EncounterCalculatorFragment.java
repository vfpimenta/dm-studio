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

import java.util.ArrayList;
import java.util.List;

import vfpimenta.dungeonmasterstudio.R;

public class EncounterCalculatorFragment extends Fragment implements View.OnClickListener {

    private enum Opt{
        Player, Enemy
    }

    public static final String ARG_PAGE = "ARG_PAGE";

    private int mPage;
    private View mView;

    private static List<View> playerRows;
    private static List<View> enemyRows;

    public static EncounterCalculatorFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        EncounterCalculatorFragment fragment = new EncounterCalculatorFragment();
        fragment.setArguments(args);
        playerRows = new ArrayList<>();
        enemyRows = new ArrayList<>();
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
        View playerView = buildPlayerView(playerContainer, inflater);
        playerRows.add(playerView);
        refreshPlayerContainer(playerContainer);

        LinearLayout enemyContainer = view.findViewById(R.id.enemy_container);
        View enemyView = buildEnemyView(enemyContainer, inflater);
        enemyRows.add(enemyView);
        refreshEnemyContainer(enemyContainer);

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

    private void refreshPlayerContainer(LinearLayout playerContainer){
        refreshContainer(playerContainer, Opt.Player);
    }

    private void refreshEnemyContainer(LinearLayout enemyContainer){
        refreshContainer(enemyContainer, Opt.Enemy);
    }

    private void refreshContainer(LinearLayout container, Opt option){
        List<View> rows = option == Opt.Player ? playerRows : enemyRows;

        container.removeAllViews();
        container.invalidate();
        for(View u : rows){
            container.addView(u);
        }
        container.invalidate();
    }

    private View buildPlayerView(final LinearLayout playerContainer, LayoutInflater inflater){
        View playerView = inflater.inflate(R.layout.view_player_layout, null);
        playerView.findViewById(R.id.remove_player).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                playerRows.remove(v.getParent());
                refreshPlayerContainer(playerContainer);
            }
        });
        return playerView;
    }

    private View buildEnemyView(final LinearLayout enemyContainer, LayoutInflater inflater){
        View enemyView = inflater.inflate(R.layout.view_enemy_layout, null);
        enemyView.findViewById(R.id.remove_enemy).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                enemyRows.remove(v.getParent());
                refreshEnemyContainer(enemyContainer);
            }
        });
        return enemyView;
    }

    @Override
    public void onClick(View v){
        switch (v.getId()) {
            case  R.id.add_player: {
                final LinearLayout playerContainer = mView.findViewById(R.id.player_container);
                View playerView = buildPlayerView(playerContainer,LayoutInflater.from(mView.getContext()));

                playerRows.add(playerView);
                refreshPlayerContainer(playerContainer);
                break;
            }

            case R.id.add_enemy: {
                LinearLayout enemyContainer = mView.findViewById(R.id.enemy_container);
                View enemyView = buildEnemyView(enemyContainer,LayoutInflater.from(mView.getContext()));

                enemyRows.add(enemyView);
                refreshEnemyContainer(enemyContainer);
                break;
            }
        }
    }
}