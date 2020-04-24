package com.example.tree.Utils;

import android.app.Activity;

import java.util.Stack;

public class StackManager {
    private static Stack mActivityStack;
    private static StackManager mInstance;

    public static StackManager getStackManager(){
        if(mInstance == null){
            mInstance = new StackManager();
        }
        return mInstance;
    }

    public void popActivity(Activity activity){
        if(activity != null){
            activity.finish();
            mActivityStack.remove(activity);
            activity = null;
        }
    }

    public Activity currentActivity(){
        if(mActivityStack == null || mActivityStack.size() == 0){
            return null;
        }
        Activity activity = (Activity) mActivityStack.lastElement();
        return activity;
    }

    public void pushActivity(Activity activity){
        if(mActivityStack == null){
            mActivityStack = new Stack();
        }
        mActivityStack.add(activity);
    }

    public void popTopActivity(Class cla){
        while (true){
            Activity activity = currentActivity();
            if(activity == null){
                break;
            }
            if(activity.getClass().equals(cla)){
                break;
            }
            popActivity(activity);
        }
    }

    public void popAllActivity(){
        while (true){
            Activity activity = currentActivity();
            if(activity == null){
                break;
            }
            popActivity(activity);
        }
    }
}