package org.misty.rc.Qiitaroid;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.LoaderManager;
import android.app.ProgressDialog;
import android.content.Loader;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONObject;
import org.misty.rc.Qiitaroid.qiita.User;

import java.net.URI;

public class QiitaroidActivity extends Activity {

    private String[] _PlanetTitle;
    private DrawerLayout _DrawerLayout;
    private ListView _DrawerList;
    private ActionBarDrawerToggle _DrawerToggle;
    private LinearLayout _Drawer;

    private Button _Button;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //loader debugmode
        LoaderManager.enableDebugLogging(true);
        //loader initialize
        getLoaderManager().initLoader(0, null, callbacks);

        _PlanetTitle = getResources().getStringArray(R.array.planets_array);

        //Navigation Drawer
        _DrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        _DrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        _Drawer = (LinearLayout) findViewById(R.id.left_drawer_layout);

        _DrawerList = (ListView) findViewById(R.id.left_drawer_list);
        _DrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, _PlanetTitle));
        _DrawerList.setOnItemClickListener(new DrawerItemClickListener());

        _Button = (Button) findViewById(R.id.left_drawer_button);
        _Button.setOnClickListener(new ButtonClickListener());

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        _DrawerToggle = new ActionBarDrawerToggle(
                this,
                _DrawerLayout,
                R.drawable.ic_drawer,
                R.string.drawer_open,
                R.string.drawer_close
        ) {
            @Override
            public void onDrawerClosed(View drawerView) {
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                invalidateOptionsMenu();
            }
        };

        _DrawerLayout.setDrawerListener(_DrawerToggle);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        _DrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        _DrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(_DrawerToggle.onOptionsItemSelected(item)) {
            return true;
        } else {
            return false;
        }
    }

    private class ButtonClickListener implements Button.OnClickListener {
        @Override
        public void onClick(View v) {
            _DrawerLayout.closeDrawer(_Drawer);

            Bundle args = new Bundle();
            args.putString("url", "https://qiita.com/api/v1/tags");
            getLoaderManager().restartLoader(0, args, callbacks);
        }
    }

    //DrawerItemClickListener
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        }
    }

    //LoaderCallback
    private final LoaderManager.LoaderCallbacks<String> callbacks = new LoaderManager.LoaderCallbacks<String>() {
        @Override
        public Loader<String> onCreateLoader(int id, Bundle args) {

            ProgressDialogFragment dialog = new ProgressDialogFragment();
            Bundle progressBundle = new Bundle();
            progressBundle.putString(Ref.PROGRESS_MESSAGE, "データほげほげ");
            dialog.setArguments(progressBundle);
            dialog.show(getFragmentManager(), Ref.PROGRESS_TAG);

            return new QiitaLoader(QiitaroidActivity.this, args);
        }

        @Override
        public void onLoadFinished(Loader<String> loader, String data) {
            ProgressDialogFragment dialog =
                    (ProgressDialogFragment)getFragmentManager().findFragmentByTag(Ref.PROGRESS_TAG);
            if(dialog != null) {
                dialog.onDismiss(dialog.getDialog());
            }

            Gson gson = new GsonBuilder().create();
            User user = gson.fromJson(data, User.class);

//            String parseJson = "";
//            try {
//                JSONObject json = new JSONObject(data);
//                parseJson = json.toString(4);
//            } catch(Exception ex) {
//                ex.printStackTrace();
//            }

            try {
                ImageView im = (ImageView) findViewById(R.id.profile_icon);
                im.setImageURI(Uri.parse(user.getProfile_image_url()));

                TextView tvv = (TextView) findViewById(R.id.profile_name);
                tvv.setText(user.getUrl_name());

            } catch (Exception ex) {
                ex.printStackTrace();
            }

            String parseJson = gson.toJson(user, User.class);


            TextView tv = (TextView)findViewById(R.id.tv);
            tv.setText(parseJson);
        }

        @Override
        public void onLoaderReset(Loader<String> loader) {
            //To change body of implemented methods use File | Settings | File Templates.
        }
    };

    //Progress Fragment
    public static class ProgressDialogFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            ProgressDialog dialog = new ProgressDialog(getActivity());
            dialog.setMessage(getArguments().getString(Ref.PROGRESS_MESSAGE));
            return dialog;
        }
    }
}
