package com.example.btlandroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class TrichDanAdapter extends ArrayAdapter<TrichDan> {
    public TrichDanAdapter(Context context, List<TrichDan> quotes) {
        super(context, 0, quotes);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TrichDan quote = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_list_trichdan, parent, false);
        }

        TextView txtQuote = convertView.findViewById(R.id.txtQuote);
        TextView txtBookInfo = convertView.findViewById(R.id.txtBookInfo);

        txtQuote.setText(quote.getCauNoi());

        if (quote.getSach() != null) {
            txtBookInfo.setText(quote.getSach().getTenSach() + " - " + quote.getSach().getTacGia());
        } else {
            txtBookInfo.setText("Sách không tồn tại");
        }

        return convertView;
    }


}
