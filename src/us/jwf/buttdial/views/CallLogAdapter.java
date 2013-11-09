package us.jwf.buttdial.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import us.jwf.buttdial.App;
import us.jwf.buttdial.R;
import us.jwf.buttdial.models.Call;
import us.jwf.buttdial.utils.Utils;

import java.util.List;

/**
 *
 */
public class CallLogAdapter extends BaseAdapter {
    private Context context;
    private App app;
    private List<Call> calls;

    public CallLogAdapter(Context context) {
        this.context = context;
        this.app = (App) context.getApplicationContext();
    }

    public void refresh() {
        calls = this.app.calls().getRecentCalls();
    }

    public void refresh(boolean notify){
        refresh();
        if(notify){
            this.notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        if (calls == null) {
            refresh();
        }
        return calls.size();
    }

    @Override
    public Call getItem(int position) {
        if (calls == null) {
            refresh();
        }
        return calls.get(position);
    }

    @Override
    public long getItemId(int position) {
        if (calls == null) {
            refresh();
        }

        return calls.get(position).date;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CallLogItem tag;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.calllog_item, parent, false);
            tag = new CallLogItem(convertView);
            convertView.setTag(tag);
        } else {
            tag = (CallLogItem) convertView.getTag();
        }

        Call item = getItem(position);
        if (item.name == null || item.name.equals("")) {
            tag.name.setText(app.getResources().getString(R.string.unknown));
        } else {
            tag.name.setText(item.name);
        }
        tag.number.setText(Utils.phoneNumber(item.number));
        tag.when.setText(Utils.relativeTime(item.date));
        tag.duration.setText(Utils.callDuration(item.duration));

        return convertView;
    }

    private class CallLogItem {
        public TextView name;
        public TextView number;
        public TextView when;
        public TextView duration;

        public CallLogItem(View view) {
            name = (TextView) view.findViewById(R.id.name);
            number = (TextView) view.findViewById(R.id.number);
            when = (TextView) view.findViewById(R.id.when);
            duration = (TextView) view.findViewById(R.id.duration);
        }
    }
}
