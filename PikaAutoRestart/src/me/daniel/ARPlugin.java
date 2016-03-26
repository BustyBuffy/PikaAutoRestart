package me.daniel;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public final class ARPlugin extends JavaPlugin {
    
    private static final int TPS = 20;
    
    private BukkitTask task_warn=null;
    private BukkitTask task_restart=null;
    private BukkitTask task_warn_2=null;
    private BukkitTask task_warn_3=null;
    private BukkitTask task_warn_4=null;
    private BukkitTask task_warn_5=null;
    private BukkitTask task_warn_6=null;
    private BukkitTask task_warn_7=null;
    private BukkitTask task_warn_8=null;
    private BukkitTask task_warn_9=null;
    
    private long current_h=0;
    private long current_m=0;
    private long current_s=0;
    private boolean enabled=false;
    
    private long timeToTicks(long h,long m, long s){
        s+=60*(m + 60*h);
        return s*TPS;
    }
    private String retlog(String s){
        getLogger().info(s);
        return s;
    }
    
    private void loadConfig(){
        saveDefaultConfig();
        reloadConfig();
        enabled=this.getConfig().getBoolean("interval.enabled");
        current_h=this.getConfig().getLong("interval.time.h");
        current_m=this.getConfig().getLong("interval.time.m");
        current_s=this.getConfig().getLong("interval.time.s");
    }
    private String reload(){
        cancelRestart();
        loadConfig();
        String action="interval disabled in configuration.";
        if(enabled) action=scheduleRestart(current_h,current_m,current_s);
        return retlog("Reloaded config - "+action);
    }
    
    private String cancelRestart(){
        enabled=false;
        if(task_warn==null && task_warn==null) return retlog("No restart was scheduled.");
        getLogger().info("Stopping PR tasks...");
        if(task_warn!=null) task_warn.cancel();
        if(task_warn_2!=null) task_warn_2.cancel();
        if(task_warn_3!=null) task_warn_3.cancel();
        if(task_warn_4!=null) task_warn_4.cancel();
        if(task_warn_5!=null) task_warn_5.cancel();
        if(task_warn_6!=null) task_warn_6.cancel();
        if(task_warn_7!=null) task_warn_7.cancel();
        if(task_warn_8!=null) task_warn_8.cancel();
        if(task_warn_9!=null) task_warn_9.cancel();
        if(task_restart!=null) task_restart.cancel();
        task_warn=null;
        task_warn_2=null;
        task_warn_3=null;
        task_warn_4=null;
        task_warn_5=null;
        task_warn_6=null;
        task_warn_7=null;
        task_warn_8=null;
        task_warn_9=null;
        task_restart=null;
        getLogger().info("Stopped PR tasks.");
        return retlog("The scheduled restart was cancelled for this session.");
    }
    
    private String scheduleRestart(long h, long m, long s){
        cancelRestart();
        enabled=true;
        getLogger().info("Scheduling new PR tasks...");
        current_h=h;
        current_m=m;
        current_s=s;
        long ticks_warn_9 = timeToTicks(h-0, m-5, s-0);
        long ticks_warn_7 = timeToTicks(h-0,m-1,s-0);
        long ticks_warn = timeToTicks(h-0,m-0,s-30);
        long ticks_restart = timeToTicks(h-0,m-0,s-0);
        long ticks_warn_2 = timeToTicks(h-0, m-0, s-10);
        long ticks_warn_3 = timeToTicks(h-0, m-0, s-5);
        long ticks_warn_4 = timeToTicks(h-0, m-0, s-4);
        long ticks_warn_5 = timeToTicks(h-0, m-0, s-3);
        long ticks_warn_6 = timeToTicks(h-0, m-0, s-2);
        long ticks_warn_8 = timeToTicks(h-0, m-0, s-1);
        task_warn = new ARTask_warn(this).runTaskLater(this, ticks_warn);
        task_warn_2 = new ARTask_warn_2(this).runTaskLater(this, ticks_warn_2);
        task_warn_3 = new ARTask_warn_3(this).runTaskLater(this, ticks_warn_3);
        task_warn_4 = new ARTask_warn_4(this).runTaskLater(this, ticks_warn_4);
        task_warn_5 = new ARTask_warn_5(this).runTaskLater(this, ticks_warn_5);
        task_warn_6 = new ARTask_warn_6(this).runTaskLater(this, ticks_warn_6);
        task_warn_7 = new ARTask_warn_7(this).runTaskLater(this, ticks_warn_7);
        task_warn_8 = new ARTask_warn_8(this).runTaskLater(this, ticks_warn_8);
        task_warn_9 = new ARTask_warn_9(this).runTaskLater(this, ticks_warn_9);
        task_restart = new ARTask_restart(this).runTaskLater(this, ticks_restart);
        getLogger().info("Scheduled new PR tasks.");
        return retlog("Scheduled restart for "+h+"h "+m+"m "+s+"s from now.");
    }
      
    @Override
    public void onEnable() {
        getLogger().info("PR Enabling...");
        reload();
        getLogger().info("PR Enabled.");
    }
    @Override
    public void onDisable() {
        getLogger().info("PR Disabling...");
        cancelRestart();
        getLogger().info("PR Disabed.");
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("pr")) {
            if(!sender.hasPermission("pikarestart.pr")){ sender.sendMessage("You don't have permission to do this."); return true; }
            else{
                if(args.length==1){
                    if(args[0].equalsIgnoreCase("now")){
                            sender.sendMessage(scheduleRestart(0,0,1));
                            return true;
                    }
                    if(args[0].equalsIgnoreCase("cancel")){
                            sender.sendMessage(cancelRestart());
                            return true;
                    }
                    if(args[0].equalsIgnoreCase("reload")){
                            sender.sendMessage(reload());
                            return true;
                    }
                    if(args[0].equalsIgnoreCase("status")){
                            String[] status={"ENABLED","DISABLED"};
                            int i=enabled?0:1;
                            sender.sendMessage("Scheduled restart is "+status[i]+" and was last set to: "+current_h+"h "+current_m+"m "+current_s+"s.");
                            return true;
                    }
                }
                if(args.length==3){
                    try {
                        sender.sendMessage(scheduleRestart(Integer.parseInt(args[0]),Integer.parseInt(args[1]),Integer.parseInt(args[0])));
                        return true;  
                     } catch (NumberFormatException e) {  
                        sender.sendMessage(retlog("The time values entered could not be understood."));
                        return false;
                     }  
                }
            }
        }
        
        return false;
    }

    
   
    
    
    
}
