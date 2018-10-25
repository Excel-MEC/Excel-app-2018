package ml.arjunnair.excelapp.ui;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ml.arjunnair.excelapp.R;

import static android.support.constraint.Constraints.TAG;

public class HTMLTextFragment extends Fragment {

    String htmlText = "No description provided";

    public HTMLTextFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle aboutDetails = getArguments();
        if (aboutDetails != null) {
            htmlText = aboutDetails.getString("text");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_htmltext, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView textView = view.findViewById(R.id.html_text);
        if (htmlText != null) {
            Spanned sp = Html.fromHtml(htmlText);
            textView.setText(sp);
        }
    }
}
