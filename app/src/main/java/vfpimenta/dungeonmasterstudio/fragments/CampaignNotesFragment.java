package vfpimenta.dungeonmasterstudio.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import vfpimenta.dungeonmasterstudio.R;
import vfpimenta.dungeonmasterstudio.entities.CharacterEntity;
import vfpimenta.dungeonmasterstudio.entities.ItemEntity;
import vfpimenta.dungeonmasterstudio.entities.LocationEntity;
import vfpimenta.dungeonmasterstudio.entities.NoteEntity;
import vfpimenta.dungeonmasterstudio.util.IOHandler;

public class CampaignNotesFragment extends Fragment implements View.OnClickListener {

    private List<CharacterEntity> characters = new ArrayList<>();
    private List<LocationEntity> locations = new ArrayList<>();
    private List<ItemEntity> items = new ArrayList<>();
    private List<NoteEntity> notes = new ArrayList<>();

    public static final String ARG_PAGE = "ARG_PAGE";

    private int mPage;
    private Gson gson;

    public static CampaignNotesFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        CampaignNotesFragment fragment = new CampaignNotesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
        gson = new Gson();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_campaign_notes, container, false);

        ImageButton addCharacter = view.findViewById(R.id.add_character);
        addCharacter.setOnClickListener(this);
        ImageButton addLocation = view.findViewById(R.id.add_location);
        addLocation.setOnClickListener(this);
        ImageButton addItem = view.findViewById(R.id.add_item);
        addItem.setOnClickListener(this);
        ImageButton addNote = view.findViewById(R.id.add_note);
        addNote.setOnClickListener(this);

        fillCharacterContainer(view);

        return view;
    }

    @Override
    public void onStop() {
        super.onStop();

        String characterData = gson.toJson(characters);
        IOHandler.storeCharacterData(getContext(), characterData);
    }

    private void fillCharacterContainer(View view){
        String characterData = IOHandler.loadCharacterData(getContext());
        List<CharacterEntity> savedCharacters = gson.fromJson(characterData, new TypeToken<List<CharacterEntity>>(){}.getType());
        if(savedCharacters != null){
            characters = savedCharacters;
        }

        final LinearLayout characterContainer = view.findViewById(R.id.character_container);
        for(CharacterEntity character : characters){
            buildCharacterView(characterContainer, character);
        }
    }

    private void buildCharacterView(final LinearLayout characterContainer, final CharacterEntity character){
        View characterView = getActivity().getLayoutInflater().inflate(R.layout.view_character_layout, null);
        final String reference = character.getReference() != null ? character.getReference().toString() : "<null>";
        characterView.findViewById(R.id.character_info).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.character_info);
                builder.setMessage(Html.fromHtml(
                        "Name: "+character.getName()+"<br/>"+
                                "Description: "+character.getDescription()+"<br/>"+
                                "Reference: "+reference+"<br/>"
                ))
                .show();
            }
        });
        characterView.findViewById(R.id.remove_character).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                characters.remove(character);
                characterContainer.removeView((View) v.getParent());
            }
        });
        ((TextView) characterView.findViewById(R.id.character_label)).setText(character.getName());
        characterContainer.addView(characterView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_character: {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                final LayoutInflater inflater = getActivity().getLayoutInflater();
                final View view = inflater.inflate(R.layout.dialog_character_form, null);
                builder.setTitle(R.string.character_form_title)
                        .setView(view)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int wich){
                                String name = ((EditText) view.findViewById(R.id.character_name)).getText().toString();
                                String description = ((EditText) view.findViewById(R.id.character_description)).getText().toString();
                                CharacterEntity character = new CharacterEntity(name, description, null);
                                final LinearLayout characterContainer = getView().findViewById(R.id.character_container);
                                buildCharacterView(characterContainer, character);
                                characters.add(character);
                            }
                        })
                        .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int wich){
                                // do nothing
                            }
                        })
                        .show();
                break;
            }
            case R.id.add_location: {
                break;
            }
            case R.id.add_item: {
                break;
            }
            case R.id.add_note: {
                break;
            }
        }
    }
}
