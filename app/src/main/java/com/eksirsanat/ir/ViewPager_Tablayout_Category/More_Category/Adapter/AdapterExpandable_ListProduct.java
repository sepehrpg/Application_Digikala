package com.eksirsanat.ir.ViewPager_Tablayout_Category.More_Category.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.eksirsanat.ir.R;
import com.eksirsanat.ir.ViewPager_Tablayout_Category.More_Category.ItemModel_Server;

import java.util.HashMap;
import java.util.List;

public class AdapterExpandable_ListProduct extends BaseExpandableListAdapter {

    List<String> Header;

    //List<ItemModel_Server> Items;

    HashMap<String,List<ItemModel_Server> > ListItems=new HashMap<>();


    Context context;

    public AdapterExpandable_ListProduct(Context context,List<String> Header,HashMap<String,List<ItemModel_Server> > ListItems){

        this.context=context;
        this.Header=Header;
        this.ListItems=ListItems;

    }


    @Override
    public int getGroupCount() {
        return Header.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {

        //HeadModel_Server headModel_server=Header.get(groupPosition);

        //int size=ListItems.get(headModel_server.getName()).size();

        return  ListItems.get(Header.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {

        return Header.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return ListItems.get(Header.get(groupPosition)).get(childPosition);
        //return ListItems.get(Header.get(groupPosition));

    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {

        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        //HeadModel_Server headModel_server=Header.get(groupPosition);
        //String headerTitle = headModel_server.getName();
        String headerTitle =Header.get(groupPosition);


        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.listTitle);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;



    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {



        String childText=ListItems.get(Header.get(groupPosition)).get(childPosition).getName();


        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item, null);
        }

        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.expandedListItem);

        txtListChild.setText(childText);
        return convertView;


    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true; //very import
    }
}
