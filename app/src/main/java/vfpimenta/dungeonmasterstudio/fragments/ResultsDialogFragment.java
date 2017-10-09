package vfpimenta.dungeonmasterstudio.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Html;

public class ResultsDialogFragment extends DialogFragment {

    public static ResultsDialogFragment newInstance(String title, int easy, int medium, int hard, int deadly, int raw, double adjusted, String level) {
        ResultsDialogFragment frag = new ResultsDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putInt("easy", easy);
        args.putInt("medium",medium);
        args.putInt("hard",hard);
        args.putInt("deadly",deadly);
        args.putInt("raw",raw);
        args.putDouble("adjusted",adjusted);
        args.putString("level",level);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle((String) getArguments().get("title"));
        builder.setMessage(Html.fromHtml(
                "Easy threshold: "+getArguments().get("easy")+"<br/>"+
                        "Medium threshold: "+getArguments().get("medium")+"<br/>"+
                        "Hard threshold: "+getArguments().get("hard")+"<br/>"+
                        "Deadly threshold: "+getArguments().get("deadly")+"<br/>"+
                        "Raw XP: "+getArguments().get("raw")+"<br/>"+
                        "Adjusted XP: "+getArguments().get("adjusted")+"<br/>"+
                        "Encounter level: "+getArguments().get("level")
        ));
        return builder.create();
    }
}