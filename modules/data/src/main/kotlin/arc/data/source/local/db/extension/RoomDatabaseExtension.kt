package arc.data.source.local.db.extension

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SQLiteDatabaseHook
import net.sqlcipher.database.SupportFactory

/**
 * [RoomDatabaseExtension] contains extension functions for [RoomDatabase]
 * @author jimlyas
 * @since 08 Feb 2023
 *
 * Copyright © 2022-2023 jimlyas. All rights reserved.
 */
object RoomDatabaseExtension {

	/**
	 * Function to build an instance of [db], that inherit [RoomDatabase]
	 * @param db [RoomDatabase] class that to build
	 * @param context [Context] of current application
	 * @param databaseName [String] of name for the [RoomDatabase]
	 * @param passPhrase [String] of pass phrase to encrypt the database
	 * @param fallbackMigration [Boolean] define does the database should fall back when there's new version?
	 * @param useMemoryProtection [Boolean] define does the database will use memory protection, you can disable it to improve performance
	 * @param migrationList [List] of [Migration] of [RoomDatabase] for each new versions
	 * @return instance of [db]
	 */
	inline fun <reified db : RoomDatabase> createDatabase(
		context: Context,
		databaseName: String,
		passPhrase: String,
		fallbackMigration: Boolean = false,
		useMemoryProtection: Boolean = false,
		migrationList: List<Migration>? = null
	): db = Room.databaseBuilder(context, db::class.java, databaseName).apply {
		openHelperFactory(
			SupportFactory(
				SQLiteDatabase.getBytes(passPhrase.toCharArray()),
				object : SQLiteDatabaseHook {
					override fun preKey(database: SQLiteDatabase?) = Unit

					override fun postKey(database: SQLiteDatabase?) {
						database?.rawExecSQL(
							if (useMemoryProtection) "PRAGMA cipher_memory_security = ON"
							else "PRAGMA cipher_memory_security = OFF"
						)
					}
				})
		)
		if (fallbackMigration) fallbackToDestructiveMigration()
		migrationList?.forEach { migration -> addMigrations(migration) }
	}.build()
}