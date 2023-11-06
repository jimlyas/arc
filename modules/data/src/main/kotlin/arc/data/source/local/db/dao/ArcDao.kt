package arc.data.source.local.db.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Update
import arc.data.source.local.db.DbService
import arc.data.source.local.db.model.DbEntity

/**
 * [ArcDao] generalize implementation of insert, update, and delete [entity] using [androidx.room.Room]
 * database.
 *
 * For reading data purposes, please implement your own inside your DAO after inherit [ArcDao]
 *
 * @author jimlyas
 * @since 08 Feb 2023
 * @param entity [Object] that inherit [DbEntity] that will be manipulated using this DAO
 *
 * Copyright © 2022-2023 jimlyas. All rights reserved.
 */
interface ArcDao<entity : DbEntity> : DbService {

	/**
	 * Function to store [entity] to the database
	 * @param data instance of [entity] to be saved
	 */
	@Insert(onConflict = REPLACE)
	fun insert(data: entity)

	/**
	 * Function to update [entity] value inside the database
	 * @param data instance of [entity] to be updated
	 */
	@Update
	fun update(vararg data: entity)

	/**
	 * Function to remove an [entity] from the database
	 * @param data instance of [entity] to be removed
	 */
	@Delete
	fun remove(vararg data: entity)
}