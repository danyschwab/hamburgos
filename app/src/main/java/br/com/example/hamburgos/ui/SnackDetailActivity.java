package br.com.example.hamburgos.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import br.com.example.hamburgos.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SnackDetailActivity extends AppCompatActivity {


//    @BindView(R.id.image_picture)
//    ImageView imagePicture;

    private Unbinder unbinder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snack_detail);

        unbinder = ButterKnife.bind(this);

//        Picture picture = (Picture) getIntent().getSerializableExtra(Constants.PICTURE);
//
//        textUsername.setText(getString(R.string.user_name, picture.getUser()));
//        textTags.setText(getString(R.string.tags, picture.getTags()));
//        textLikes.setText(String.valueOf(picture.getLikes()));
//        textComments.setText(String.valueOf(picture.getComments()));
//        textFavorites.setText(String.valueOf(picture.getFavorites()));
//        Picasso.with(this)
//                .load(picture.getPreviewURL())
//                .placeholder(R.drawable.vector_camera)
//                .into(imagePicture);imagePicture
    }

    @Override
    protected void onDestroy() {
        if (unbinder != null) {
            unbinder.unbind();
        }
        super.onDestroy();
    }
}
