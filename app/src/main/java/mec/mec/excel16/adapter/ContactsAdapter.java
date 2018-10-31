package mec.mec.excel16.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import mec.mec.excel16.R;
import mec.mec.excel16.models.Contact;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.MyViewHolder>{
    private Context mContext;
    private ArrayList<Contact> contactsList;

    // Constructors
    public ContactsAdapter(Context mContext, ArrayList<Contact> contactsList) {
        this.mContext = mContext;
        this.contactsList = contactsList;
    }
    public ArrayList<Contact> getContacts() {
        return contactsList;
    }

    public void setContacts(ArrayList<Contact> eventList) {
        this.contactsList = eventList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        final Contact event = contactsList.get(position);



        holder.designation.setText(event.getDesignation());
        holder.name.setText(event.getName());
        holder.email.setText(event.getEmail());
        holder.phoneNumber.setText(event.getPhone());


        Picasso.with(mContext).load(event.getImg())
                .placeholder(R.drawable.excel_logo2)
                .error(R.drawable.excel_logo2)
                .into(holder.photo);


    }


    @Override
    public int getItemCount() {
        return contactsList.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout layout;
        public ImageView photo;
        public TextView designation;
        public TextView name;
        public TextView email;
        public TextView phoneNumber;

        private MyViewHolder(View view) {
            super(view);
            layout = view.findViewById(R.id.layout);
            photo = view.findViewById(R.id.photo);
            designation = view.findViewById(R.id.designation);
            name = view.findViewById(R.id.name);
            email = view.findViewById(R.id.email);
            phoneNumber = view.findViewById(R.id.phone_number);
        }
    }
}
