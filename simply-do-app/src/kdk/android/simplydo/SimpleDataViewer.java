/*
 * Copyright (C) 2010 Keith Kildare
 * 
 * This file is part of SimplyDo.
 * 
 * SimplyDo is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * SimplyDo is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with SimplyDo.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package kdk.android.simplydo;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

public class SimpleDataViewer implements DataViewer
{
    private DataManager dataManager;
    
    private List<ItemDesc> itemData = new ArrayList<ItemDesc>();
    private List<ListDesc> listData = new ArrayList<ListDesc>();
    
    private ListDesc selectedList;

    
    public SimpleDataViewer(DataManager dataManager)
    {
        this.dataManager = dataManager;
    }
    
    
    public List<ItemDesc> getItemData()
    {
        return itemData;
    }

    
    public List<ListDesc> getListData()
    {
        return listData;
    }
    
    public void setSelectedList(ListDesc selectedList)
    {
        this.selectedList = selectedList;
        if(selectedList == null)
        {
            itemData.clear();
        }
        else
        {
            fetchItems(selectedList.getId());
        }           
    }
    
    public ListDesc getSelectedList()
    {
        return selectedList;
    }
    
    public void fetchLists()
    {
        listData = dataManager.fetchLists();
    }
    
    public void fetchItems(int listId)
    {
        itemData = dataManager.fetchItems(listId);
    }
    
    public void updateItemActiveness(int itemId, boolean active)
    {
        dataManager.updateItemActiveness(itemId, active);
        if(selectedList != null)
        {
            fetchItems(selectedList.getId());
        }
        fetchLists();
    }
    
    public void updateItemStarness(int itemId, boolean star)
    {
        dataManager.updateItemStarness(itemId, star);
        if(selectedList != null)
        {
            fetchItems(selectedList.getId());
        }
        fetchLists();
    }
    
    public void updateItemLabel(int itemId, String newLabel)
    {
        dataManager.updateItemLabel(itemId, newLabel);
        if(selectedList != null)
        {
            fetchItems(selectedList.getId());
        }
    }
    
    public void updateListLabel(int listId, String newLabel)
    {
        dataManager.updateListLabel(listId, newLabel);
        fetchLists();
    }
    
    public void createList(String label)
    {
        dataManager.createList(label);
        fetchLists();
    }
    
    public void createItem(String label)
    {
        int listId = selectedList.getId();
        dataManager.createItem(listId, label);
        fetchItems(listId);
    }
    
    public void deleteInactive()
    {
        if(selectedList == null)
        {
            Log.e(L.TAG, "deleteInactive() called but no list is selected");
            return;
        }

        int listId = selectedList.getId();
        dataManager.deleteInactive(listId);
        fetchItems(listId);
        fetchLists();    
    }
    
    public void deleteList(int listId)
    {
        dataManager.deleteList(listId);
        fetchLists();
    }
    
    public void deleteItem(int itemId)
    {
        dataManager.deleteItem(itemId);
        if(selectedList != null)
        {
            fetchItems(selectedList.getId());
        }
        fetchLists();
    }
    
    public void close()
    {
        // Do nothing
    }

}
