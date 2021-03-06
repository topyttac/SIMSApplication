package simms.biosci.simsapplication.Fragment;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import simms.biosci.simsapplication.Manager.FeedGermplasm;
import simms.biosci.simsapplication.R;

/**
 * Created by nuuneoi on 11/16/2014.
 */
@SuppressWarnings("unused")
public class ShowGermplasmFragment extends Fragment {

    private Typeface montserrat_regular, montserrat_bold;
    private TextView tv_title, tv_germplasm, tv_cross, tv_source, tv_select_source, tv_lot, tv_location, tv_select_location, tv_stock,
            tv_balance, tv_room, tv_shelf, tv_row, tv_box, tv_note;
    private EditText et_germplasm, et_cross, et_lot, et_stock, et_balance, et_room, et_shelf, et_row, et_box, et_note;
    private Button btn_update, btn_delete;
    private int source = -1, location = -1;
    private DatabaseReference mRootRef, mGermplasmRef;
    private String key;
    private static final int REQUEST_CODE_SHOW = 4;

    public ShowGermplasmFragment() {
        super();
    }

    @SuppressWarnings("unused")
    public static ShowGermplasmFragment newInstance(String key) {
        ShowGermplasmFragment fragment = new ShowGermplasmFragment();
        Bundle args = new Bundle();
        args.putString("KEY", key);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);

        if (savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_show_germplasm, container, false);
        initInstances(rootView, savedInstanceState);
        return rootView;
    }

    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here
        Bundle bundle = getArguments();
        key = bundle.getString("KEY");
    }

    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here
        montserrat_regular = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Montserrat-Regular.ttf");
        montserrat_bold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Montserrat-SemiBold.ttf");
        mRootRef = FirebaseDatabase.getInstance().getReference();

        tv_title = (TextView) rootView.findViewById(R.id.tv_title);
        btn_update = (Button) rootView.findViewById(R.id.btn_update);
        btn_delete = (Button) rootView.findViewById(R.id.btn_delete);
        tv_germplasm = (TextView) rootView.findViewById(R.id.tv_germplasm);
        tv_cross = (TextView) rootView.findViewById(R.id.tv_cross);
        tv_source = (TextView) rootView.findViewById(R.id.tv_source);
        tv_select_source = (TextView) rootView.findViewById(R.id.tv_select_source);
        tv_lot = (TextView) rootView.findViewById(R.id.tv_lot);
        tv_location = (TextView) rootView.findViewById(R.id.tv_location);
        tv_select_location = (TextView) rootView.findViewById(R.id.tv_select_location);
        tv_stock = (TextView) rootView.findViewById(R.id.tv_stock);
        tv_balance = (TextView) rootView.findViewById(R.id.tv_balance);
        tv_room = (TextView) rootView.findViewById(R.id.tv_room);
        tv_shelf = (TextView) rootView.findViewById(R.id.tv_shelf);
        tv_row = (TextView) rootView.findViewById(R.id.tv_row);
        tv_box = (TextView) rootView.findViewById(R.id.tv_box);
        tv_note = (TextView) rootView.findViewById(R.id.tv_note);
        et_germplasm = (EditText) rootView.findViewById(R.id.et_germplasm);
        et_cross = (EditText) rootView.findViewById(R.id.et_cross);
        et_lot = (EditText) rootView.findViewById(R.id.et_lot);
        et_stock = (EditText) rootView.findViewById(R.id.et_stock);
        et_balance = (EditText) rootView.findViewById(R.id.et_balance);
        et_room = (EditText) rootView.findViewById(R.id.et_room);
        et_shelf = (EditText) rootView.findViewById(R.id.et_shelf);
        et_row = (EditText) rootView.findViewById(R.id.et_row);
        et_box = (EditText) rootView.findViewById(R.id.et_box);
        et_note = (EditText) rootView.findViewById(R.id.et_note);

        btn_update.setTypeface(montserrat_bold);
        btn_delete.setTypeface(montserrat_bold);
        tv_title.setTypeface(montserrat_bold);
        tv_germplasm.setTypeface(montserrat_bold);
        tv_cross.setTypeface(montserrat_bold);
        tv_source.setTypeface(montserrat_bold);
        tv_select_source.setTypeface(montserrat_regular);
        tv_lot.setTypeface(montserrat_bold);
        tv_location.setTypeface(montserrat_bold);
        tv_select_location.setTypeface(montserrat_regular);
        tv_stock.setTypeface(montserrat_bold);
        tv_balance.setTypeface(montserrat_bold);
        tv_room.setTypeface(montserrat_bold);
        tv_shelf.setTypeface(montserrat_bold);
        tv_row.setTypeface(montserrat_bold);
        tv_box.setTypeface(montserrat_bold);
        tv_note.setTypeface(montserrat_bold);
        et_germplasm.setTypeface(montserrat_regular);
        et_cross.setTypeface(montserrat_regular);
        et_lot.setTypeface(montserrat_regular);
        et_stock.setTypeface(montserrat_regular);
        et_balance.setTypeface(montserrat_regular);
        et_room.setTypeface(montserrat_regular);
        et_shelf.setTypeface(montserrat_regular);
        et_row.setTypeface(montserrat_regular);
        et_box.setTypeface(montserrat_regular);
        et_note.setTypeface(montserrat_regular);

        tv_select_location.setText(Html.fromHtml("<u>Tap to select</u>"));
        tv_select_source.setText(Html.fromHtml("<u>Tap to select</u>"));

        btn_update.setOnClickListener(btn_update_click);
        btn_delete.setOnClickListener(btn_delete_click);
        tv_select_location.setOnClickListener(tv_select_location_click);
        tv_select_source.setOnClickListener(tv_select_source_click);

        mRootRef.child("germplasm").orderByChild("g_key").equalTo(key).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                FeedGermplasm model = dataSnapshot.getValue(FeedGermplasm.class);
                et_germplasm.setText(model.getG_name() + "");
                et_cross.setText(model.getG_cross() + "");
                tv_select_source.setText(Html.fromHtml("<u>" + model.getG_source() + "</u>"));
                tv_select_source.setTextColor(getResources().getColor(R.color.light_blue));
                et_lot.setText(model.getG_lot() + "");
                tv_select_location.setText(Html.fromHtml("<u>" + model.getG_location() + "</u>"));
                tv_select_location.setTextColor(getResources().getColor(R.color.light_blue));
                et_stock.setText(model.getG_stock() + "");
                et_balance.setText(model.getG_balance() + "");
                et_room.setText(model.getG_room() + "");
                et_shelf.setText(model.getG_shelf() + "");
                et_row.setText(model.getG_row() + "");
                et_box.setText(model.getG_box() + "");
                et_note.setText(model.getG_note() + "");
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    /*
     * Save Instance State Here
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save Instance State here
    }

    /*
     * Restore Instance State Here
     */
    @SuppressWarnings("UnusedParameters")
    private void onRestoreInstanceState(Bundle savedInstanceState) {
        // Restore Instance State here
    }

    View.OnClickListener btn_update_click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.bounce);
            BounceInterpolator interpolator = new BounceInterpolator();
            anim.setInterpolator(interpolator);
            btn_update.startAnimation(anim);

            if(tv_select_location.getText().toString().equals("Tap to select") || tv_select_source.getText().toString().equals("Tap to select") ||
                    et_germplasm.getText().toString().equals("") || et_cross.getText().toString().equals("") ||
                    et_lot.getText().toString().equals("") || et_stock.getText().toString().equals("") ||
                    et_balance.getText().toString().equals("") || et_room.getText().toString().equals("") ||
                    et_shelf.getText().toString().equals("") || et_row.getText().toString().equals("") ||
                    et_box.getText().toString().equals("") || et_note.getText().toString().equals("")){
                Toast.makeText(getContext(), "Please fill in all information.", Toast.LENGTH_SHORT).show();
            } else{
                HashMap<String, Object> germplasm = new HashMap<String, Object>();
                germplasm.put("g_name", et_germplasm.getText().toString());
                germplasm.put("g_cross", et_cross.getText().toString());
                germplasm.put("g_source", tv_select_source.getText().toString());
                germplasm.put("g_lot", Integer.parseInt(et_lot.getText().toString()));
                germplasm.put("g_location", tv_select_location.getText().toString());
                germplasm.put("g_stock", et_stock.getText().toString());
                germplasm.put("g_balance", Integer.parseInt(et_balance.getText().toString()));
                germplasm.put("g_room", Integer.parseInt(et_room.getText().toString()));
                germplasm.put("g_shelf", Integer.parseInt(et_shelf.getText().toString()));
                germplasm.put("g_row", Integer.parseInt(et_row.getText().toString()));
                germplasm.put("g_box", Integer.parseInt(et_box.getText().toString()));
                germplasm.put("g_note", et_note.getText().toString());
                germplasm.put("g_key", key);
                Map<String, Object> child = new HashMap<>();
                child.put(key, germplasm);
                mRootRef.child("germplasm").updateChildren(child);
                Toast.makeText(getContext(), "Update germplasm successfully.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.putExtra("KEY", key);
                intent.putExtra("g_name", et_germplasm.getText().toString());
                intent.putExtra("g_cross", et_cross.getText().toString());
                intent.putExtra("g_source", tv_select_source.getText().toString());
                intent.putExtra("g_lot", Integer.parseInt(et_lot.getText().toString()));
                intent.putExtra("g_location", tv_select_location.getText().toString());
                intent.putExtra("g_stock", et_stock.getText().toString());
                intent.putExtra("g_balance", Integer.parseInt(et_balance.getText().toString()));
                intent.putExtra("g_room", Integer.parseInt(et_room.getText().toString()));
                intent.putExtra("g_shelf", Integer.parseInt(et_shelf.getText().toString()));
                intent.putExtra("g_row", Integer.parseInt(et_row.getText().toString()));
                intent.putExtra("g_box", Integer.parseInt(et_box.getText().toString()));
                intent.putExtra("g_note", et_note.getText().toString());
                intent.putExtra("what2do", "update");
                getActivity().setResult(REQUEST_CODE_SHOW, intent);
                getActivity().finish();
            }
        }
    };

    View.OnClickListener btn_delete_click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.bounce);
            BounceInterpolator interpolator = new BounceInterpolator();
            anim.setInterpolator(interpolator);
            btn_delete.startAnimation(anim);

            mRootRef.child("germplasm").child(key).removeValue();
            Toast.makeText(getContext(), "Delete germplasm successfully.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.putExtra("KEY", key);
            intent.putExtra("what2do", "delete");
            getActivity().setResult(REQUEST_CODE_SHOW, intent);
            getActivity().finish();
        }
    };

    View.OnClickListener tv_select_location_click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.bounce);
            BounceInterpolator interpolator = new BounceInterpolator();
            anim.setInterpolator(interpolator);
            tv_select_location.startAnimation(anim);

            MaterialDialog.Builder builder = new MaterialDialog.Builder(getContext());
            builder
                    .title("Select Source")
                    .items("Thamasat", "Biosci 2")
                    .typeface("Montserrat-Regular.ttf", "Montserrat-Regular.ttf")
                    .itemsCallbackSingleChoice(location, new MaterialDialog.ListCallbackSingleChoice() {
                        @Override
                        public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                            tv_select_location.setText(Html.fromHtml("<u>" + text + "</u>"));
                            tv_select_location.setTextColor(getResources().getColor(R.color.light_blue));
                            location = which;
                            return true;
                        }
                    })
                    .negativeText("cancel")
                    .show();
        }
    };

    View.OnClickListener tv_select_source_click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.bounce);
            BounceInterpolator interpolator = new BounceInterpolator();
            anim.setInterpolator(interpolator);
            tv_select_source.startAnimation(anim);

            MaterialDialog.Builder builder = new MaterialDialog.Builder(getContext());
            builder
                    .title("Select Source")
                    .items("Nursery 1", "Nursery 2", "Nursery 3")
                    .typeface("Montserrat-Regular.ttf", "Montserrat-Regular.ttf")
                    .itemsCallbackSingleChoice(source, new MaterialDialog.ListCallbackSingleChoice() {
                        @Override
                        public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                            tv_select_source.setText(Html.fromHtml("<u>" + text + "</u>"));
                            tv_select_source.setTextColor(getResources().getColor(R.color.light_blue));
                            source = which;
                            return true;
                        }
                    })
                    .negativeText("cancel")
                    .show();
        }
    };
}
