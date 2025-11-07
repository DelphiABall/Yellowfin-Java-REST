package com.yellowfinbi.api.classes;

import org.json.JSONObject;

public class YFUser {
	private int ipId; // set by Yellowfin
	private String lastName;
	private String firstName;
	private String languageCode;
	private String emailAddress;
	private String userId;
	private String roleCode;
	private String timeZoneCode;
	private Status status;

	public enum Status {
		ACTIVE, INACTIVE, INACTIVEWITHEMAIL
	}

	// Constructors
	public YFUser() {
	}

	public YFUser(int ipId, String lastName, String firstName, String languageCode, String emailAddress, String userId,
			String roleCode, String timeZoneCode, Status status) {
		this.ipId = ipId; // Yellowfin ID, used for Updating a user
		this.lastName = lastName;
		this.firstName = firstName;
		this.languageCode = languageCode;
		this.emailAddress = emailAddress;
		this.userId = userId; // 3rd party system ID for login
		this.roleCode = roleCode;
		this.timeZoneCode = timeZoneCode;
		this.status = status;
	}

	public static YFUser createFromJSON(String json) {
		JSONObject jsonObject = new JSONObject(json);
		YFUser user = new YFUser();
		user.setIpId(jsonObject.optInt("ipId", 0));
		user.setLastName(jsonObject.optString("lastName", ""));
		user.setFirstName(jsonObject.optString("firstName", ""));
		user.setLanguageCode(jsonObject.optString("languageCode", ""));
		user.setEmailAddress(jsonObject.optString("emailAddress", ""));
		user.setUserId(jsonObject.optString("userId", ""));
		user.setRoleCode(jsonObject.optString("roleCode", ""));
		user.setTimeZoneCode(jsonObject.optString("timeZoneCode", ""));
		String statusValue = jsonObject.optString("status", "");
		if ("ACTIVE".equals(statusValue)) {
			user.setStatus(Status.ACTIVE);
		} else if ("INACTIVE".equals(statusValue)) {
			user.setStatus(Status.INACTIVE);
		} else if ("INACTIVEWITHEMAIL".equals(statusValue)) {
			user.setStatus(Status.INACTIVEWITHEMAIL);
		} else {
			user.setStatus(null);
		}
		return user;
	}

	public JSONObject toJSON(Boolean isUpdate) {

		JSONObject jsonObject = new JSONObject();
		// Check and add properties with values

		if (!isUpdate) {
			if (ipId > 0) {
				jsonObject.put("ipId", ipId);
			}
			jsonObject.put("emailAddress", emailAddress); // Required - Maybe Blank
			jsonObject.put("userId", userId); // Required - Maybe Blank

		}

		if (!isUpdate) { // Required - Maybe Blank
			jsonObject.put("roleCode", roleCode);
		} else {
			if (roleCode != null && !roleCode.isEmpty()) {
				jsonObject.put("roleCode", roleCode);
			}
		}

		if (lastName != null && !lastName.isEmpty()) {
			jsonObject.put("lastName", lastName);
		}
		if (firstName != null && !firstName.isEmpty()) {
			jsonObject.put("firstName", firstName);
		}
		if (languageCode != null && !languageCode.isEmpty()) {
			jsonObject.put("languageCode", languageCode);
		}
		if (timeZoneCode != null && !timeZoneCode.isEmpty()) {
			jsonObject.put("timeZoneCode", timeZoneCode);
		}
		// Convert the Status enum to a string if not null
		if (status != null) {
			jsonObject.put("status", status.name());
		}

		return jsonObject;
	}

	// Getter and Setter methods for each property
	public int getIpId() {
		return ipId;
	}

	public void setIpId(int ipId) {
		this.ipId = ipId;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLanguageCode() {
		return languageCode;
	}

	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRoleCode() {

		if (roleCode == null || roleCode.isEmpty()) {
			return "YFREPORTCONSUMER";
		}
		return roleCode;

	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getTimeZoneCode() {
		return timeZoneCode;
	}

	public void setTimeZoneCode(String timeZoneCode) {
		this.timeZoneCode = timeZoneCode;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
}
