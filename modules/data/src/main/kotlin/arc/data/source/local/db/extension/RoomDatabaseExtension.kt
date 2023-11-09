package arc.data.source.local.db.extension

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import net.sqlcipher.database.SQLiteDatabase.getBytes
import net.sqlcipher.database.SupportFactory

/**
 * [RoomDatabaseExtension] contains extension functions for [RoomDatabase]
 * @author jimlyas
 * @since 0.1.0
 *
 * Copyright © 2022-2023 jimlyas. All rights reserved.
 */
object RoomDatabaseExtension {

	/**
	 * Function to build an instance of [db], that inherit [RoomDatabase]
	 * @receiver [Context]
	 * @param db [RoomDatabase] class that to build
	 * @param databaseName [String] of name for the [RoomDatabase]
	 * @param passPhrase [String] of pass phrase to encrypt the database
	 * @param fallbackMigration [Boolean] define does the database should fall back when there's new version?
	 * @param migrationList [List] of [Migration] of [RoomDatabase] for each new versions
	 * @return instance of [db]
	 */
	inline fun <reified db : RoomDatabase> Context.createDatabase(
		databaseName: String,
		passPhrase: String,
		fallbackMigration: Boolean = false,
		migrationList: List<Migration>? = null
	): db = Room.databaseBuilder(this, db::class.java, databaseName).apply {
		openHelperFactory(SupportFactory(getBytes(passPhrase.toCharArray())))
		if (fallbackMigration) fallbackToDestructiveMigration()
		migrationList?.forEach { migration -> addMigrations(migration) }
	}.build()
}