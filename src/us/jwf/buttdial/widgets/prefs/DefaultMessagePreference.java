package us.jwf.buttdial.widgets.prefs;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import us.jwf.buttdial.App;
import us.jwf.buttdial.R;

/**
 *
 */
public class DefaultMessagePreference extends Preference implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private Activity activity;
    private App app;
    private View view;
    private Button addOptionButton;
    private RadioGroup radioGroup;
    private Button removeOptionButton;

    public DefaultMessagePreference(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setup();
    }

    public DefaultMessagePreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup();
    }

    public DefaultMessagePreference(Context context) {
        super(context);
        setup();
    }

    protected void setup() {
        activity = (Activity) getContext();
        app = (App) activity.getApplication();
        setPersistent(false);
    }

    @Override
    protected void onSetInitialValue(boolean restorePersistedValue, Object defaultValue) {
    }

    @Override
    protected Object onGetDefaultValue(TypedArray a, int index) {
        return "";
    }

    @Override
    protected View onCreateView(ViewGroup parent) {
        this.view = LayoutInflater.from(getContext()).inflate(R.layout.pref_default_message, parent, false);
        radioGroup = (RadioGroup) view.findViewById(R.id.message_list_radiogroup);

        addOptionButton = (Button) view.findViewById(R.id.add_option_button);
        removeOptionButton = (Button) view.findViewById(R.id.remove_option_button);
        return view;
    }

    @Override
    public void onClick(View v) {
        if (v == addOptionButton) {
            startAddOptionDialog();
        }

        if (v == removeOptionButton) {
            startRemoveOptionDialog();
        }
    }

    @Override
    protected void onBindView(View view) {
        super.onBindView(view);
        updateUI();
        addOptionButton.setOnClickListener(this);
        removeOptionButton.setOnClickListener(this);
    }

    public void updateUI() {
        radioGroup.removeAllViews();
        radioGroup.setOnCheckedChangeListener(null);

        String[] messageOptions = app.settings().getMessageOptionsArray();
        RadioButton buttonToCheck = new RadioButton(getContext());
        for (int i = 0; i < messageOptions.length; i++) {
            RadioButton button = new RadioButton(getContext());
            button.setText(messageOptions[i]);
            button.setTag(messageOptions[i]);
            if (app.settings().getDefaultMessage().equals(messageOptions[i])) {
                buttonToCheck = button;
            }
            radioGroup.addView(button);
        }
        buttonToCheck.setChecked(true);
        buttonToCheck.setOnCheckedChangeListener(this);

        removeOptionButton.setEnabled(messageOptions.length != 1);
    }

    private void startAddOptionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        final View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.pref_add_message_dialog_content, (LinearLayout)this.view, false);
        builder.setTitle(R.string.pref_new_apology_dialog_title);
        builder.setView(dialogView);
        builder.setPositiveButton(R.string.pref_new_apology_positive_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText inputText = (EditText) dialogView.findViewById(R.id.new_apology_message_field);
                String content = inputText.getText().toString().trim();
                if (content.length() == 0) {
                    return;
                }
                app.settings().addMessageOption(content);
                updateUI();
            }
        });
        builder.setNegativeButton(R.string.pref_new_apology_negative_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.show();
    }

    private void startRemoveOptionDialog() {
        final String[] options = app.settings().getMessageOptionsArray();

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(R.string.pref_remove_apology_dialog_title);
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                app.settings().removeMessageOption(options[which]);
                updateUI();
            }
        });
        builder.show();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (!isChecked) {
            return;
        }
        String message = (String) buttonView.getTag();
        app.settings().setDefaultMessage(message);
    }
}
