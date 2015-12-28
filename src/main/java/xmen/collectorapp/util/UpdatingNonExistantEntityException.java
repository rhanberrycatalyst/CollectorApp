package xmen.collectorapp.util;

import javax.persistence.PersistenceException;

public class UpdatingNonExistantEntityException extends PersistenceException {
	private static final long serialVersionUID = 1L;

	public UpdatingNonExistantEntityException() {
        super("Tried to update a non existant entity in database.");
    }
}
