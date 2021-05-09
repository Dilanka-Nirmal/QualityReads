package com.example.qualityreads.AdminSession;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.qualityreads.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.valdesekamdem.library.mdtoast.MDToast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import models.Drivers;


public class DriverListFragment extends Fragment {

    Button button;
    ListView listView;
    List<Drivers> user;
    DatabaseReference ref;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v2 = inflater.inflate(R.layout.fragment_driver_list, container, false);

        button=(Button)v2.findViewById(R.id.addriver);
        listView = (ListView)v2.findViewById(R.id.driverlist);

        user = new ArrayList<>();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new NewDriverFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.admin_fragment_container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        ref = FirebaseDatabase.getInstance().getReference("Drivers");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user.clear();

                for (DataSnapshot studentDatasnap : dataSnapshot.getChildren()){

                    Drivers drivers = studentDatasnap.getValue(Drivers.class);
                    user.add(drivers);
                }

                MyAdapter adapter = new MyAdapter(getContext(),R.layout.driver_custom, (ArrayList<Drivers>) user);
                listView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return v2;

    }

    static class ViewHolder {

        CircleImageView circleImageView;
        TextView COL1;
        TextView COL2;
        TextView COL3;
        TextView COL4;
        TextView COL5;
        ImageButton imageButton1;
        ImageButton imageButton2;
        ImageButton imageButton3;


    }


    class MyAdapter extends ArrayAdapter<Drivers> {
        LayoutInflater inflater;
        Context myContext;
        List<Map<String, String>> newList;
        List<Drivers> user;


        public MyAdapter(Context context, int resource, ArrayList<Drivers> objects) {
            super(context, resource, objects);
            myContext = context;
            user = objects;
            inflater = LayoutInflater.from(context);
            int y;
            String barcode;
        }

        @SuppressLint("SetTextI18n")
        @Override
        public View getView(int position, View view, ViewGroup parent) {
            final ViewHolder holder;
            if (view == null) {
                holder = new ViewHolder();
                view = inflater.inflate(R.layout.driver_custom, null);

                holder.COL1 = (TextView) view.findViewById(R.id.name);
                holder.COL2 = (TextView) view.findViewById(R.id.email);
                holder.COL3 = (TextView) view.findViewById(R.id.vehicleno);
                holder.COL4 = (TextView) view.findViewById(R.id.contact);
                holder.COL5 = (TextView) view.findViewById(R.id.address);
                holder.circleImageView = (CircleImageView) view.findViewById(R.id.propic);
                holder.imageButton1 = (ImageButton)view.findViewById(R.id.edit);
                holder.imageButton2 = (ImageButton)view.findViewById(R.id.delete);
                holder.imageButton3 = (ImageButton)view.findViewById(R.id.assign);

                view.setTag(holder);
            } else {

                holder = (ViewHolder) view.getTag();
            }

            holder.COL1.setText("Name:-"+user.get(position).getName());
            holder.COL2.setText("Email:-"+user.get(position).getEmail());
            holder.COL3.setText("Vehicle No:-"+user.get(position).getVehicleno());
            holder.COL4.setText("Contact:-"+user.get(position).getContact());
            holder.COL5.setText("Address:-"+user.get(position).getAddress());
            Picasso.get().load(user.get(position).getImage()).into(holder.circleImageView);
            System.out.println(holder);

            final String idd = user.get(position).getId();

            holder.imageButton3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                            .setTitle("Do you want to assign this driver?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    MDToast mdToast = MDToast.makeText(getActivity(), "Driver assigned successfully", MDToast.LENGTH_SHORT, MDToast.TYPE_SUCCESS);
                                    mdToast.show();
                                }
                            })

                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            })
                            .show();
                }
            });

            holder.imageButton2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                            .setTitle("Do you want to delete this driver details?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {


                                    FirebaseDatabase.getInstance().getReference("Drivers").child(idd).removeValue();
                                    Toast.makeText(myContext, "Deleted successfully", Toast.LENGTH_SHORT).show();

                                    Fragment fragment = new DriverListFragment();
                                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                    fragmentTransaction.replace(R.id.admin_fragment_container, fragment);
                                    fragmentTransaction.addToBackStack(null);
                                    fragmentTransaction.commit();
                                }
                            })

                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            })
                            .show();
                }
            });

            holder.imageButton1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
                    View view1 = inflater.inflate(R.layout.update_driver,null);
                    dialogBuilder.setView(view1);

                    final EditText editText1 = (EditText)view1.findViewById(R.id.updid);
                    final EditText editText2 = (EditText)view1.findViewById(R.id.updname);
                    final EditText editText3 = (EditText)view1.findViewById(R.id.updemail);
                    final EditText editText4 = (EditText)view1.findViewById(R.id.updvehicleNo);
                    final EditText editText5 = (EditText)view1.findViewById(R.id.updcontact);
                    final EditText editText6 = (EditText)view1.findViewById(R.id.updaddress);
                    final CircleImageView circleImageView = (CircleImageView) view1.findViewById(R.id.updriver);
                    Button button = (Button)view1.findViewById(R.id.upaddd);

                    final AlertDialog alertDialog = dialogBuilder.create();
                    alertDialog.show();

                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Drivers").child(idd);
                    System.out.println(reference);
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            System.out.println("fffffffffffffff");
                            String id = snapshot.child("id").getValue().toString();
                            System.out.println("1");
                            String name = snapshot.child("name").getValue().toString();
                            System.out.println("2");
                            String email = snapshot.child("email").getValue().toString();
                            System.out.println("3");
                            String vehicle = snapshot.child("vehicleno").getValue().toString();
                            String contact = snapshot.child("contact").getValue().toString();
                            String address = snapshot.child("address").getValue().toString();
                            String image = snapshot.child("image").getValue().toString();

                            System.out.println(id);

                            editText1.setText(id);
                            editText2.setText(name);
                            editText3.setText(email);
                            editText4.setText(vehicle);
                            editText5.setText(contact);
                            editText6.setText(address);
                            Picasso.get().load(image).into(circleImageView);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            String id = editText1.getText().toString();
                            String name = editText2.getText().toString();
                            String email = editText3.getText().toString();
                            String vehicle = editText4.getText().toString();
                            String contact = editText5.getText().toString();
                            String address = editText6.getText().toString();


                            if(id.equals("")) {
                                editText1.setError("ID is required");
                            }if(name.equals("")){
                                editText2.setError("Name is required");
                            }if(email.equals("")){
                                editText3.setError("Email is required");
                            }if(vehicle.equals("")){
                                editText4.setError("Vehicle number is required");
                            }if(contact.equals("")){
                                editText5.setError("Contact number is required");
                            }if(address.equals("")){
                                editText6.setError("Address is required");
                            }else {

                                HashMap map = new HashMap();
                                map.put("id", id);
                                map.put("name",name);
                                map.put("email",email);
                                map.put("vehicleno",vehicle);
                                map.put("contact",contact);
                                map.put("address",address);
                                reference.updateChildren(map);

                                Toast.makeText(getActivity(), "Driver details updated successfully", Toast.LENGTH_SHORT).show();

                                alertDialog.dismiss();
                            }
                        }
                    });
                }
            });

            return view;
        }
    }
}












