var iterationController = function(iterationId, element) {
 this.iterationId = iterationId;
 this.element = element;
 var me = this;
 ModelFactory.getIteration(this.iterationId, function(data) { me.render(data); });
}
iterationController.prototype = {
    render: function(data) {
      this.view = jQuery(this.element).iterationGoalTable();
      var goals = data.getIterationGoals();
      var me = this;
      jQuery.each(goals, function(index, goal){
        var row = me.view.createRow(goal);
        var name = row.createCell({
          type: "text", 
          get: function() { return goal.getName();}, 
          set: function(val){ goal.setName(val);}});
        var elsum = row.createCell({
          get: function() { return agilefantUtils.aftimeToString(goal.getEffortLeft()); }});
        var oesum = row.createCell({
          get: function() { return agilefantUtils.aftimeToString(goal.getOriginalEstimate()); }});
        var essum = row.createCell({
          get: function() { return agilefantUtils.aftimeToString(goal.getEffortSpent()); }});
        var tasks = row.createCell({
          get: function() { 
        	  return goal.getDoneTasks() + " / " + goal.getTotalTasks();
        	}});
        var buttons = row.createCell();
        buttons.setActionCell({items: [
                                       {
                                         text: "Edit",
                                         callback: function(row) {
                                           row.openEdit();
                                         }
                                       }, {
                                         text: "Delete"
                                       }
                                       ]});
        var desc = row.createCell({
          type: "wysiwyg", 
          get: function() { return goal.description; }, 
          set: function(val) { goal.setDescription(val);},
          buttons: {
            save: {text: "Save", action: function() {
              goal.openTransaction();
              row.closeEdit();
              goal.commit();
            }},
            cancel: {text: "Cancel", action: function() {
              row.cancelEdit();
            }}
          }});
      });
      this.view.render();
    }
}