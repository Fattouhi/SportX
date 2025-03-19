package com.example.sportx.services.abir;

import java.sql.SQLException;

public class ServiceFactory {
    private static ServiceFactory instance;
    private NotificationService notificationService;
    private GroupService groupService;
    private GroupMemberService groupMemberService;
    private UserService userService;

    private ServiceFactory() {
        // Private constructor to enforce singleton pattern
    }

    public static ServiceFactory getInstance() {
        if (instance == null) {
            instance = new ServiceFactory();
        }
        return instance;
    }

    public NotificationService getNotificationService() throws SQLException {
        if (notificationService == null) {
            notificationService = new NotificationService(getGroupMemberService());
        }
        return notificationService;
    }

    public GroupService getGroupService() {
        if (groupService == null) {
            groupService = new GroupService();
        }
        return groupService;
    }

    public GroupMemberService getGroupMemberService() {
        if (groupMemberService == null) {
            try {
                groupMemberService = new GroupMemberService();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return groupMemberService;
    }

    public UserService getUserService() {
        if (userService == null) {
            userService = new UserService();
        }
        return userService;
    }
}