package vfpimenta.dungeonmasterstudio.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vfpimenta.dungeonmasterstudio.R;
import vfpimenta.dungeonmasterstudio.util.Calculator;
import vfpimenta.dungeonmasterstudio.util.EncounterResult;

public class EncounterCalculatorFragment extends Fragment implements View.OnClickListener {

    private enum Opt{
        Player, Enemy
    }

    public static final String ARG_PAGE = "ARG_PAGE";

    private int mPage;

    private List<View> playerRows = new ArrayList<>();
    private List<View> enemyRows = new ArrayList<>();

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

        ImageButton addPlayer = view.findViewById(R.id.add_player);
        addPlayer.setOnClickListener(this);
        ImageButton addEnemy = view.findViewById(R.id.add_enemy);
        addEnemy.setOnClickListener(this);
        Button calculate = view.findViewById(R.id.calculate);
        calculate.setOnClickListener(this);

        if(savedInstanceState != null){
            LinearLayout playerContainer = view.findViewById(R.id.player_container);
            LinearLayout enemyContainer = view.findViewById(R.id.enemy_container);

            for(int i : savedInstanceState.getIntArray("player-data")){
                View playerView = buildPlayerView(playerContainer,LayoutInflater.from(view.getContext()));
                ((Spinner) playerView.findViewById(R.id.spinner_level)).setSelection(i);

                playerRows.add(playerView);
            }

            for(int i : savedInstanceState.getIntArray("enemy-data")){
                View enemyView = buildEnemyView(enemyContainer,LayoutInflater.from(view.getContext()));
                ((Spinner) enemyView.findViewById(R.id.spinner_cr)).setSelection(i);

                enemyRows.add(enemyView);
            }
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        refreshPlayerContainer((LinearLayout) getView().findViewById(R.id.player_container));
        refreshEnemyContainer((LinearLayout) getView().findViewById(R.id.enemy_container));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putIntArray("player-data", getPlayerLevels());
        outState.putIntArray("enemy-data", getEnemyCrs());
    }

    private void refreshPlayerContainer(LinearLayout playerContainer){
        if(getView() != null){
            playerContainer = getView().findViewById(R.id.player_container);
        }
        refreshContainer(playerContainer, Opt.Player);
    }

    private void refreshEnemyContainer(LinearLayout enemyContainer){
        if(getView() != null) {
            enemyContainer = getView().findViewById(R.id.enemy_container);
        }
        refreshContainer(enemyContainer, Opt.Enemy);
    }

    private void refreshContainer(LinearLayout container, Opt option){
        List<View> rows = option == Opt.Player ? playerRows : enemyRows;

        container.removeAllViews();
        int index = 0;
        for(View u : rows){
            index++;
            if(u.getParent() != null){
                ((LinearLayout)u.getParent()).removeView(u);
            }
            container.addView(u);
            if(option == Opt.Player) {
                ((TextView) u.findViewById(R.id.player_label)).setText(getResources().getString(R.string.player_label)+index);
            }else if(option == Opt.Enemy){
                ((TextView) u.findViewById(R.id.enemy_label)).setText(getResources().getString(R.string.monster_label)+index);
            }
        }
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

    private int[] getPlayerLevels(){
        int[] playerLevels = new int[playerRows.size()];
        for(int i = 0; i<playerRows.size(); i++){
            View playerRow = playerRows.get(i);
            Spinner spinnerLevel = (Spinner) playerRow.findViewById(R.id.spinner_level);
            playerLevels[i] = spinnerLevel.getSelectedItemPosition();
        }

        return playerLevels;
    }

    private int[] getEnemyCrs(){
        int[] enemyCrs = new int[enemyRows.size()];
        for(int i = 0; i<enemyRows.size(); i++){
            View enemyRow = enemyRows.get(i);
            Spinner spinnerCr = (Spinner) enemyRow.findViewById(R.id.spinner_cr);
            enemyCrs[i] = spinnerCr.getSelectedItemPosition();
        }

        return enemyCrs;
    }

    private int[] clearZeros(int[] array){
        int j = 0;
        for( int i=0;  i<array.length;  i++ )
        {
            if (array[i] != 0)
                array[j++] = array[i];
        }
        int [] newArray = new int[j];
        System.arraycopy( array, 0, newArray, 0, j );
        return newArray;
    }

    private void launchCalculation(){
        EncounterResult results = Calculator.calculate(clearZeros(getPlayerLevels()), clearZeros(getEnemyCrs()));
        ResultsDialogFragment dialog = ResultsDialogFragment.newInstance(
                getResources().getString(R.string.results),
                results.getEasyThreshold(),
                results.getMediumThreshold(),
                results.getHardThreshold(),
                results.getDeadlyThreshold(),
                results.getRawExperience(),
                results.getAdjustedExperience(),
                results.getEncounterLevel());
        dialog.show(getFragmentManager(), "dialog");
    }

    private boolean checkForm(){
        for(int i : getPlayerLevels()){
            if(i == 0) return false;
        }

        for(int i : getEnemyCrs()){
            if(i == 0) return false;
        }
        return true;
    }

    @Override
    public void onClick(View v){
        switch (v.getId()) {
            case  R.id.add_player: {
                final LinearLayout playerContainer = getView().findViewById(R.id.player_container);
                View playerView = buildPlayerView(playerContainer,LayoutInflater.from(getView().getContext()));

                playerRows.add(playerView);
                refreshPlayerContainer(playerContainer);
                break;
            }

            case R.id.add_enemy: {
                LinearLayout enemyContainer = getView().findViewById(R.id.enemy_container);
                View enemyView = buildEnemyView(enemyContainer,LayoutInflater.from(getView().getContext()));

                enemyRows.add(enemyView);
                refreshEnemyContainer(enemyContainer);
                break;
            }

            case R.id.calculate: {
                if (!checkForm()){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle(R.string.incomplete_form_title)
                            .setMessage(R.string.incomplete_form_message)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener(){
                                public void onClick(DialogInterface dialog, int wich){
                                    launchCalculation();
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener(){
                                public void onClick(DialogInterface dialog, int wich){
                                    // do nothing
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }else{
                    launchCalculation();
                }
            }
        }
    }
}