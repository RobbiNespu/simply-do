package kdk.android.simplydo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class DeleteInactiveAction
{
    private Activity activity;
    private DataViewer dataViewer;
    private ListPropertiesAdapter listPropertiesAdapter;
    private ItemPropertiesAdapter itemPropertiesAdapter;
    
    private AlertDialog.Builder dialogBuilder;

    public DeleteInactiveAction(
            Activity activity, 
            DataViewer dataViewer,
            ListPropertiesAdapter listPropertiesAdapter, 
            ItemPropertiesAdapter itemPropertiesAdapter)
    {
        this.activity = activity;
        this.dataViewer = dataViewer;
        this.listPropertiesAdapter = listPropertiesAdapter;
        this.itemPropertiesAdapter = itemPropertiesAdapter;
        
        dialogBuilder = new AlertDialog.Builder(this.activity);
        dialogBuilder.setMessage("Are you sure you want to delete all inactive items in this list?")
               .setCancelable(true)
               .setTitle("Delete Inactive?")
               .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       deleteInactive();
                   }
               })
               .setNegativeButton("No", null);
    }

    public void deleteInactive(int dialogId)
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity.getBaseContext());
        boolean confirmDeleteInactive = prefs.getBoolean("confirmDeleteInactive", true);
        
        if(confirmDeleteInactive)
        {
            activity.showDialog(dialogId);
        }
        else
        {
            deleteInactive();
        }
    }
    
    public Dialog createDialog()
    {
        return dialogBuilder.create();
    }
    
    private void deleteInactive()
    {
        dataViewer.deleteInactive();
        itemPropertiesAdapter.notifyDataSetChanged();
        listPropertiesAdapter.notifyDataSetChanged();
    }
}
