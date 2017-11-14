package com.footinit.selfproject.data.pref;

import com.footinit.selfproject.data.DataManager;

/**
 * Created by Abhijit on 08-11-2017.
 */

public interface PreferenceHelper {

    void setCurrentUserId(Long id);

    Long getCurrentUserId();

    void setCurrentUserName(String userName);

    String getCurrentUserName();

    void setCurrentUserEmail(String email);

    String getCurrentUserEmail();

    void updateUserInfoInPrefs(Long userId,
                               String userName,
                               String userEmail,
                               DataManager.LoggedInMode mode);

    void setCurrentUserLoggedInMode(DataManager.LoggedInMode mode);

    int getCurrentUserLoggedInMode();
}
