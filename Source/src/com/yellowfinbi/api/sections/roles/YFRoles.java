package com.yellowfinbi.api.sections.roles;
import com.yellowfinbi.api.common.*;
import org.json.JSONObject;
import org.json.JSONArray;
import java.util.List;
class YFFunctionImpl implements IYFFunction {
    private String code, name, typeCode, accessLevel, desc;
    static { YFFactoryRegistry.registerFactory(IYFFunction.class, YFFunctionImpl::new); }
    @Override public void loadFromJSON(JSONObject j) {
        code=YFJsonHelper.getString(j,"functionCode",""); name=YFJsonHelper.getString(j,"functionName","");
        typeCode=YFJsonHelper.getString(j,"functionTypeCode",""); accessLevel=YFJsonHelper.getString(j,"accessLevelCode","");
        desc=YFJsonHelper.getString(j,"functionDescription","");
    }
    @Override public String toJSON(){return asJSON().toString();}
    @Override public JSONObject asJSON(){
        JSONObject j=new JSONObject(); j.put("functionCode",code); j.put("functionName",name);
        j.put("functionTypeCode",typeCode); j.put("accessLevelCode",accessLevel); j.put("functionDescription",desc); return j;
    }
    @Override public String getFunctionCode(){return code;} @Override public String getFunctionName(){return name;}
    @Override public String getFunctionTypeCode(){return typeCode;} @Override public String getAccessLevelCode(){return accessLevel;}
    @Override public String getFunctionDescription(){return desc;}
}
class YFRoleImpl implements IYFRole {
    private String roleName, roleDesc, roleCode; private boolean mandatory, defaultFlag, guest;
    private final YFModelList<IYFFunction> functions = new YFModelList<>(IYFFunction.class, "functions");
    static { YFFactoryRegistry.registerFactory(IYFRole.class, YFRoleImpl::new); }
    @Override public void loadFromJSON(JSONObject j) {
        roleName=YFJsonHelper.getString(j,"roleName",""); roleDesc=YFJsonHelper.getString(j,"roleDescription","");
        roleCode=YFJsonHelper.getString(j,"roleCode",""); mandatory=YFJsonHelper.getBool(j,"mandatoryFlag",false);
        defaultFlag=YFJsonHelper.getBool(j,"defaultFlag",false); guest=YFJsonHelper.getBool(j,"guestFlag",false);
        functions.loadFromJSON(j);
    }
    @Override public String toJSON(){return asJSON().toString();}
    @Override public JSONObject asJSON(){
        JSONObject j=new JSONObject(); j.put("roleName",roleName); j.put("roleDescription",roleDesc);
        j.put("roleCode",roleCode);
        JSONArray fa=new JSONArray(); for(IYFFunction f:functions.getItems()) fa.put(f.asJSON()); j.put("functions",fa);
        return j;
    }
    @Override public String getRoleName(){return roleName;} @Override public String getRoleDescription(){return roleDesc;}
    @Override public String getRoleCode(){return roleCode;} @Override public boolean isMandatoryFlag(){return mandatory;}
    @Override public boolean isDefaultFlag(){return defaultFlag;} @Override public boolean isGuestFlag(){return guest;}
    public YFModelList<IYFFunction> getFunctions(){return functions;}
}
