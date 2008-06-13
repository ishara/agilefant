package fi.hut.soberit.agilefant.db;

import java.util.List;
import java.util.Map;

import fi.hut.soberit.agilefant.model.AFTime;
import fi.hut.soberit.agilefant.model.Backlog;
import fi.hut.soberit.agilefant.model.BacklogHourEntry;
import fi.hut.soberit.agilefant.model.BacklogItemHourEntry;

public interface BacklogHourEntryDAO extends GenericDAO<BacklogHourEntry> {
    /**
     * Returns a list of all the hour entries for the specified Backlog.
     */
    public List<BacklogHourEntry> getEntriesByBacklog(Backlog target);
    /**
     * Returns a list of all the hour entries in the specified Backlog.
     */
    public List<BacklogHourEntry> getSumsByBacklog(Backlog backlog); 

}
