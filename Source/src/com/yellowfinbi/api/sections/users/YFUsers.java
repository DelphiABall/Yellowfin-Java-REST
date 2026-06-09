package com.yellowfinbi.api.sections.users;
import com.yellowfinbi.api.common.*;
import org.json.JSONObject;

// ── UserPreferences ──────────────────────────────────────────
class YFUserPreferencesImpl implements IYFUserPreferences {
    private String entryPage, allowConnect, allowView, allowPost;
    private int draftCount, recentCount; private boolean retainFilter;
    static { YFFactoryRegistry.registerFactory(IYFUserPreferences.class, YFUserPreferencesImpl::new); }
    @Override public void loadFromJSON(JSONObject j) {
        entryPage=YFJsonHelper.getString(j,"entryPage",""); draftCount=YFJsonHelper.getInt(j,"draftContentItemCount",0);
        recentCount=YFJsonHelper.getInt(j,"recentContentItemCount",0); retainFilter=YFJsonHelper.getBool(j,"retainContentTypeFilterOnBrowsePage",false);
        allowConnect=YFJsonHelper.getString(j,"allowUsersToConnect","REQUIREAPPROVAL");
        allowView=YFJsonHelper.getString(j,"allowUsersToViewTimeline","REQUIRECONNECTION");
        allowPost=YFJsonHelper.getString(j,"allowUsersToPostOnTimeline","REQUIRECONNECTION");
    }
    @Override public String getEntryPage(){return entryPage;} @Override public int getDraftContentItemCount(){return draftCount;}
    @Override public int getRecentContentItemCount(){return recentCount;} @Override public boolean isRetainContentTypeFilterOnBrowsePage(){return retainFilter;}
    @Override public String getAllowUsersToConnect(){return allowConnect;} @Override public String getAllowUsersToViewTimeline(){return allowView;}
    @Override public String getAllowUsersToPostOnTimeline(){return allowPost;}
}

// ── SimpleUserModel ──────────────────────────────────────────
class YFSimpleUserModelImpl implements IYFSimpleUserModel {
    private long userId; private String name, email, connStatus;
    static { YFFactoryRegistry.registerFactory(IYFSimpleUserModel.class, YFSimpleUserModelImpl::new); }
    @Override public void loadFromJSON(JSONObject j) {
        userId=YFJsonHelper.getLong(j,"userId",0); name=YFJsonHelper.getString(j,"name","");
        email=YFJsonHelper.getString(j,"email",""); connStatus=YFJsonHelper.getString(j,"userConnectionStatus","");
    }
    @Override public long getUserId(){return userId;} @Override public String getName(){return name;}
    @Override public String getEmail(){return email;} @Override public String getUserConnectionStatus(){return connStatus;}
}

// ── UserPatch ────────────────────────────────────────────────
class YFUserPatchImpl implements IYFUserPatch {
    private String firstName="",lastName="",title="",desc="",langCode="",tzCode="";
    private IYFUserPreferences prefs;
    static { YFFactoryRegistry.registerFactory(IYFUserPatch.class, YFUserPatchImpl::new); }
    @Override public void loadFromJSON(JSONObject j) {
        firstName=YFJsonHelper.getString(j,"firstName",""); lastName=YFJsonHelper.getString(j,"lastName","");
        title=YFJsonHelper.getString(j,"title",""); desc=YFJsonHelper.getString(j,"description","");
        langCode=YFJsonHelper.getString(j,"preferredLanguageCode",""); tzCode=YFJsonHelper.getString(j,"timeZoneCode","");
        JSONObject pj=YFJsonHelper.getObject(j,"userPreferences");
        if(pj!=null){prefs=YFFactoryRegistry.createNew(IYFUserPreferences.class); prefs.loadFromJSON(pj);}
    }
    @Override public String toJSON(){return asJSON().toString();}
    @Override public JSONObject asJSON(){
        JSONObject j=new JSONObject();
        j.put("firstName",firstName); j.put("lastName",lastName);
        j.put("title",title); j.put("description",desc);
        if(!langCode.isEmpty())j.put("preferredLanguageCode",langCode);
        if(!tzCode.isEmpty())j.put("timeZoneCode",tzCode);
        if(prefs!=null){
            j.put("entryPage",prefs.getEntryPage());
            j.put("draftContentItemCount",prefs.getDraftContentItemCount());
            j.put("recentContentItemCount",prefs.getRecentContentItemCount());
            j.put("retainContentTypeFilterOnBrowsePage",prefs.isRetainContentTypeFilterOnBrowsePage());
            j.put("allowUsersToConnect",prefs.getAllowUsersToConnect());
            j.put("allowUsersToViewTimeline",prefs.getAllowUsersToViewTimeline());
            j.put("allowUsersToPostOnTimeline",prefs.getAllowUsersToPostOnTimeline());
        }
        return j;
    }
    @Override public String getFirstName(){return firstName;} @Override public void setFirstName(String v){firstName=v;}
    @Override public String getLastName(){return lastName;} @Override public void setLastName(String v){lastName=v;}
    @Override public String getTitle(){return title;} @Override public void setTitle(String v){title=v;}
    @Override public String getDescription(){return desc;} @Override public void setDescription(String v){desc=v;}
    @Override public String getPreferredLanguageCode(){return langCode;} @Override public void setPreferredLanguageCode(String v){langCode=v;}
    @Override public String getTimeZoneCode(){return tzCode;} @Override public void setTimeZoneCode(String v){tzCode=v;}
    @Override public IYFUserPreferences getUserPreferences(){
        if(prefs==null) prefs=YFFactoryRegistry.createNew(IYFUserPreferences.class);
        return prefs;
    }
}

// ── User (full) ──────────────────────────────────────────────
class YFUserImpl implements IYFUser {
    private long userId; private String userName,email,firstName,lastName,title,desc,status,roleCode,tzCode,langCode;
    private boolean isCurrent; private int numStories,numFollowers,numFollowing,numDiscussions;
    private IYFUserPreferences prefs;
    static { YFFactoryRegistry.registerFactory(IYFUser.class, YFUserImpl::new); }
    @Override public void loadFromJSON(JSONObject j) {
        userId=YFJsonHelper.getLong(j,"userId",0); userName=YFJsonHelper.getString(j,"userName","");
        email=YFJsonHelper.getString(j,"email",""); firstName=YFJsonHelper.getString(j,"firstName","");
        lastName=YFJsonHelper.getString(j,"lastName",""); title=YFJsonHelper.getString(j,"title","");
        desc=YFJsonHelper.getString(j,"description",""); status=YFJsonHelper.getString(j,"status","");
        roleCode=YFJsonHelper.getString(j,"roleCode",""); tzCode=YFJsonHelper.getString(j,"timeZoneCode","");
        langCode=YFJsonHelper.getString(j,"preferredLanguageCode",""); isCurrent=YFJsonHelper.getBool(j,"isCurrentUser",false);
        numStories=YFJsonHelper.getInt(j,"numStories",0); numFollowers=YFJsonHelper.getInt(j,"numFollowers",0);
        numFollowing=YFJsonHelper.getInt(j,"numFollowing",0); numDiscussions=YFJsonHelper.getInt(j,"numDiscussionStreams",0);
        // The API may return "name" (combined display name) instead of firstName/lastName
        String name=YFJsonHelper.getString(j,"name","");
        if(!name.isEmpty() && firstName.isEmpty() && lastName.isEmpty()) {
            int sp=name.indexOf(' ');
            if(sp>0){firstName=name.substring(0,sp);lastName=name.substring(sp+1);}
            else{firstName=name;}
        }
        // Fall back: use email as userName if not provided
        if(userName.isEmpty() && !email.isEmpty()) userName=email;
        JSONObject pj=YFJsonHelper.getObject(j,"userPreferences");
        if(pj!=null){prefs=YFFactoryRegistry.createNew(IYFUserPreferences.class); prefs.loadFromJSON(pj);}
    }
    @Override public String toJSON(){return asJSON().toString();}
    @Override public JSONObject asJSON(){
        JSONObject j=new JSONObject();
        j.put("userId",userId); j.put("userName",userName); j.put("email",email);
        j.put("firstName",firstName); j.put("lastName",lastName); j.put("title",title);
        j.put("description",desc); j.put("status",status); j.put("roleCode",roleCode);
        j.put("timeZoneCode",tzCode); j.put("preferredLanguageCode",langCode);
        return j;
    }
    @Override public long getUserId(){return userId;} @Override public String getUserName(){return userName;}
    @Override public String getEmail(){return email;} @Override public String getFirstName(){return firstName;}
    @Override public String getLastName(){return lastName;} @Override public String getTitle(){return title;}
    @Override public String getDescription(){return desc;} @Override public String getStatus(){return status;}
    @Override public String getRoleCode(){return roleCode;} @Override public String getTimeZoneCode(){return tzCode;}
    @Override public String getPreferredLanguageCode(){return langCode;} @Override public boolean isCurrentUser(){return isCurrent;}
    @Override public int getNumStories(){return numStories;} @Override public int getNumFollowers(){return numFollowers;}
    @Override public int getNumFollowing(){return numFollowing;} @Override public int getNumDiscussionStreams(){return numDiscussions;}
    @Override public IYFUserPreferences getUserPreferences(){
        if(prefs==null) prefs=YFFactoryRegistry.createNew(IYFUserPreferences.class);
        return prefs;
    }
}

// ── NewUser ──────────────────────────────────────────────────
class YFNewUserImpl implements IYFNewUser {
    private long userId; private String userName="",email="",roleCode="",status="ACTIVE",password="";
    private String firstName="",lastName="",title="",desc="",tzCode="",langCode="";
    private IYFUserPreferences prefs;
    static { YFFactoryRegistry.registerFactory(IYFNewUser.class, YFNewUserImpl::new); }
    @Override public void loadFromJSON(JSONObject j) {
        userId=YFJsonHelper.getLong(j,"userId",0);
        firstName=YFJsonHelper.getString(j,"firstName",""); lastName=YFJsonHelper.getString(j,"lastName","");
        title=YFJsonHelper.getString(j,"title",""); desc=YFJsonHelper.getString(j,"description","");
        langCode=YFJsonHelper.getString(j,"preferredLanguageCode",""); tzCode=YFJsonHelper.getString(j,"timeZoneCode","");
        status=YFJsonHelper.getString(j,"status","ACTIVE");
        JSONObject pj=YFJsonHelper.getObject(j,"userPreferences");
        if(pj!=null){prefs=YFFactoryRegistry.createNew(IYFUserPreferences.class); prefs.loadFromJSON(pj);}
    }
    @Override public String toJSON(){return asJSON().toString();}
    @Override public JSONObject asJSON(){
        JSONObject j=new JSONObject();
        j.put("userName",userName); j.put("email",email);
        j.put("roleCode",roleCode); j.put("status",status);
        j.put("password",password);
        j.put("firstName",firstName); j.put("lastName",lastName);
        j.put("title",title); j.put("description",desc);
        j.put("timeZoneCode",tzCode); j.put("preferredLanguageCode",langCode);
        if(prefs!=null){
            j.put("entryPage",prefs.getEntryPage());
            j.put("draftContentItemCount",prefs.getDraftContentItemCount());
            j.put("recentContentItemCount",prefs.getRecentContentItemCount());
            j.put("retainContentTypeFilterOnBrowsePage",prefs.isRetainContentTypeFilterOnBrowsePage());
            j.put("allowUsersToConnect",prefs.getAllowUsersToConnect());
            j.put("allowUsersToViewTimeline",prefs.getAllowUsersToViewTimeline());
            j.put("allowUsersToPostOnTimeline",prefs.getAllowUsersToPostOnTimeline());
        }
        return j;
    }
    @Override public long getUserId(){return userId;} @Override public void setUserId(long v){userId=v;}
    @Override public String getUserName(){return userName;} @Override public void setUserName(String v){userName=v;}
    @Override public String getEmail(){return email;} @Override public void setEmail(String v){email=v;}
    @Override public String getRoleCode(){return roleCode;} @Override public void setRoleCode(String v){roleCode=v;}
    @Override public String getStatus(){return status;} @Override public void setStatus(String v){status=v;}
    @Override public String getPassword(){return password;} @Override public void setPassword(String v){password=v;}
    @Override public String getFirstName(){return firstName;} @Override public void setFirstName(String v){firstName=v;}
    @Override public String getLastName(){return lastName;} @Override public void setLastName(String v){lastName=v;}
    @Override public String getTitle(){return title;} @Override public void setTitle(String v){title=v;}
    @Override public String getDescription(){return desc;} @Override public void setDescription(String v){desc=v;}
    @Override public String getTimeZoneCode(){return tzCode;} @Override public void setTimeZoneCode(String v){tzCode=v;}
    @Override public String getPreferredLanguageCode(){return langCode;} @Override public void setPreferredLanguageCode(String v){langCode=v;}
    @Override public IYFUserPreferences getUserPreferences(){
        if(prefs==null) prefs=YFFactoryRegistry.createNew(IYFUserPreferences.class);
        return prefs;
    }
}

// ── UserFollowRequest ────────────────────────────────────────
class YFUserFollowRequestImpl implements IYFUserFollowRequest {
    private long subjectUserId, objectUserId; private String connStatus;
    static { YFFactoryRegistry.registerFactory(IYFUserFollowRequest.class, YFUserFollowRequestImpl::new); }
    @Override public void loadFromJSON(JSONObject j) {
        subjectUserId=YFJsonHelper.getLong(j,"subjectUserId",0); objectUserId=YFJsonHelper.getLong(j,"objectUserId",0);
        connStatus=YFJsonHelper.getString(j,"userConnectionStatus","");
    }
    @Override public long getSubjectUserId(){return subjectUserId;} @Override public long getObjectUserId(){return objectUserId;}
    @Override public String getUserConnectionStatus(){return connStatus;}
}

// ── FavouriteModel ───────────────────────────────────────────
class YFFavouriteModelImpl implements IYFFavouriteModel {
    private long userId, contentId; private String name, contentType, title, desc, contentUuid, publishedDate;
    static { YFFactoryRegistry.registerFactory(IYFFavouriteModel.class, YFFavouriteModelImpl::new); }
    @Override public void loadFromJSON(JSONObject j) {
        userId=YFJsonHelper.getLong(j,"userId",0); name=YFJsonHelper.getString(j,"name","");
        contentType=YFJsonHelper.getString(j,"contentType",""); title=YFJsonHelper.getString(j,"title","");
        desc=YFJsonHelper.getString(j,"description",""); contentId=YFJsonHelper.getLong(j,"contentId",0);
        contentUuid=YFJsonHelper.getString(j,"contentUuid",""); publishedDate=YFJsonHelper.getString(j,"publishedDate","");
    }
    @Override public long getUserId(){return userId;} @Override public String getName(){return name;}
    @Override public String getContentType(){return contentType;} @Override public String getTitle(){return title;}
    @Override public String getDescription(){return desc;} @Override public long getContentId(){return contentId;}
    @Override public String getContentUuid(){return contentUuid;} @Override public String getPublishedDate(){return publishedDate;}
}
