package com.readutf.csprojectapi.authentication;

import com.readutf.csprojectapi.profile.Profile;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor @NoArgsConstructor
public class AuthData {

    UUID token;
    Profile profile;

}
