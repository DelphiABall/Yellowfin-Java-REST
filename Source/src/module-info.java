/**
 * 
 */
/**
 * @author boysa
 *
 */
module com.yellowfinbi.connector {
	requires java.net.http;
	requires org.json;

	exports com.yellowfinbi.api.common;
	exports com.yellowfinbi.api.transport;
	exports com.yellowfinbi.api.security;
	exports com.yellowfinbi.api.sections.api;
	exports com.yellowfinbi.api.sections.cache;
	exports com.yellowfinbi.api.sections.categories;
	exports com.yellowfinbi.api.sections.content;
	exports com.yellowfinbi.api.sections.dashboards;
	exports com.yellowfinbi.api.sections.datasources;
	exports com.yellowfinbi.api.sections.filters;
	exports com.yellowfinbi.api.sections.health;
	exports com.yellowfinbi.api.sections.images;
	exports com.yellowfinbi.api.sections.orgs;
	exports com.yellowfinbi.api.sections.presentations;
	exports com.yellowfinbi.api.sections.reports;
	exports com.yellowfinbi.api.sections.roles;
	exports com.yellowfinbi.api.sections.usergroups;
	exports com.yellowfinbi.api.sections.users;
}