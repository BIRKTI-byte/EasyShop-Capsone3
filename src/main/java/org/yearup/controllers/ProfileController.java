package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.data.ProfileDao;
import org.yearup.data.UserDao;
import org.yearup.models.Profile;
import org.yearup.models.User;

import java.security.Principal;

@RestController
@RequestMapping("/profile")
@CrossOrigin
@PreAuthorize("isAuthenticated()")
public class ProfileController
{
    private final ProfileDao profileDao;
    private final UserDao userDao;

    @Autowired
    public ProfileController(ProfileDao profileDao, UserDao userDao)
    {
        this.profileDao = profileDao;
        this.userDao = userDao;
    }

    // GET /profile - return current user's profile
    @GetMapping("")
    public Profile getProfile(Principal principal)
    {
        try
        {
            User user = userDao.getByUserName(principal.getName());
            Profile profile = profileDao.getByUserId(user.getId());
            
            if (profile == null)
            {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Profile not found.");
            }
            
            return profile;
        }
        catch (ResponseStatusException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }

    // PUT /profile - update current user's profile
    @PutMapping("")
    public Profile updateProfile(@RequestBody Profile profile, Principal principal)
    {
        try
        {
            User user = userDao.getByUserName(principal.getName());
            profile.setUserId(user.getId()); // Ensure we're updating the correct user's profile
            
            Profile updatedProfile = profileDao.update(profile);
            
            if (updatedProfile == null)
            {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Profile not found.");
            }
            
            return updatedProfile;
        }
        catch (ResponseStatusException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }
}
