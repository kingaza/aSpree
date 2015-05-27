package com.github.kingaza.aspree;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import jp.wasabeef.richeditor.RichEditor;
/**
 * Created by abu on 2015/5/24.
 */
public class RichEditFragment extends Fragment {

    private static final String TAG = "RichEditFragment";

    private RichEditor mEditor;
    private TextView mPreview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_richedit, container, false);
        mEditor = (RichEditor) view.findViewById(R.id.editor);
        mEditor.setEditorHeight(200);
        mEditor.setPlaceholder("Insert text here...");

        mPreview = (TextView) view.findViewById(R.id.preview);
        mEditor.setOnTextChangeListener(new RichEditor.OnTextChangeListener() {
            @Override
            public void onTextChange(String text) {
                mPreview.setText(text);
            }
        });

        Log.i(TAG, "RichEditFragment is created.");
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
}
