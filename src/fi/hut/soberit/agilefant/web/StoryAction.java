package fi.hut.soberit.agilefant.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

import fi.hut.soberit.agilefant.business.StoryBusiness;
import fi.hut.soberit.agilefant.business.TransferObjectBusiness;
import fi.hut.soberit.agilefant.model.Backlog;
import fi.hut.soberit.agilefant.model.Story;
import fi.hut.soberit.agilefant.model.StoryState;
import fi.hut.soberit.agilefant.model.Task;
import fi.hut.soberit.agilefant.model.User;
import fi.hut.soberit.agilefant.transfer.StoryTO;
import fi.hut.soberit.agilefant.util.StoryMetrics;

@Component("storyAction")
@Scope("prototype")
public class StoryAction extends ActionSupport implements CRUDAction, Prefetching {

    private static final long serialVersionUID = -4289013472775815522L;

    private int backlogId = 0;

    private Integer storyId;

    private StoryState state;
    
    private int iterationId;

    private int priority;

    private Story story;

    private Backlog backlog;

    private Set<Integer> userIds = new HashSet<Integer>();
    
    private boolean usersChanged = false;
    
    private Collection<Task> storyContents = new ArrayList<Task>();
    
    private String storyListContext;
    
    private StoryMetrics metrics;
    
    private boolean moveTasks;

    @Autowired
    private StoryBusiness storyBusiness;
    
    @Autowired
    private TransferObjectBusiness transferObjectBusiness;


    @Override
    public String execute() throws Exception {
        story = new Story();
        return super.execute();
    }
    
    // CRUD
    
    public String create() {
        story = this.storyBusiness.create(story, backlogId, userIds);
        return Action.SUCCESS;
    }

    public String delete() {
        story = storyBusiness.retrieve(storyId);
        storyBusiness.delete(story.getId());
        return Action.SUCCESS;
    }

    public String retrieve() {
        story = storyBusiness.retrieve(storyId);
        story = this.toTransferObject(story);
        return Action.SUCCESS;
    }

    public String store() {
        Set<Integer> users = null;
        if (usersChanged) {
            users = this.userIds;
        }
        story = storyBusiness.store(storyId, story, null, users);
//        story = this.toTransferObject(story);
        return Action.SUCCESS;
    }
    
    
    // OTHER FUNCTIONS
    
    public String moveStory() {
        storyBusiness.attachStoryToBacklog(storyId, backlogId, moveTasks);
        return Action.SUCCESS;
    }
    
    public String storyContents() {
        storyContents = storyBusiness.getStoryContents(storyId, iterationId);
        return Action.SUCCESS;
    }

    public String metrics() {
        if (storyId > 0) {
            metrics = storyBusiness.calculateMetrics(storyId);
        } else {
            metrics = storyBusiness.calculateMetricsWithoutStory(iterationId);
        }
        return Action.SUCCESS;
    }

    private StoryTO toTransferObject(Story story) {
        Collection<User> responsibles = storyBusiness.getStorysProjectResponsibles(story);
        return transferObjectBusiness.constructStoryTO(story, responsibles);
    }

    // PREFETCHING
    
    public String getIdFieldName() {
        return "storyId";
    }
    
    public void initializePrefetchedData(int objectId) {
        story = storyBusiness.retrieve(objectId);
    }
    
    
    // AUTOGENERATED
    
    public Backlog getBacklog() {
        return backlog;
    }

    public void setBacklog(Backlog backlog) {
        this.backlog = backlog;
    }

    public int getBacklogId() {
        return backlogId;
    }

    public void setBacklogId(int backlogId) {
        this.backlogId = backlogId;
    }

    public Story getStory() {
        return story;
    }

    public void setStory(Story story) {
        this.story = story;
        this.storyId = story.getId();
    }
   
    public String getStoryName() {
        return story.getName();
    }

    public void setStoryName(String storyName) {
        story.setName(storyName);
    }

    public StoryState getState() {
        return state;
    }

    public void setState(StoryState state) {
        this.state = state;
    }

    public Set<Integer> getUserIds() {
        return userIds;
    }

    public void setUserIds(Set<Integer> userIds) {
        this.userIds = userIds;
    }

    public Integer getStoryId() {
        return storyId;
    }

    public void setStoryId(Integer storyId) {
        this.storyId = storyId;
    }
    
    public void setIterationId(int iterationId) {
        this.iterationId = iterationId;
    }
    
    public int getIterationId() {
        return iterationId;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Integer getPriority() {
        return priority;
    }
    
    public void setMoveTasks(boolean moveTasks) {
        this.moveTasks = moveTasks;
    }

    public String getStoryListContext() {
        return storyListContext;
    }

    public void setStoryListContext(String storyListContext) {
        this.storyListContext = storyListContext;
    }
    
    public void setStoryBusiness(StoryBusiness storyBusiness) {
        this.storyBusiness = storyBusiness;
    }
    
    public void setTransferObjectBusiness(
            TransferObjectBusiness transferObjectBusiness) {
        this.transferObjectBusiness = transferObjectBusiness;
    }

    public Collection<Task> getStoryContents() {
        return storyContents;
    }

    public StoryMetrics getMetrics() {
        return metrics;
    }

    public void setUsersChanged(boolean usersChanged) {
        this.usersChanged = usersChanged;
    }
    
}
