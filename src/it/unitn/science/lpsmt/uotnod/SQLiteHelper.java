package it.unitn.science.lpsmt.uotnod;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLiteHelper extends SQLiteOpenHelper{

	
	// Plugins constants
	public static final String TABLE_PLUGIN="plugin";
	public static final String PLUGIN_COL_ID="id";
	public static final String PLUGIN_COL_NAME="name";
	public static final String PLUGIN_COL_LAUNCHER="launcher";
	public static final String PLUGIN_COL_DESCRIPTION="description";
	public static final String PLUGIN_COL_STATUS="status";
	public static final String PLUGIN_COL_DATASRC="datasrc";
	public static final String PLUGIN_COL_EMPTY="empty";
	
	// Table plugin creation statement
	private static final String PLUGIN_TABLE_CREATE="create table "
			+ TABLE_PLUGIN + "("
			+ PLUGIN_COL_ID + " integer primary key autoincrement,"
			+ PLUGIN_COL_NAME + " text not null,"
			+ PLUGIN_COL_LAUNCHER + " text not null,"
			+ PLUGIN_COL_STATUS + " integer not null,"
			+ PLUGIN_COL_DESCRIPTION + " text not null,"
			+ PLUGIN_COL_DATASRC + " text not null,"
			+ PLUGIN_COL_EMPTY + " integer not null"
			+ ")";
	
	// Table plugins initialization sql statement
	private static final String PLUGIN_TABLE_INITIALIZE="insert into "
			+ TABLE_PLUGIN + " ("
			+ PLUGIN_COL_NAME + ","
			+ PLUGIN_COL_LAUNCHER + ","
			+ PLUGIN_COL_STATUS + ","
			+ PLUGIN_COL_DESCRIPTION + ","
			+ PLUGIN_COL_DATASRC + ","
			+ PLUGIN_COL_EMPTY + ")"
			+ " SELECT "
			+ "'Attività per famiglie'" + " AS " + PLUGIN_COL_NAME + ","
			+ "'Family'" + " AS " + PLUGIN_COL_LAUNCHER + ","
			+ "1" + " AS " + PLUGIN_COL_STATUS + ","
			+ "'family desc'" + " AS " + PLUGIN_COL_DESCRIPTION + ","
			+ "'http://dati.trentino.it/storage/f/2013-05-08T083538/Estate-giovani-e-famiglia_2013.xml'" + " AS " + PLUGIN_COL_DATASRC + ","
			+ "1" + " AS " + PLUGIN_COL_EMPTY
			+ " "
			+ " UNION SELECT "
			+ "'Esercizi pubblici'" + " AS " + PLUGIN_COL_NAME + ","
			+ "'Shops'" + " AS " + PLUGIN_COL_LAUNCHER + ","
			+ "1" + " AS " + PLUGIN_COL_STATUS + ","
			+ "'eser pub desc'" + " AS " + PLUGIN_COL_DESCRIPTION + ","
			+ "''" + " AS " + PLUGIN_COL_DATASRC + ","
			+ "1" + " AS " + PLUGIN_COL_EMPTY
			+ " "
			+ " UNION SELECT "
			+ "'Meteo'" + " AS " + PLUGIN_COL_NAME + ","
			+ "'Weather'" + " AS " + PLUGIN_COL_LAUNCHER + ","
			+ "1" + " AS " + PLUGIN_COL_STATUS + ","
			+ "'meteo desc'" + " AS " + PLUGIN_COL_DESCRIPTION + ","
			+ "''" + " AS " + PLUGIN_COL_DATASRC + ","
			+ "1" + " AS " + PLUGIN_COL_EMPTY
			+ " "
			+ " UNION SELECT "
			+ "'Devel activity'" + " AS " + PLUGIN_COL_NAME + ","
			+ "'DOMParser'" + " AS " + PLUGIN_COL_LAUNCHER + ","
			+ "0" + " AS " + PLUGIN_COL_STATUS + ","
			+ "'Just a starting poin for devel activities'" + " AS " + PLUGIN_COL_DESCRIPTION + ","
			+ "''" + " AS " + PLUGIN_COL_DATASRC + ","
			+ "1" + " AS " + PLUGIN_COL_EMPTY
			+ " ";
	
	// Uotnod family plugin constants
	// Organization (Uotnod family plugin) constants
	public static final String TABLE_UOTNODFAMILIY_ORG="uotnodFamilyOrg";
	public static final String UOTNODFAMILIY_ORG_COL_ID="orgId";
	public static final String UOTNODFAMILIY_ORG_COL_NAME="name";
	public static final String UOTNODFAMILIY_ORG_COL_PHONE="phone";
	public static final String UOTNODFAMILIY_ORG_COL_MOBILE="mobile";
	public static final String UOTNODFAMILIY_ORG_COL_WEBSITE="website";
	public static final String UOTNODFAMILIY_ORG_COL_EMAIL="email";
	
	// Table organization (Uotnod family plugin) creation statement
		private static final String UOTNODFAMILIY_ORG_CREATE="create table "
				+ TABLE_UOTNODFAMILIY_ORG + "("
				+ UOTNODFAMILIY_ORG_COL_ID + " integer primary key,"
				+ UOTNODFAMILIY_ORG_COL_NAME + " text not null,"
				+ UOTNODFAMILIY_ORG_COL_PHONE + " text,"
				+ UOTNODFAMILIY_ORG_COL_MOBILE + " text,"
				+ UOTNODFAMILIY_ORG_COL_WEBSITE + " text,"
				+ UOTNODFAMILIY_ORG_COL_EMAIL + " text );";
			
		// Table plugins initialization sql statement
		/*private static final String UOTNODFAMILIY_ORG_INITIALIZE="insert into "
				+ TABLE_PLUGIN + " ("
				+ PLUGIN_COL_NAME + ","
				+ PLUGIN_COL_LAUNCHER + ","
				+ PLUGIN_COL_STATUS + ","
				+ PLUGIN_COL_DESCRIPTION + ")"
				+ " SELECT "
				+ "'Attività per famiglie'" + " AS " + PLUGIN_COL_NAME + ","
				+ "'UotnodFamily'" + " AS " + PLUGIN_COL_LAUNCHER + ","
				+ "1" + " AS " + PLUGIN_COL_STATUS + ","
				+ "'family desc'" + " AS " + PLUGIN_COL_DESCRIPTION
				+ " "
				+ " UNION SELECT "
				+ "'Esercizi pubblici'" + " AS " + PLUGIN_COL_NAME + ","
				+ "'UotnodShops'" + " AS " + PLUGIN_COL_LAUNCHER + ","
				+ "1" + " AS " + PLUGIN_COL_STATUS + ","
				+ "'eser pub desc'" + " AS " + PLUGIN_COL_DESCRIPTION
				+ " "
				+ " UNION SELECT "
				+ "'Meteo'" + " AS " + PLUGIN_COL_NAME + ","
				+ "'UotnodWheater'" + " AS " + PLUGIN_COL_LAUNCHER + ","
				+ "1" + " AS " + PLUGIN_COL_STATUS + ","
				+ "'meteo desc'" + " AS " + PLUGIN_COL_DESCRIPTION
				+ " ";
		*/
	
	// Shared constants
	public static final String DATABASE_NAME="uotnod.db";
	public static final int DATABASE_VERSION = 18;

	// Database creation sql statement
	private static final String DATABASE_CREATE = PLUGIN_TABLE_CREATE		
			+ "\n" + UOTNODFAMILIY_ORG_CREATE;

	// Database creation sql statement
	private static final String DATABASE_INITIALIZE = PLUGIN_TABLE_INITIALIZE;

	
	public SQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		//db.execSQL(DATABASE_CREATE);
		db.execSQL(PLUGIN_TABLE_CREATE);
		Log.d(MyApplication.DEBUGTAG,PLUGIN_TABLE_CREATE);
		db.execSQL(UOTNODFAMILIY_ORG_CREATE);
		db.execSQL(DATABASE_INITIALIZE);	
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(SQLiteHelper.class.getName(),"Upgrading database from version "+ oldVersion + " to "
				+ newVersion + ", which will destroy all data");
	// TODO ciclare per rimuovere tutte le tabelle
		db.execSQL("DROP TABLE IF EXISTS "+ TABLE_PLUGIN);
		db.execSQL("DROP TABLE IF EXISTS "+ TABLE_UOTNODFAMILIY_ORG);		
		onCreate(db);		
	}
	
}
