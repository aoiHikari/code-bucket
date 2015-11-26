/**
 * This file is part of the source code and related artifacts for eGym Application.
 * <p>
 * Copyright © 2013 eGym GmbH
 */
package mongo;

import javax.annotation.Nonnull;

import com.mongodb.CommandResult;
import com.mongodb.DB;
import com.mongodb.annotations.Immutable;

@Immutable
public final class MongoHelper {

	private MongoHelper() {
		throw new IllegalStateException("Should not be initialized!");
	}

	/**
	 * Checks if the current database is running on the master (PRIMARY) node.
	 *
	 * @return true if the database is running on the primary node, false otherwise (not PRIMARY, or cannot be resolved).
	 */
	public static boolean isMaster(@Nonnull final DB db) {
		final CommandResult isMasterResult = db.command("isMaster");
		if (isMasterResult == null) {
			return false;
		}
		final Object isMasterObject = isMasterResult.get("ismaster");
		if (isMasterObject instanceof Boolean) {
			return (boolean) isMasterObject;
		}

		final Object primaryNode = isMasterResult.get("primary");
		final Object currentNode = isMasterResult.get("me");

		return primaryNode != null && primaryNode.equals(currentNode);
	}
}
