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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
import vfpimenta.dungeonmasterstudio.exceptions.MissingFieldException;
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

        fillEntityContainer(view);

        return view;
    }

    @Override
    public void onStop() {
        super.onStop();

        String characterData = gson.toJson(characters);
        String locationData = gson.toJson(locations);
        String itemData = gson.toJson(items);
        String noteData = gson.toJson(notes);

        IOHandler.storeCharacterData(getContext(), characterData);
        IOHandler.storeLocationData(getContext(), locationData);
        IOHandler.storeItemData(getContext(), itemData);
        IOHandler.storeNoteData(getContext(), noteData);
    }

    private void fillEntityContainer(View view){
        String characterData = IOHandler.loadCharacterData(getContext());
        String locationData = IOHandler.loadLocationData(getContext());
        String itemData = IOHandler.loadItemData(getContext());
        String noteData = IOHandler.loadNoteData(getContext());

        List<CharacterEntity> savedCharacters = gson.fromJson(characterData, new TypeToken<List<CharacterEntity>>(){}.getType());
        List<LocationEntity> savedLocations = gson.fromJson(locationData, new TypeToken<List<LocationEntity>>(){}.getType());
        List<ItemEntity> savedItems = gson.fromJson(itemData, new TypeToken<List<ItemEntity>>(){}.getType());
        List<NoteEntity> savedNotes = gson.fromJson(noteData, new TypeToken<List<NoteEntity>>(){}.getType());

        if(savedCharacters != null){
            characters = savedCharacters;
        }
        if(savedLocations != null){
            locations = savedLocations;
        }
        if(savedItems != null){
            items = savedItems;
        }
        if(savedNotes != null){
            notes = savedNotes;
        }

        final LinearLayout characterContainer = view.findViewById(R.id.character_container);
        final LinearLayout locationContainer = view.findViewById(R.id.location_container);
        final LinearLayout itemContainer = view.findViewById(R.id.item_container);
        final LinearLayout noteContainer = view.findViewById(R.id.note_container);

        for(CharacterEntity character : characters){
            buildCharacterView(characterContainer, character);
        }

        for(LocationEntity location : locations){
            buildLocationView(locationContainer, location);
        }

        for(ItemEntity item : items){
            buildItemView(itemContainer, item);
        }

        for(NoteEntity note : notes){
            buildNoteView(noteContainer, note);
        }
    }

    private void buildCharacterView(final LinearLayout characterContainer, final CharacterEntity character){
        View characterView = getActivity().getLayoutInflater().inflate(R.layout.view_entity_layout, null);
        characterView.findViewById(R.id.entity_info).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.character_info)
                        .setMessage(Html.fromHtml(character.getHtml()))
                        .show();
            }
        });
        characterView.findViewById(R.id.remove_entity).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(final View v){
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.confirm_deletion_title)
                        .setMessage(R.string.confirm_deletion_message)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int wich){
                                characters.remove(character);
                                characterContainer.removeView((View) v.getParent());
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int wich){
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
        ((TextView) characterView.findViewById(R.id.entity_label)).setText(character.getName());
        characterContainer.addView(characterView);
    }

    private void buildLocationView(final LinearLayout locationContainer, final LocationEntity location){
        View locationView = getActivity().getLayoutInflater().inflate(R.layout.view_entity_layout, null);
        locationView.findViewById(R.id.entity_info).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.location_info);
                builder.setMessage(Html.fromHtml(
                        "Name: "+location.getName()+"<br/>"+
                                "Description: "+location.getDescription()+"<br/>"
                ))
                        .show();
            }
        });
        locationView.findViewById(R.id.remove_entity).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(final View v){
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.confirm_deletion_title)
                        .setMessage(R.string.confirm_deletion_message)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int wich){
                                locations.remove(location);
                                locationContainer.removeView((View) v.getParent());
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int wich){
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
        ((TextView) locationView.findViewById(R.id.entity_label)).setText(location.getName());
        locationContainer.addView(locationView);
    }

    private void buildItemView(final LinearLayout itemContainer, final ItemEntity item){
        View itemView = getActivity().getLayoutInflater().inflate(R.layout.view_entity_layout, null);
        itemView.findViewById(R.id.entity_info).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.item_info);
                builder.setMessage(Html.fromHtml(
                        "Name: "+item.getName()+"<br/>"+
                                "Description: "+item.getDescription()+"<br/>"
                ))
                        .show();
            }
        });
        itemView.findViewById(R.id.remove_entity).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(final View v){
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.confirm_deletion_title)
                        .setMessage(R.string.confirm_deletion_message)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int wich){
                                items.remove(item);
                                itemContainer.removeView((View) v.getParent());
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int wich){
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
        ((TextView) itemView.findViewById(R.id.entity_label)).setText(item.getName());
        itemContainer.addView(itemView);
    }

    private void buildNoteView(final LinearLayout noteContainer, final NoteEntity note){
        View noteView = getActivity().getLayoutInflater().inflate(R.layout.view_entity_layout, null);
        noteView.findViewById(R.id.entity_info).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.note_info);
                builder.setMessage(Html.fromHtml(
                        "Name: "+note.getName()+"<br/>"+
                                "Description: "+note.getDescription()+"<br/>"
                ))
                        .show();
            }
        });
        noteView.findViewById(R.id.remove_entity).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(final View v){
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.confirm_deletion_title)
                        .setMessage(R.string.confirm_deletion_message)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int wich){
                                notes.remove(note);
                                noteContainer.removeView((View) v.getParent());
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int wich){
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
        ((TextView) noteView.findViewById(R.id.entity_label)).setText(note.getName());
        noteContainer.addView(noteView);
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
                                try{
                                    CharacterEntity character = CharacterEntity.init(view, getResources());

                                    final LinearLayout characterContainer = getView().findViewById(R.id.character_container);
                                    buildCharacterView(characterContainer, character);
                                    characters.add(character);
                                } catch(MissingFieldException e){
                                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
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
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                final LayoutInflater inflater = getActivity().getLayoutInflater();
                final View view = inflater.inflate(R.layout.dialog_location_form, null);
                builder.setTitle(R.string.location_form_title)
                        .setView(view)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int wich){
                                String name = ((EditText) view.findViewById(R.id.location_name)).getText().toString();
                                String description = ((EditText) view.findViewById(R.id.location_description)).getText().toString();
                                LocationEntity location = new LocationEntity(name, description, null, null);
                                final LinearLayout locationContainer = getView().findViewById(R.id.location_container);
                                buildLocationView(locationContainer, location);
                                locations.add(location);
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
            case R.id.add_item: {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                final LayoutInflater inflater = getActivity().getLayoutInflater();
                final View view = inflater.inflate(R.layout.dialog_item_form, null);
                builder.setTitle(R.string.item_form_title)
                        .setView(view)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int wich){
                                String name = ((EditText) view.findViewById(R.id.item_name)).getText().toString();
                                String description = ((EditText) view.findViewById(R.id.item_description)).getText().toString();
                                ItemEntity item = new ItemEntity(name, description);
                                final LinearLayout itemContainer = getView().findViewById(R.id.item_container);
                                buildItemView(itemContainer, item);
                                items.add(item);
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
            case R.id.add_note: {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                final LayoutInflater inflater = getActivity().getLayoutInflater();
                final View view = inflater.inflate(R.layout.dialog_note_form, null);
                builder.setTitle(R.string.note_form_title)
                        .setView(view)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int wich){
                                String name = ((EditText) view.findViewById(R.id.note_name)).getText().toString();
                                String description = ((EditText) view.findViewById(R.id.note_description)).getText().toString();
                                NoteEntity note = new NoteEntity(name, description, null);
                                final LinearLayout noteContainer = getView().findViewById(R.id.note_container);
                                buildNoteView(noteContainer, note);
                                notes.add(note);
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
        }
    }
}
