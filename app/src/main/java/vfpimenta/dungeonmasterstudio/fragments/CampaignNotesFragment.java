package vfpimenta.dungeonmasterstudio.fragments;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import vfpimenta.dungeonmasterstudio.R;
import vfpimenta.dungeonmasterstudio.entities.BasicEntity;
import vfpimenta.dungeonmasterstudio.entities.CharacterEntity;
import vfpimenta.dungeonmasterstudio.entities.ItemEntity;
import vfpimenta.dungeonmasterstudio.entities.LocationEntity;
import vfpimenta.dungeonmasterstudio.entities.NoteEntity;
import vfpimenta.dungeonmasterstudio.exceptions.MissingFieldException;
import vfpimenta.dungeonmasterstudio.util.IOHandler;

public class CampaignNotesFragment extends Fragment implements View.OnClickListener {
    private static final int CHARACTER_CODE = 0;
    private static final int LOCATION_CODE = 1;
    private static final int ITEM_CODE = 2;
    private static final int NOTE_CODE = 3;

    private List<CharacterEntity> characters = new ArrayList<>();
    private List<LocationEntity> locations = new ArrayList<>();
    private List<ItemEntity> items = new ArrayList<>();
    private List<NoteEntity> notes = new ArrayList<>();

    public static final String ARG_PAGE = "ARG_PAGE";

    private int mPage;
    private Gson gson;
    private View dialog;
    private Bitmap img;

    public View getDialog() {
        return dialog;
    }

    public void setDialog(View dialog) {
        this.dialog = dialog;
    }

    public Bitmap getImg() {
        return img;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }

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

    private void buildCharacterView(final LinearLayout container, final BasicEntity entity){
        buildView(container, entity, R.string.character_info);
    }

    private void buildLocationView(final LinearLayout container, final BasicEntity entity){
        buildView(container, entity, R.string.location_info);
    }

    private void buildItemView(final LinearLayout container, final BasicEntity entity){
        buildView(container, entity, R.string.item_info);
    }

    private void buildNoteView(final LinearLayout container, final BasicEntity entity){
        buildView(container, entity, R.string.note_info);
    }

    private void buildView(final LinearLayout container, final BasicEntity entity, final int title){
        View characterView = getActivity().getLayoutInflater().inflate(R.layout.view_entity_layout, null);
        characterView.findViewById(R.id.entity_info).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
                if(entity.getImage() != null){
                    ImageView imageView = new ImageView(getContext());
                    imageView.setImageBitmap(entity.getImage());
                    builder.setView(imageView);
                }
                builder.setTitle(title).setMessage(Html.fromHtml(entity.toHtml())).show();
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
                                characters.remove(entity);
                                container.removeView((View) v.getParent());
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
        ((TextView) characterView.findViewById(R.id.entity_label)).setText(entity.getName());
        container.addView(characterView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_character: {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                final LayoutInflater inflater = getActivity().getLayoutInflater();
                final View view = inflater.inflate(R.layout.dialog_character_form, null);
                setDialog(view);
                view.findViewById(R.id.add_character_img).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View _view) {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent, "Choose Picture"), CHARACTER_CODE);
                    }
                });
                builder.setTitle(R.string.character_form_title)
                        .setView(view)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int wich){
                                try{
                                    CharacterEntity character = CharacterEntity.init(view, getImg(), getResources());

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
                setDialog(view);
                view.findViewById(R.id.add_location_img).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View _view) {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent, "Choose Picture"), LOCATION_CODE);
                    }
                });
                view.findViewById(R.id.add_location_person).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View _view) {
                        LinearLayout peopleContainer = view.findViewById(R.id.people_container);
                        Spinner peopleSpinner = new Spinner(getContext());
                        List<String> spinnerArray =  new ArrayList<>();
                        for(CharacterEntity character : characters){
                            spinnerArray.add(character.getName());
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, spinnerArray);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        peopleSpinner.setAdapter(adapter);

                        peopleContainer.addView(peopleSpinner);
                    }
                });
                builder.setTitle(R.string.location_form_title)
                        .setView(view)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int wich){
                                try{
                                    LocationEntity location = LocationEntity.init(view, getImg());

                                    final LinearLayout locationContainer = getView().findViewById(R.id.location_container);
                                    buildLocationView(locationContainer, location);
                                    locations.add(location);
                                } catch (MissingFieldException e){
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
            case R.id.add_item: {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                final LayoutInflater inflater = getActivity().getLayoutInflater();
                final View view = inflater.inflate(R.layout.dialog_item_form, null);
                view.findViewById(R.id.add_item_img).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View _view) {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent, "Choose Picture"), ITEM_CODE);
                    }
                });
                builder.setTitle(R.string.item_form_title)
                        .setView(view)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int wich){
                                try {
                                    ItemEntity item = ItemEntity.init(view, getImg(), getResources());

                                    final LinearLayout itemContainer = getView().findViewById(R.id.item_container);
                                    buildItemView(itemContainer, item);
                                    items.add(item);
                                } catch (MissingFieldException e){
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
            case R.id.add_note: {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                final LayoutInflater inflater = getActivity().getLayoutInflater();
                final View view = inflater.inflate(R.layout.dialog_note_form, null);
                view.findViewById(R.id.add_note_img).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View _view) {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent, "Choose Picture"), NOTE_CODE);
                    }
                });
                builder.setTitle(R.string.note_form_title)
                        .setView(view)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int wich){
                                try{
                                    NoteEntity note = NoteEntity.init(view, getImg());

                                    final LinearLayout noteContainer = getView().findViewById(R.id.note_container);
                                    buildNoteView(noteContainer, note);
                                    notes.add(note);
                                } catch (MissingFieldException e) {
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
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_OK) {
            Uri selectedImg = data.getData();
            int buttonId = 0;
            switch(requestCode){
                case CHARACTER_CODE:
                    buttonId = R.id.add_character_img;
                    break;
                case LOCATION_CODE:
                    buttonId = R.id.add_location_img;
                    break;
                case ITEM_CODE:
                    buttonId = R.id.add_item_img;
                    break;
                case NOTE_CODE:
                    buttonId = R.id.add_note_img;
                    break;
            }

            TableRow buttonRow = (TableRow) getDialog().findViewById(buttonId).getParent();
            TextView textView = new TextView(this.getContext());
            textView.setText(data.getDataString().substring(data.getDataString().lastIndexOf('/')));
            buttonRow.removeView(getDialog().findViewById(buttonId));
            buttonRow.addView(textView);

            try {
                setImg(MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), selectedImg));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
