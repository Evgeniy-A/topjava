package ru.javawebinar.topjava;

import org.springframework.lang.NonNull;
import org.springframework.test.context.support.DefaultActiveProfilesResolver;

import java.util.Arrays;

//http://stackoverflow.com/questions/23871255/spring-profiles-simple-example-of-activeprofilesresolver
public class ActiveDbProfileResolver extends DefaultActiveProfilesResolver {
    @Override
    public @NonNull
    String[] resolve(@NonNull Class<?> aClass) {
        // https://stackoverflow.com/a/52438829/548473
        String[] activeProfiles = super.resolve(aClass);
        int activeProfilesWithDbSize = activeProfiles.length + 1;
        String[] activeProfilesWithDb = Arrays.copyOf(activeProfiles, activeProfilesWithDbSize);
        activeProfilesWithDb[activeProfilesWithDbSize - 1] = Profiles.getActiveDbProfile();
        return activeProfilesWithDb;
    }
}