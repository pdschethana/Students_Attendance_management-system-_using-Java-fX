package com.ijse.javaprojectfx.db;

/**
 * User Session Management Class
 * Manages current user session information across the application
 */
public class UserSession {

    private static UserSession instance;
    private String currentUserId;
    private String currentUserName;
    private String currentUserRole;
    private String currentUserEmail;

    // Private constructor for singleton pattern
    private UserSession() {
    }

    /**
     * Get singleton instance of UserSession
     * @return UserSession instance
     */
    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    /**
     * Set user session data
     * @param userId User ID
     * @param userName User Name
     * @param userRole User Role (Admin/Lecturer)
     * @param userEmail User Email
     */
    public void setUserSession(String userId, String userName, String userRole, String userEmail) {
        this.currentUserId = userId;
        this.currentUserName = userName;
        this.currentUserRole = userRole;
        this.currentUserEmail = userEmail;
    }

    /**
     * Clear user session (logout)
     */
    public void clearSession() {
        this.currentUserId = null;
        this.currentUserName = null;
        this.currentUserRole = null;
        this.currentUserEmail = null;
    }

    /**
     * Check if user is logged in
     * @return true if user is logged in, false otherwise
     */
    public boolean isLoggedIn() {
        return currentUserId != null && !currentUserId.isEmpty();
    }

    /**
     * Check if current user is admin
     * @return true if admin, false otherwise
     */
    public boolean isAdmin() {
        return "Admin".equalsIgnoreCase(currentUserRole);
    }

    /**
     * Check if current user is lecturer
     * @return true if lecturer, false otherwise
     */
    public boolean isLecturer() {
        return "Lecturer".equalsIgnoreCase(currentUserRole);
    }

    // Getters
    public String getCurrentUserId() {
        return currentUserId;
    }

    public String getCurrentUserName() {
        return currentUserName;
    }

    public String getCurrentUserRole() {
        return currentUserRole;
    }

    public String getCurrentUserEmail() {
        return currentUserEmail;
    }

    /**
     * Get formatted user info for display
     * @return Formatted user information string
     */
    public String getUserDisplayInfo() {
        if (isLoggedIn()) {
            return currentUserName + " (" + currentUserRole + ")";
        }
        return "Not logged in";
    }

    @Override
    public String toString() {
        return "UserSession{" +
                "userId='" + currentUserId + '\'' +
                ", userName='" + currentUserName + '\'' +
                ", userRole='" + currentUserRole + '\'' +
                ", userEmail='" + currentUserEmail + '\'' +
                '}';
    }
}