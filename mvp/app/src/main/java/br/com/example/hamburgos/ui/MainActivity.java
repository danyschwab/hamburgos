package br.com.example.hamburgos.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.Switch;

import java.util.List;

import br.com.example.hamburgos.R;
import br.com.example.hamburgos.listener.SnackItemClickListener;
import br.com.example.hamburgos.model.Request;
import br.com.example.hamburgos.model.Snack;
import br.com.example.hamburgos.request.Presenter;
import br.com.example.hamburgos.util.Constants;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.list_snacks)
    RecyclerView recyclerView;

//    @BindView(R.id.progress_bar)
//    ProgressBar progressBar;

    private SnacksAdapter adapter;
    private Presenter presenter;

    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);

        presenter = new Presenter(this);
        adapter = new SnacksAdapter(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        adapter.setClickListener(new SnackItemClickListener() {
            @Override
            public View.OnClickListener onClick(final String type, final Snack snack) {
                return new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if ( Constants.ADD.equals(type)){
                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                            builder.setTitle(getString(R.string.confirmation) + " - " + snack.getName());
                            builder.setMessage(snack.getIngredientListString());
                            builder.setPositiveButton(R.string.label_yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    presenter.addRequest(snack);
                                }
                            });
                            builder.setNegativeButton(R.string.label_no, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            });
                            builder.show();
                        } else if ( Constants.CUSTOM.equals(type)) {
                            Intent intent = new Intent(MainActivity.this, SnackDetailActivity.class);
                            intent.putExtra(Constants.SNACK, snack.getId());
                            startActivity(intent);
                        }
                    }
                };
            }
        });

        LinearLayoutManager layoutParams = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutParams);

        recyclerView.setAdapter(adapter);

        presenter.getSnacks();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_promotions) {
            Intent intent = new Intent(MainActivity.this, PromotionActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_shopper) {
            Intent intent = new Intent(MainActivity.this, RequestActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onDestroy() {
        if (unbinder != null) {
            unbinder.unbind();
        }
        super.onDestroy();
    }

    public void setContent(List<Snack> snacks) {
//        progressBar.setVisibility(View.GONE);
        adapter.setContent(snacks);
    }

    public void setError(String errorMessage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage(errorMessage);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
               dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    public void requestConfirmation() {
        Intent intent = new Intent(MainActivity.this, RequestActivity.class);
        startActivity(intent);
    }

}