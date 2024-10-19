// SinopsisFragment.java
package com.example.mercadolibromobile.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.bumptech.glide.Glide;
import com.example.mercadolibromobile.R;

public class SinopsisFragment extends Fragment {

    public static SinopsisFragment newInstance(String title, String description, String imageUrl) {
        SinopsisFragment fragment = new SinopsisFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("description", description);
        args.putString("image", imageUrl);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sinopsis, container, false);

        String title = getArguments().getString("title");
        String description = getArguments().getString("description");
        String imageUrl = getArguments().getString("image");

        TextView tvTitle = view.findViewById(R.id.book_title);
        TextView tvDescription = view.findViewById(R.id.book_synopsis);
        ImageView ivBookCover = view.findViewById(R.id.book_image);
        Button btnVolver = view.findViewById(R.id.btnvolversinopsis);

        tvTitle.setText(title);
        tvDescription.setText(description);
        Glide.with(this).load(imageUrl).into(ivBookCover);

        btnVolver.setOnClickListener(v -> getActivity().onBackPressed());

        return view;
    }
}
