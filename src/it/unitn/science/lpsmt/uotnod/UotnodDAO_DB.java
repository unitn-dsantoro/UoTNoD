package it.unitn.science.lpsmt.uotnod;

import it.unitn.science.lpsmt.uotnod.plugins.*;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class UotnodDAO_DB implements UotnodDAO {

	private SQLiteDatabase database;
	private SQLiteHelper dbHelper;
	private String[] allPluginColumns = { SQLiteHelper.PLUGIN_COL_ID,
			SQLiteHelper.PLUGIN_COL_NAME,SQLiteHelper.PLUGIN_COL_LAUNCHER,
			SQLiteHelper.PLUGIN_COL_STATUS,SQLiteHelper.PLUGIN_COL_DESCRIPTION };
	private String[] allUotnodFamilyOrgColumns = { SQLiteHelper.UOTNODFAMILIY_ORG_COL_ID,
			SQLiteHelper.UOTNODFAMILIY_ORG_COL_NAME, SQLiteHelper.UOTNODFAMILIY_ORG_COL_PHONE,
			SQLiteHelper.UOTNODFAMILIY_ORG_COL_MOBILE, SQLiteHelper.UOTNODFAMILIY_ORG_COL_WEBSITE,
			SQLiteHelper.UOTNODFAMILIY_ORG_COL_EMAIL };
	
	@Override
	public void open() {
		if (dbHelper == null){
			dbHelper = new SQLiteHelper(MyApplication.getAppContext());
		}
		database = dbHelper.getWritableDatabase();
	}

	@Override
	public void close() {
		dbHelper.close();
		
	}

	@Override
	public Plugin insertPlugin(Plugin person) {		
		Log.w("WARNING", "insertPlugin: Not implemented");
		return null;
	}

	@Override
	public void deletePlugin(Plugin person) {
		Log.w("WARNING", "deletePlugin: Not implemented");
	}

	@Override
	public List<Plugin> getAllPlugins() {
		List<Plugin> plugins = new ArrayList<Plugin>();
		//Cursor cursor = database.rawQuery("select * from "+SQLiteHelper.TABLE_PLUGIN+";", null);
		Cursor cursor = database.query(SQLiteHelper.TABLE_PLUGIN, allPluginColumns, null, null, null, null, null);
		cursor.moveToFirst();
		while(!cursor.isAfterLast()){
			Plugin plugin = cursorToPlugin(cursor);
			plugins.add(plugin);
			cursor.moveToNext();
		}
		cursor.close(); // Always remember to close the cursor
		return plugins;
	}

	@Override
	public Plugin enablePlugin(Plugin plugin) {		
		plugin.setStatus(true);		
		long insertId = database.update(SQLiteHelper.TABLE_PLUGIN, pluginToValues(plugin), SQLiteHelper.PLUGIN_COL_ID+"=?", new String[]{String.valueOf(plugin.getId())});	
		// Now read from DB the inserted person and return it
		Cursor cursor = database.query(SQLiteHelper.TABLE_PLUGIN,allPluginColumns,SQLiteHelper.PLUGIN_COL_ID + " =?",new String[]{""+insertId},null,null,null);
		cursor.moveToFirst();
		Plugin p = cursorToPlugin(cursor);
		cursor.close();
		return p;		
	}

	@Override
	public Plugin disablePlugin(Plugin plugin) {
		plugin.setStatus(false);		
		long insertId = database.update(SQLiteHelper.TABLE_PLUGIN, pluginToValues(plugin), SQLiteHelper.PLUGIN_COL_ID+"=?", new String[]{String.valueOf(plugin.getId())});
		// Now read from DB the inserted person and return it
		Cursor cursor = database.query(SQLiteHelper.TABLE_PLUGIN,allPluginColumns,SQLiteHelper.PLUGIN_COL_ID + " =?",new String[]{""+insertId},null,null,null);
		cursor.moveToFirst();
		Plugin p = cursorToPlugin(cursor);
		cursor.close();
		return p;
	}

	public Plugin cursorToPlugin(Cursor cursor) {
		long id = cursor.getLong(0);
		String name = cursor.getString(1);
		String className = cursor.getString(2);
		Boolean status = false;
		if ( cursor.getString(3) == "1" ) {
			status = true;
		}		
		String description = cursor.getString(4);
		return new Plugin(id,name,className,status,description);
	}
	
	public ContentValues pluginToValues(Plugin plugin) {
		ContentValues values = new ContentValues();
		values.put(SQLiteHelper.PLUGIN_COL_NAME, plugin.getName());
		values.put(SQLiteHelper.PLUGIN_COL_ID, plugin.getId());
		Integer status = 0;
		if ( plugin.getStatus() ) {
			status = 1;
		}
		values.put(SQLiteHelper.PLUGIN_COL_STATUS, status);
		return values;
	}
	
	@Override
	public UotnodFamilyOrg insertFamilyOrg(UotnodFamilyOrg organization) {
		long insertId = organization.getOrgId();
		UotnodFamilyOrg myOrg = getFamilyOrgById(insertId);
		if (myOrg != null){
			Log.d(MyApplication.DEBUGTAG,"Updating DB, replacing: " + myOrg.toString() + " with: " + organization.toString());
			//TODO: invece che cancellare e inserire qui si dovrebbe aggiornare il record, am sembra funzionare anche così - 140111
			insertId = database.delete(SQLiteHelper.TABLE_UOTNODFAMILIY_ORG, SQLiteHelper.UOTNODFAMILIY_ORG_COL_ID + " =?",new String[]{""+myOrg.getOrgId()});
			//insertId = database.update(SQLiteHelper.TABLE_UOTNODFAMILIY_ORG, orgToValuesNoId(organization), SQLiteHelper.UOTNODFAMILIY_ORG_COL_ID + " =?", new String[]{""+myOrg.getOrgId()});
		}		
		insertId = database.insert(SQLiteHelper.TABLE_UOTNODFAMILIY_ORG, null, orgToValues(organization));		
		// Now read from DB the inserted person and return it
		Cursor cursor = database.query(SQLiteHelper.TABLE_UOTNODFAMILIY_ORG,allUotnodFamilyOrgColumns,SQLiteHelper.UOTNODFAMILIY_ORG_COL_ID + " =?",new String[]{""+insertId},null,null,null);		
		cursor.moveToFirst();
		UotnodFamilyOrg o = cursorToOrg(cursor);
		cursor.close();
		return o;
	}
	
	@Override
	public UotnodFamilyOrg getFamilyOrgById(long id) {
		Cursor cursor = database.query(SQLiteHelper.TABLE_UOTNODFAMILIY_ORG,allUotnodFamilyOrgColumns,SQLiteHelper.UOTNODFAMILIY_ORG_COL_ID + " =?",new String[]{""+id},null,null,null);
		UotnodFamilyOrg o = null;		
		if (cursor.getCount() > 0) {
			cursor.moveToFirst();
			o = cursorToOrg(cursor);
			cursor.close();
		}		
		return o;
	}

	private UotnodFamilyOrg cursorToOrg(Cursor cursor) {
		long id = cursor.getLong(0);
		String name = cursor.getString(1);
		String phone = cursor.getString(2);
		String mobile = cursor.getString(3);
		String website = cursor.getString(4);
		String email = cursor.getString(5);		
		return new UotnodFamilyOrg(id, name, phone, mobile, website, email);
	}

	private ContentValues orgToValues(UotnodFamilyOrg organization) {
		ContentValues values = new ContentValues();
		values.put(SQLiteHelper.UOTNODFAMILIY_ORG_COL_EMAIL, organization.getEmail());
		values.put(SQLiteHelper.UOTNODFAMILIY_ORG_COL_ID, organization.getOrgId());
		values.put(SQLiteHelper.UOTNODFAMILIY_ORG_COL_MOBILE, organization.getMobile());
		values.put(SQLiteHelper.UOTNODFAMILIY_ORG_COL_NAME, organization.getName());
		values.put(SQLiteHelper.UOTNODFAMILIY_ORG_COL_PHONE, organization.getPhone());
		values.put(SQLiteHelper.UOTNODFAMILIY_ORG_COL_WEBSITE, organization.getWebsite());		
		return values;
	}
		
	@Override
	public List<Entry> getAllFamilyOrgs() {
		List<Entry> organizations = new ArrayList<Entry>();
		//Cursor cursor = database.rawQuery("select * from "+SQLiteHelper.TABLE_PLUGIN+";", null);
		Cursor cursor = database.query(SQLiteHelper.TABLE_UOTNODFAMILIY_ORG, allUotnodFamilyOrgColumns, null, null, null, null, null);
		cursor.moveToFirst();
		while(!cursor.isAfterLast()){
			UotnodFamilyOrg organization = cursorToOrg(cursor);
			organizations.add(organization);
			cursor.moveToNext();
		}
		cursor.close(); // Always remember to close the cursor
		return organizations;
	}

}