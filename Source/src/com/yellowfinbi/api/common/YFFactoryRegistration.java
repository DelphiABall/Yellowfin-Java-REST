package com.yellowfinbi.api.common;

import com.yellowfinbi.api.sections.api.IYFAPIInfo;
import com.yellowfinbi.api.sections.api.YFAPIInfo;
import com.yellowfinbi.api.sections.categories.IYFCategory;
import com.yellowfinbi.api.sections.categories.IYFSimpleCategoryModel;
import com.yellowfinbi.api.sections.content.IYFContentItem;
import com.yellowfinbi.api.sections.dashboards.IYFDashboardListItemModel;
import com.yellowfinbi.api.sections.dashboards.IYFDashboardReportModel;
import com.yellowfinbi.api.sections.datasources.IYFClientDataSource;
import com.yellowfinbi.api.sections.datasources.IYFDataSource;
import com.yellowfinbi.api.sections.filters.IYFFilterValue;
import com.yellowfinbi.api.sections.filters.IYFPossibleFilter;
import com.yellowfinbi.api.sections.filters.IYFSearchOptions;
import com.yellowfinbi.api.sections.filters.IYFSortOption;
import com.yellowfinbi.api.sections.health.IYFConfigDBInfo;
import com.yellowfinbi.api.sections.health.IYFHealth;
import com.yellowfinbi.api.sections.health.IYFNodeCacheInfo;
import com.yellowfinbi.api.sections.health.IYFNodeInfo;
import com.yellowfinbi.api.sections.orgs.IYFOrg;
import com.yellowfinbi.api.sections.presentations.IYFPresentationListItemModel;
import com.yellowfinbi.api.sections.presentations.IYFPresentationReportModel;
import com.yellowfinbi.api.sections.reports.IYFReportListItemModel;
import com.yellowfinbi.api.sections.reports.YFReportListItemModel;
import com.yellowfinbi.api.sections.roles.IYFFunction;
import com.yellowfinbi.api.sections.roles.IYFRole;
import com.yellowfinbi.api.sections.usergroups.IYFUserGroup;
import com.yellowfinbi.api.sections.usergroups.IYFUserGroupMemberListItem;
import com.yellowfinbi.api.sections.users.IYFFavouriteModel;
import com.yellowfinbi.api.sections.users.IYFNewUser;
import com.yellowfinbi.api.sections.users.IYFSimpleUserModel;
import com.yellowfinbi.api.sections.users.IYFUser;
import com.yellowfinbi.api.sections.users.IYFUserFollowRequest;
import com.yellowfinbi.api.sections.users.IYFUserPatch;
import com.yellowfinbi.api.sections.users.IYFUserPreferences;

/**
 * Registers all default factory implementations.
 * Call {@code YFFactoryRegistration.registerAll()} at application startup
 * before making any API calls.
 *
 * To replace any default class with a custom implementation, call
 * {@code YFFactoryRegistry.registerFactory(IYFUser.class, MyCustomUser::new)}
 * AFTER registerAll().
 */
public final class YFFactoryRegistration {

    private static boolean registered = false;

    private YFFactoryRegistration() { }

    public static void registerAll() {
        if (registered) return;
        registered = true;

        // Api
        YFFactoryRegistry.registerFactory(IYFAPIInfo.class, YFAPIInfo::new);

        // Reports
        YFFactoryRegistry.registerFactory(IYFReportListItemModel.class, YFReportListItemModel::new);

        // Users
        // The implementations are package-private, so we force class loading
        // via the static initializer blocks in YFUsers.java
        forceLoad("com.yellowfinbi.api.sections.users.YFUserPreferencesImpl");
        forceLoad("com.yellowfinbi.api.sections.users.YFSimpleUserModelImpl");
        forceLoad("com.yellowfinbi.api.sections.users.YFUserPatchImpl");
        forceLoad("com.yellowfinbi.api.sections.users.YFUserImpl");
        forceLoad("com.yellowfinbi.api.sections.users.YFNewUserImpl");
        forceLoad("com.yellowfinbi.api.sections.users.YFUserFollowRequestImpl");
        forceLoad("com.yellowfinbi.api.sections.users.YFFavouriteModelImpl");

        // Health
        forceLoad("com.yellowfinbi.api.sections.health.YFNodeCacheInfoImpl");
        forceLoad("com.yellowfinbi.api.sections.health.YFConfigDBInfoImpl");
        forceLoad("com.yellowfinbi.api.sections.health.YFNodeInfoImpl");
        forceLoad("com.yellowfinbi.api.sections.health.YFHealthImpl");

        // Categories
        forceLoad("com.yellowfinbi.api.sections.categories.YFSimpleCategoryModelImpl");
        forceLoad("com.yellowfinbi.api.sections.categories.YFCategoryImpl");

        // Content
        forceLoad("com.yellowfinbi.api.sections.content.YFContentItemImpl");

        // Dashboards
        forceLoad("com.yellowfinbi.api.sections.dashboards.YFDashboardListItemModelImpl");
        forceLoad("com.yellowfinbi.api.sections.dashboards.YFDashboardReportModelImpl");

        // DataSources
        forceLoad("com.yellowfinbi.api.sections.datasources.YFDataSourceImpl");
        forceLoad("com.yellowfinbi.api.sections.datasources.YFClientDataSourceImpl");

        // Filters
        forceLoad("com.yellowfinbi.api.sections.filters.YFFilterValueImpl");
        forceLoad("com.yellowfinbi.api.sections.filters.YFPossibleFilterImpl");
        forceLoad("com.yellowfinbi.api.sections.filters.YFSortOptionImpl");
        forceLoad("com.yellowfinbi.api.sections.filters.YFSearchOptionsImpl");

        // Orgs
        forceLoad("com.yellowfinbi.api.sections.orgs.YFOrgImpl");

        // Presentations
        forceLoad("com.yellowfinbi.api.sections.presentations.YFPresentationListItemModelImpl");
        forceLoad("com.yellowfinbi.api.sections.presentations.YFPresentationReportModelImpl");

        // Roles
        forceLoad("com.yellowfinbi.api.sections.roles.YFFunctionImpl");
        forceLoad("com.yellowfinbi.api.sections.roles.YFRoleImpl");

        // UserGroups
        forceLoad("com.yellowfinbi.api.sections.usergroups.YFUserGroupImpl");
        forceLoad("com.yellowfinbi.api.sections.usergroups.YFUserGroupMemberListItemImpl");
    }

    /** Force a class to load, triggering its static initializer block. */
    private static void forceLoad(String className) {
        try {
            Class.forName(className);
        } catch (ClassNotFoundException e) {
            System.err.println("Warning: Could not load factory class: " + className);
        }
    }
}
